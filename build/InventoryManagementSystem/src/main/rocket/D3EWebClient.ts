import { Observable, Subject } from "rxjs";
import { webSocket, WebSocketSubject } from "rxjs/webSocket";
import { Consumer, OneFunction } from "../classes/core";
import Env from "../classes/Env";
import Completer from "../core/Completer";

export default class D3EWebClient {
  private _url: string;

  private _channel: Subject<ArrayBuffer>;

  ws: WebSocket;
  _ready: Promise<boolean>;
  readyResolver: Consumer<boolean>;

  private static _init(url: string): D3EWebClient {
    let c = new D3EWebClient();
    c._url = url;
    c._connect();
    return c;
  }

  private static _ins: D3EWebClient;

  public static get(): D3EWebClient {
    if (D3EWebClient._ins == null) {
      let url: string = Env.get().resolvedWSurl + "/api/rocket";
      D3EWebClient._ins = D3EWebClient._init(url);
    }
    return D3EWebClient._ins;
  }

  private _connect(): void {
    this._ready = new Promise<boolean>((res, rej) => {
      this.readyResolver = res;
    });
    this._channel = new Subject<ArrayBuffer>();
    let ws = new WebSocket(this._url);
    ws.binaryType = "arraybuffer";
    ws.addEventListener("message", (event) => {
      if (event.data instanceof ArrayBuffer) {
        this._channel.next(event.data);
      } else {
        console.log(event.data);
      }
    });
    ws.onopen = () => {
      this.ws = ws;
      this.readyResolver(true);
    };
    ws.onclose = () => {
      this._channel.complete();
    };
  }

  public ready(): Promise<boolean> {
    return this._ready;
  }

  public disconnect(): void {
    try {
      this.ws.close();
    } catch (e) {}
    this._channel = null;
    D3EWebClient._ins = null;
  }

  public send(data: Uint8Array): void {
    this.ws.send(data.buffer);
  }

  public broadcastStream(): Observable<ArrayBuffer> {
    return this._channel;
  }
}
