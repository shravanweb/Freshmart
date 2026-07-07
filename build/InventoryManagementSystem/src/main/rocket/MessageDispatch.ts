import { Observable } from "rxjs";
import ConnectionEvent from "../classes/ConnectionEvent";
import ConnectionStatus from "../classes/ConnectionStatus";
import { Consumer } from "../classes/core";
import Result from "../classes/Result";
import ResultStatus from "../classes/ResultStatus";
import IdGenerator from "../classes/IdGenerator";
import LoginResult from "../classes/LoginResult";
import Completer from "../core/Completer";
import StreamExt from "../core/StreamExt";
import D3EObjectChanges from "../utils/D3EObjectChanges";
import DBObject from "../utils/DBObject";
import DBSaveStatus from "../utils/DBSaveStatus";
import EventBus from "../utils/EventBus";
import LocalDataStore from "../utils/LocalDataStore";
import ReferenceCatch from "../utils/ReferenceCatch";
import Timer from "../utils/Timer";
import BufferReader from "./BufferReader";
import Channels from "./Channels";
import { ChannelConstants, D3ETemplate, RPCConstants } from "./D3ETemplate";
import {
  D3ETemplateClass,
  D3ETemplateMethodWithReturn,
  D3ETemplateType,
  D3ETypeUsage,
  D3EUsage,
} from "./D3ETemplateTypes";
import D3EWebClient from "./D3EWebClient";
import WSReader from "./WSReader";
import WSWriter from "./WSWriter";
import D3EDisposable from "./D3EDisposable";
import Duration from "../core/Duration";
import GlobalFunctions from "../utils/GlobalFunctions";
import BaseUser from "../models/BaseUser";

class Resp {
  constructor(public id: number, public reader: WSReader) {}
}

enum _ConnectionStatus {
  Connecting,
  TypeExchange,
  Ready,
  Disconnected,
}

class ObjectSyncDisposable implements D3EDisposable {
  private _disposed: boolean = false;
  constructor(public subId: string) {}

  public dispose(): void {
    this._disposed = true;
    if (this.subId == null) {
      return;
    }
    MessageDispatch.get()._unsubscribe(this.subId);
  }

  public get disposed(): boolean {
    return this._disposed;
  }
}

export default class MessageDispatch {
  public static readonly allowParallelReq: boolean = true;
  public static readonly allowParallelMutation: boolean = false;
  public static readonly ERROR: number = 0;
  public static readonly CONFIRM_TEMPLATE: number = 1;
  public static readonly HASH_CHECK: number = 2;
  public static readonly TYPE_EXCHANGE: number = 3;
  public static readonly RESTORE: number = 4;
  public static readonly OBJECT_QUERY: number = 5;
  public static readonly DATA_QUERY: number = 6;
  public static readonly SAVE: number = 7;
  public static readonly DELETE: number = 8;
  public static readonly UNSUBSCRIBE: number = 9;
  public static readonly LOGIN: number = 10;
  public static readonly LOGIN_WITH_TOKEN: number = 11;
  public static readonly CONNECT: number = 12;
  public static readonly DISCONNECT: number = 13;
  public static readonly LOGOUT: number = 14;
  public static readonly OBJECTS: number = -1;
  public static readonly CHANNEL_MESSAGE: number = -2;
  // -3 -> CHANNEL_MESSAGE_ACK
  private static readonly RPC_MESSAGE: number = -4;

  private _retryCount: number = 0;
  private _status: _ConnectionStatus = _ConnectionStatus.Disconnected;
  private _client: D3EWebClient;
  private _cache: ReferenceCatch;
  private _sessionId: string;
  public respStream: Observable<Resp>;
  public subscriptions: Map<DBObject, string> = new Map();
  public readyCompleter: Completer<boolean>;
  private _reqInProgress: Completer<any>;
  private _mutationInProgress: Completer<any>;
  private _pendingMutation: number = 0;

  private static _dispatch: MessageDispatch;

  public static get(): MessageDispatch {
    if (MessageDispatch._dispatch == null) {
      MessageDispatch._dispatch = new MessageDispatch();
    }
    return MessageDispatch._dispatch;
  }

  private constructor() {}

  private async _init(): Promise<void> {
    try {
      if (this._status != _ConnectionStatus.Disconnected) {
        return;
      }
      EventBus.get().fire(
        new ConnectionEvent({ status: ConnectionStatus.Connecting })
      );
      this._status = _ConnectionStatus.Connecting;
      console.log("Status: " + this._status);
      this._cache = ReferenceCatch.get();
      this._client = D3EWebClient.get();
      let st: Observable<ArrayBuffer> = this._client.broadcastStream();
      this.respStream = StreamExt.map(st, (e: ArrayBuffer) => {
        let reader: WSReader = new WSReader(
          this._cache,
          new BufferReader(new Uint8Array(e))
        );
        let rid: number = reader.readInteger();
        return new Resp(rid, reader);
      });
      StreamExt.listen(
        this.respStream,
        (resp: Resp) => {
          if (resp.id === MessageDispatch.OBJECTS) {
            this._onObjects(resp);
          } else if (resp.id === MessageDispatch.CHANNEL_MESSAGE) {
            //read channel message
            this._onChannelMessage(resp);
          }
        },
        {
          onDone: () => {
            EventBus.get().fire(
              new ConnectionEvent({ status: ConnectionStatus.Connected })
            );
            if (this.subscriptions.isNotEmpty) {
              this._reconnect();
            }
          },
          onError: (e) => {
            EventBus.get().fire(
              new ConnectionEvent({ status: ConnectionStatus.ConnectionFailed })
            );
            if (this.subscriptions.isNotEmpty) {
              this._reconnect();
            }
          },
          cancelOnError: true,
        }
      );
      await this._client.ready();
      let res: boolean = false;
      if (this._sessionId != null) {
        console.log("Trying to restore session");
        res = await this._restore();
      }
      if (!res) {
        await this._confirmTemplate();
        let token: string = await LocalDataStore.get().getToken();
        if (token != null) {
          await this._doAuth(token, "d3e");
        }
      } else {
        console.log("Restore Success");
      }
      this._status = _ConnectionStatus.Ready;
      this._retryCount = 0;
      this.readyCompleter.complete();
      this.readyCompleter = null;
    } catch (e) {}
  }

  public async loginWithToken(token: string, type: string): Promise<BaseUser> {
    LocalDataStore.get().setUser(null, null);
    await this.checkAndInit();
    await this.waitForAccess(true);
    await this._doAuth(token, type);
    return LocalDataStore.get().currentUser();
  }

  private async _doAuth(token: string, type: string): Promise<void> {
    let writer: WSWriter = new WSWriter(this._cache);
    let mid: number = IdGenerator.get().next();
    writer.writeInteger(mid);
    writer.writeByte(MessageDispatch.LOGIN_WITH_TOKEN);
    writer.writeString(token);
    writer.writeString(type);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (e: Resp) => e.id === mid
    ).then((r) => r.reader);
    reader.readByte(); //Must be LOGIN_WITH_TOKEN
    let res: number = reader.readByte();
    if (res === 1) {
      LocalDataStore.get().setUser(null, null);
      EventBus.get().fire(
        new ConnectionEvent({ status: ConnectionStatus.AuthFailed })
      );
    } else {
      let user = await LocalDataStore.get().currentUser();
      if (user == null) {
        let newToken = reader.readString();
        let user = reader.readRef(0, null);
        reader.done();
        LocalDataStore.get().setUser(user as BaseUser, newToken);
      }
    }
  }

  private _onObjects(resp: Resp): void {
    //console.log('OnObjects: start ' + resp.id.toString());
    let reader: WSReader = resp.reader;
    reader.start();
    let update: boolean = reader.readBoolean();
    let count: number = reader.readInteger();
    while (count > 0) {
      if (update) {
        let obj: DBObject = reader.readRef(0, null);
        if (obj != null) {
          obj.saveStatus = obj.d3eChanges.hasChanges
            ? DBSaveStatus.Changed
            : DBSaveStatus.Saved;
          console.log("OnObjects: ${obj.d3eType} ID: " + obj.id.toString());
        }
      } else {
        let obj: DBObject = this._cache.findObject(
          reader.readInteger(),
          reader.readInteger()
        );
        if (obj != null) {
          obj.saveStatus = DBSaveStatus.Deleted;
        }
      }
      count--;
    }
    reader.done();
    //console.log('OnObjects: end ' + resp.id.toString());
  }

  private _onChannelMessage(resp: Resp): void {
    Channels.onMessage(resp.reader);
  }

  private _reconnect(): void {
    this._status = _ConnectionStatus.Disconnected;
    this._client?.disconnect();
    let ms: number = 2 ^ this._retryCount;
    let timeOut: number = Math.min(ms, 1000) * 10;
    console.log("Will reconnect on $timeOut ms : $ms");
    new Timer(new Duration({ milliseconds: timeOut }), async () => {
      this._retryCount++;
      await this._init();
    });
  }

  private async _restore(): Promise<boolean> {
    let writer: WSWriter = new WSWriter(this._cache);
    let mid: number = IdGenerator.get().next();
    writer.writeInteger(mid);
    writer.writeByte(MessageDispatch.RESTORE);
    writer.writeString(this._sessionId);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (e: Resp) => e.id === mid
    ).then((r) => r.reader);
    reader.readByte(); //Must be CONFIRM_TEMPLATE
    let res: number = reader.readByte();
    if (res === 0) {
      this._sessionId = reader.readString();
      return true;
    }
    EventBus.get().fire(
      new ConnectionEvent({ status: ConnectionStatus.RestoreFailed })
    );
    this._sessionId = null;
    return false;
  }

  private async _confirmTemplate(): Promise<boolean> {
    this._status = _ConnectionStatus.TypeExchange;
    console.log("Status: " + this._status);
    let writer: WSWriter = new WSWriter(this._cache);
    let mid: number = IdGenerator.get().next();
    writer.writeInteger(mid);
    writer.writeByte(MessageDispatch.CONFIRM_TEMPLATE);
    writer.writeString(D3ETemplate.HASH);
    writer.writeInteger(-1);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (e: Resp) => e.id === mid
    ).then((r) => r.reader);
    reader.readByte(); //Must be CONFIRM_TEMPLATE

    this._sessionId = reader.readString();
    let res: number = reader.readByte();
    if (res === 0) {
      return true;
    }

    //Hash Check
    writer = new WSWriter(this._cache);
    mid = IdGenerator.get().next();
    writer.writeInteger(mid);
    writer.writeByte(MessageDispatch.HASH_CHECK);

    //Write all types & usages
    let types: D3ETemplateType[] = D3ETemplate.types;
    writer.writeInteger(types.length);
    let usages: D3EUsage[] = D3ETemplate.usages;
    writer.writeInteger(usages.length);

    // Channels
    writer.writeInteger(ChannelConstants.TOTAL_CHANNEL_COUNT);

    // RPCs
    writer.writeInteger(RPCConstants.TOTAL_RPC_CLASS_COUNT);

    for (let t of types) {
      writer.writeString(t.hash);
    }
    for (let u of usages) {
      writer.writeString(u.hash);
    }
    for (let c of ChannelConstants.channels) {
      writer.writeString(c.hash);
    }
    for (let c of RPCConstants.classes) {
      writer.writeString(c.hash);
    }

    this._client.send(writer.out);
    reader = await StreamExt.firstWhere(
      this.respStream,
      (e: Resp) => e.id === mid
    ).then((r) => r.reader);
    reader.readByte(); //Must be HASH_CHECK
    res = reader.readByte();
    if (res === 0) {
      return true;
    }
    let unknownTypes: number[] = reader.readIntegerList();
    let unknownUsages: number[] = reader.readIntegerList();
    let unknownChannels: number[] = reader.readIntegerList();
    let unknownRPClasses: number[] = reader.readIntegerList();

    //Type Exchange
    writer = new WSWriter(this._cache);
    mid = IdGenerator.get().next();
    writer.writeInteger(mid);
    writer.writeByte(MessageDispatch.TYPE_EXCHANGE);

    //Types
    writer.writeInteger(unknownTypes.length);
    unknownTypes.forEach((e) => {
      writer.writeInteger(e);
      let t: D3ETemplateType = D3ETemplate.types[e];
      writer.writeString(t.name);
      writer.writeInteger(t.parent);
      writer.writeInteger(t.fields.length);
      for (let f of t.fields) {
        writer.writeString(f.name);
        writer.writeInteger(f.type);
      }
    });

    //Usages
    writer.writeInteger(unknownUsages.length);
    unknownUsages.forEach((e) => {
      writer.writeInteger(e);
      let u: D3EUsage = D3ETemplate.usages[e];
      writer.writeInteger(u.types.length);
      for (let ut of u.types) {
        this._writeTypeUsage(ut, writer);
      }
    });

    // Channels
    writer.writeInteger(unknownChannels.length);
    unknownChannels.forEach((e) => {
      writer.writeInteger(e);
      let c: D3ETemplateClass = ChannelConstants.channels[e];
      writer.writeString(c.name);
      writer.writeInteger(c.methods.length);
      for (let tm of c.methods) {
        writer.writeString(tm.name);
        writer.writeInteger(tm.params.length);
        for (let tp of tm.params) {
          writer.writeInteger(tp.type);
          writer.writeBoolean(tp.collection);
        }
      }
    });

    // Remote Procedure Calls
    writer.writeInteger(unknownRPClasses.length);
    unknownRPClasses.forEach((e) => {
      writer.writeInteger(e);
      let c: D3ETemplateClass = RPCConstants.classes[e];
      writer.writeString(c.name);
      writer.writeInteger(c.methods.length);
      for (let tm2 of c.methods) {
        let tm: D3ETemplateMethodWithReturn =
          tm2 as D3ETemplateMethodWithReturn;
        writer.writeString(tm.name);
        writer.writeInteger(tm.params.length);
        for (let tp of tm.params) {
          writer.writeInteger(tp.type);
          writer.writeBoolean(tp.collection);
        }
        writer.writeInteger(tm.returnType);
        writer.writeBoolean(tm.returnCollection);
      }
    });

    this._client.send(writer.out);
    reader = await StreamExt.firstWhere(
      this.respStream,
      (e: Resp) => e.id === mid
    ).then((r) => r.reader);
    reader.readByte(); //Must be TYPE_EXCHANGE
    let unusedTypes: number[] = reader.readIntegerList();
    unusedTypes.forEach((type) => {
      D3ETemplate.types[type].unknown = true;
    });
    let typesWithUnusedFieldsCount: number = reader.readInteger();
    for (let x: number = 0; x < typesWithUnusedFieldsCount; x++) {
      let type: number = reader.readInteger();
      let unusedFields: number[] = reader.readIntegerList();
      unusedFields.forEach((field) => {
        D3ETemplate.types[type].fields[field].unknown = true;
      });
    }
    return true;
  }

  async checkAndInit(): Promise<void> {
    console.log("Status: " + this._status);
    if (this._status === _ConnectionStatus.Ready) {
      return Promise.resolve();
    } else if (
      this._status === _ConnectionStatus.TypeExchange ||
      this._status === _ConnectionStatus.Connecting
    ) {
      if (this.readyCompleter == null) {
        this.readyCompleter = new Completer();
      }
      await this.readyCompleter?.future;
    } else if (this._status === _ConnectionStatus.Disconnected) {
      if (this.readyCompleter == null) {
        this.readyCompleter = new Completer();
      }
      await this._init();
      await this.readyCompleter?.future;
    }
  }

  private _writeTypeUsage(u: D3ETypeUsage, reader: WSWriter): void {
    reader.writeInteger(u.type);
    reader.writeInteger(u.fields.length);
    for (let f of u.fields) {
      reader.writeInteger(f.field);
      if (f.types == null || f.types.isEmpty) {
        reader.writeInteger(0);
      } else {
        reader.writeInteger(f.types.length);
        for (let tu of f.types) {
          this._writeTypeUsage(tu, reader);
        }
      }
    }
  }

  async query<T extends DBObject>(
    type: number,
    id: number,
    usage: number
  ): Promise<T> {
    if (id <= 0) {
      return null;
    }
    await this.checkAndInit();
    await this.waitForAccess(false);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    console.log(
      "Query: " +
        qid.toString() +
        " - " +
        type.toString() +
        " - " +
        id.toString()
    );
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.OBJECT_QUERY);
    writer.writeInteger(type);
    writer.writeBoolean(false);
    writer.writeInteger(usage);
    writer.writeInteger(id);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    console.log(
      "Done: " +
        qid.toString() +
        " - " +
        type.toString() +
        " - " +
        id.toString()
    );
    reader.readByte(); // Must be OBJECT_QUERY
    let res: number = reader.readInteger();
    this.releaseAccess(false);
    if (res === 0) {
      reader.start();
      let obj = reader.readRef(0, null);
      reader.done();
      return obj as T;
    } else {
      let errors: string[] = reader.readStringList();
      console.log("ObjectQuery Errors: " + errors.toString());
      return null;
    }
  }

  async waitForAccess(isMutation: boolean): Promise<boolean> {
    if (isMutation) {
      if (!MessageDispatch.allowParallelMutation) {
        if (
          this._mutationInProgress != null &&
          !this._mutationInProgress.isCompleted
        ) {
          await this._mutationInProgress.future;
        }
        this._mutationInProgress = new Completer();
      }
    }
    if (!MessageDispatch.allowParallelReq) {
      while (this._reqInProgress != null && !this._reqInProgress.isCompleted) {
        await this._reqInProgress.future;
      }
      this._reqInProgress = new Completer();
    }
    return true;
  }

  releaseAccess(isMutation: boolean): void {
    if (isMutation) {
      let wasBusy: boolean = this._pendingMutation > 2;
      this._pendingMutation--;
      let isBusy: boolean = this._pendingMutation > 2;
      if (wasBusy && !isBusy) {
        EventBus.get().fire(
          new ConnectionEvent({ status: ConnectionStatus.ConnectionNormal })
        );
      }
      this._mutationInProgress?.complete();
      this._mutationInProgress = null;
    }
    this._reqInProgress?.complete();
    this._reqInProgress = null;
  }

  async dataQuery<T extends DBObject>(
    query: string,
    usage: number,
    hasInput: boolean,
    input: DBObject,
    params?: Partial<{ synchronize: boolean }>
  ): Promise<T> {
    if (hasInput && input == null) {
      return null;
    }
    let synchronize = params?.synchronize || false;
    await this.checkAndInit();
    await this.waitForAccess(false);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    console.log(
      "DataQuery: " +
        qid.toString() +
        " - " +
        query +
        " - " +
        (hasInput ? input.d3eType : "null")
    );
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.DATA_QUERY);
    writer.writeString(query);
    writer.writeBoolean(synchronize);
    writer.writeInteger(usage);
    if (hasInput) {
      writer.writeObj(input);
    }
    this._client.send(writer.out);
    let reader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    console.log(
      "Done: " +
        qid.toString() +
        " - " +
        query +
        " - " +
        (hasInput ? input.d3eType : "null")
    );
    reader.readByte(); // Must be DATA_QUERY
    let res: number = reader.readInteger();
    this.releaseAccess(false);
    if (res === 0) {
      let subId: string;
      if (synchronize) {
        subId = reader.readString();
      }
      reader.start();
      let obj: DBObject = reader.readRef(0, null);
      if (synchronize && subId != null) {
        this.subscriptions.set(obj, subId);
      }
      reader.done();
      return obj as T;
    } else {
      let errors: string[] = reader.readStringList();
      console.log("DataQuery Errors: " + errors.toString());
      return null;
    }
  }

  checkAndSendBusy(): void {
    let wasBusy: boolean = this._pendingMutation > 2;
    this._pendingMutation++;
    let isBusy: boolean = this._pendingMutation > 2;
    if (isBusy && !wasBusy) {
      EventBus.get().fire(
        new ConnectionEvent({ status: ConnectionStatus.ConnectionBusy })
      );
    }
  }

  save = async (input: DBObject): Promise<Result<any>> => {
    this.checkAndSendBusy();
    await this.checkAndInit();
    await this.waitForAccess(true);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    console.log(
      "Save: " +
        qid.toString() +
        " - " +
        input.d3eType +
        " - " +
        input.id.toString()
    );
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.SAVE);
    writer.writeObjFull(input);
    let changes: Map<DBObject, D3EObjectChanges> = new Map();
    this.backupObjectChanges(input, changes);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    console.log(
      "Done: " +
        qid.toString() +
        " - " +
        input.d3eType +
        " - " +
        input.id.toString()
    );
    reader.readByte(); // Must be SAVE
    let res: number = reader.readByte();
    this.releaseAccess(true);
    if (res === 0) {
      let size: number = reader.readInteger();
      for (let i: number = 0; i < size; i++) {
        let type: number = reader.readInteger();
        let t: D3ETemplateType = D3ETemplate.types[type];
        let localId: number = reader.readInteger();
        let id: number = reader.readInteger();
        this._cache.updateLocalId(type, localId, id);
      }
      reader.start();
      let ref: DBObject = reader.readRef(0, null);
      reader.done();
      return new Result({ status: ResultStatus.Success });
    } else {
      this.restoreObjectChanges(input, changes);
      return new Result({
        status: ResultStatus.Errors,
        errors: reader.readStringList(),
      });
    }
  };

  public backupObjectChanges(
    obj: DBObject,
    changes: Map<DBObject, D3EObjectChanges>
  ): void {
    let type: number = D3ETemplate.typeInt(obj.d3eType);
    let tt: D3ETemplateType = D3ETemplate.types[type];
    while (tt != null) {
      changes.set(obj, obj.d3eChanges);
      obj.d3eChanges = new D3EObjectChanges();
      let index: number = tt.parentFields;
      for (let f of tt.fields) {
        if (f.child) {
          if (f.collection) {
            let values: DBObject[] = obj.get(index);
            values.forEach((e) => this.backupObjectChanges(e, changes));
          } else {
            let o: DBObject = obj.get(index);
            if (o != null) {
              this.backupObjectChanges(o, changes);
            }
          }
        }
        index++;
      }
      if (tt.parent !== -1) {
        tt = D3ETemplate.types[tt.parent];
      } else {
        tt = null;
      }
    }
  }

  public restoreObjectChanges(
    obj: DBObject,
    changes: Map<DBObject, D3EObjectChanges>
  ): void {
    let type: number = D3ETemplate.typeInt(obj.d3eType);
    let tt: D3ETemplateType = D3ETemplate.types[type];
    while (tt != null) {
      obj.d3eChanges = changes.get(obj);
      let index: number = tt.parentFields;
      for (let f of tt.fields) {
        if (f.child) {
          if (f.collection) {
            let values: DBObject[] = obj.get(index);
            values.forEach((e) => this.restoreObjectChanges(e, changes));
          } else {
            let o: DBObject = obj.get(index);
            if (o != null) {
              this.restoreObjectChanges(o, changes);
            }
          }
        }
        index++;
      }
      if (tt.parent !== -1) {
        tt = D3ETemplate.types[tt.parent];
      } else {
        tt = null;
      }
    }
  }

  public delete = async (input: DBObject): Promise<Result<any>> => {
    this.checkAndSendBusy();
    await this.checkAndInit();
    await this.waitForAccess(true);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.DELETE);
    let type: string = input.d3eType;
    let typeIdx: number = D3ETemplate.typeInt(type);
    writer.writeInteger(typeIdx);
    writer.writeInteger(input.id);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    reader.readByte(); // Must be DELETE
    let res: number = reader.readByte();
    this.releaseAccess(true);
    if (res === 0) {
      return new Result({ status: ResultStatus.Success });
    } else {
      return new Result({
        status: ResultStatus.Errors,
        errors: reader.readStringList(),
      });
    }
  };

  async login(
    type: string,
    usage: number,
    params?: Partial<{
      email: string;
      phone: string;
      username: string;
      password: string;
      deviceToken: string;
      token: string;
      code: string;
    }>
  ): Promise<LoginResult> {
    await this.checkAndInit();
    await this.waitForAccess(false);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.LOGIN);
    writer.writeInteger(usage);
    writer.writeString(type);
    writer.writeString(params?.email);
    writer.writeString(params?.phone);
    writer.writeString(params?.username);
    writer.writeString(params?.password);
    writer.writeString(params?.deviceToken);
    writer.writeString(params?.token);
    writer.writeString(params?.code);
    this._client.send(writer.out);
    let resp: Resp = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    );
    if (resp == null) {
      this.releaseAccess(false);
      return new LoginResult({
        success: false,
        failureMessage:
          "Server connection lost. Please refresh the page and try again.",
      });
    }
    let reader: WSReader = resp.reader;
    reader.readByte(); // Must be LOGIN
    let restInt: number = reader.readByte(); // Response
    this.releaseAccess(false);
    if (restInt === 1) {
      return new LoginResult({
        success: false,
        failureMessage: reader.readStringList().toString(),
      });
    }
    reader.start();
    let res: LoginResult = reader.readRef(0, null);
    reader.done();
    if (res.success) {
      try {
        LocalDataStore.get().setUser(res.userObject, res.token);
      } catch (e) {
        console.log("Exception: " + e.toString());
      }
    }
    return res;
  }

  public async logout(): Promise<void> {
    await this.checkAndInit();
    await this.waitForAccess(false);
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.LOGOUT);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    reader.readByte(); // Must be LOGOUT
    let restInt: number = reader.readByte(); // Response
    this.releaseAccess(false);
  }

  public syncObject(obj: DBObject, usage: number): D3EDisposable {
    let dis: ObjectSyncDisposable = new ObjectSyncDisposable(null);
    if (obj == null) {
      return dis;
    }
    console.log("Sync Object : " + obj.d3eType);
    Timer.run(async () => {
      let subId: string = await this._syncObject(obj, usage);
      if (subId != null) {
        if (dis.disposed) {
          this._unsubscribe(subId);
        } else {
          dis.subId = subId;
        }
      }
    });
    return dis;
  }

  private async _syncObject(obj: DBObject, usage: number): Promise<string> {
    await this.checkAndInit();
    await this.waitForAccess(false);
    let type: string = obj.d3eType;
    let typeIndex: number = D3ETemplate.typeInt(type);
    let id: number = obj.id;

    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    console.log(
      "Query: " +
        qid.toString() +
        " - " +
        type.toString() +
        " - " +
        id.toString()
    );
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.OBJECT_QUERY);
    writer.writeInteger(typeIndex);
    writer.writeBoolean(true);
    writer.writeInteger(usage);
    writer.writeInteger(id);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === qid
    ).then((m) => m.reader);
    console.log(
      "Done: " +
        qid.toString() +
        " - " +
        type.toString() +
        " - " +
        id.toString()
    );
    reader.readByte(); // Must be OBJECT_QUERY
    let res = reader.readInteger();
    this.releaseAccess(false);
    if (res === 0) {
      let subId: string = reader.readString();
      reader.start();
      reader.readRef(0, null);
      reader.done();
      return subId;
    } else {
      return null;
    }
  }

  public dispose(obj: DBObject): void {
    let subId: string = this.subscriptions.get(obj);
    if (subId != null) {
      this._unsubscribe(subId);
    }
  }

  async _unsubscribe(subId: string): Promise<void> {
    await this.checkAndInit();
    let writer: WSWriter = new WSWriter(this._cache);
    let qid: number = IdGenerator.get().next();
    console.log("Unsubscribe: " + qid.toString());
    writer.writeInteger(qid);
    writer.writeByte(MessageDispatch.UNSUBSCRIBE);
    writer.writeString(subId);
    this._client.send(writer.out);
  }

  public close(): void {
    this._status = _ConnectionStatus.Disconnected;
    this.subscriptions.clear();
    this._sessionId = null;
    this._client.disconnect();
  }

  public async connect(channelIdx: number): Promise<boolean> {
    await this.checkAndInit();
    let writer: WSWriter = new WSWriter(this._cache);
    let id: number = IdGenerator.get().next();
    writer.writeInteger(id);
    writer.writeByte(MessageDispatch.CONNECT);
    writer.writeInteger(channelIdx);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === id
    ).then((m) => m.reader);
    reader.readByte(); // CONNECT
    let code: number = reader.readByte();
    if (code === 1) {
      return false;
    }
    return true;
  }

  public async disconnect(channelIdx: number): Promise<boolean> {
    await this.checkAndInit();
    let writer: WSWriter = new WSWriter(this._cache);
    let id: number = IdGenerator.get().next();
    writer.writeInteger(id);
    writer.writeByte(MessageDispatch.DISCONNECT);
    writer.writeInteger(channelIdx);
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === id
    ).then((m) => m.reader);
    reader.readByte(); // DISCONNECT
    let code: number = reader.readByte();
    if (code === 1) {
      return false;
    }
    let result = reader.readBoolean();
    return result;
  }

  // Called when client wants to send a message to server
  public channelMessage(channelIdx: number, msgIdx: number): WSWriter {
    let writer: WSWriter = new WSWriter(this._cache);
    writer.writeInteger(MessageDispatch.CHANNEL_MESSAGE);
    writer.writeInteger(channelIdx);
    writer.writeInteger(msgIdx);
    return writer;
  }

  // RPC method
  public async rpcMessage(
    clsIdx: number,
    methodIdx: number,
    params?: Partial<{ args: Consumer<WSWriter> }>
  ): Promise<WSReader> {
    await this.checkAndInit();
    console.log(" w init done");
    let writer: WSWriter = new WSWriter(this._cache);
    let id: number = IdGenerator.get().next();
    writer.writeInteger(id);
    writer.writeByte(MessageDispatch.RPC_MESSAGE);
    console.log(
      " w RPC message: id: " +
        id.toString() +
        ", cls: " +
        clsIdx.toString() +
        ", method: " +
        methodIdx.toString()
    );
    writer.writeInteger(clsIdx);
    writer.writeInteger(methodIdx);
    if (params?.args != null) {
      params?.args(writer);
    }
    this._client.send(writer.out);
    let reader: WSReader = await StreamExt.firstWhere(
      this.respStream,
      (m: Resp) => m.id === id
    ).then((m) => m.reader);
    reader.readByte(); // RPC_MESSAGE
    return reader;
  }

  public send(w: WSWriter): void {
    if (w == null) {
      return;
    }
    this._client.send(w.out);
  }
}

GlobalFunctions.save = MessageDispatch.get().save;
GlobalFunctions.delete = MessageDispatch.get().delete;
