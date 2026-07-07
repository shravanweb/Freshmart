import Result from "../classes/Result";
import CloneContext from "./CloneContext";
import D3EObjectChanges from "./D3EObjectChanges";
import DBSaveStatus from "./DBSaveStatus";
import ObjectObservable from "./ObjectObservable";
import GlobalFunctions from "./GlobalFunctions";

export default abstract class DBObject extends ObjectObservable {
  public d3eChanges: D3EObjectChanges = new D3EObjectChanges();
  abstract get d3eType(): string;
  private _locked = 0;
  private _saveStatus: DBSaveStatus = DBSaveStatus.New;
  public abstract get id(): number;
  public abstract set id(id: number);
  public static structId: number = 0;
  public static nextStructId(): number {
    return ++this.structId;
  }
  public get saveStatus(): DBSaveStatus {
    return this._saveStatus;
  }
  public get isTransientModel(): boolean {
    return false;
  }
  public set saveStatus(saveStatus: DBSaveStatus) {
    this._saveStatus = saveStatus;
  }

  public get ident(): string {
    return this.d3eType + "-" + this.id.toString();
  }

  public toString(): string {
    return this.ident;
  }

  public collectChildValues(ctx: CloneContext): void {}
  public deepCloneIntoObj(obj: DBObject, ctx: CloneContext): void {}

  public updateChildChanges(index: number): void {
    if (!this.d3eChanges.contains(index)) {
      let val = this.get(index);
      if (Array.isArray(val)) {
        val = Array.from(val as Array<any>);
      }
      this.updateD3EChanges(index, val);
    }
  }

  public updateD3EChanges(index: number, value: any): void {
    if (this._locked > 0) {
      return;
    }
    GlobalFunctions.fieldChanged(this, index, value);
    //print('changed field $index in $d3eType');
    this.d3eChanges.add(index, value);
    if (this._saveStatus == DBSaveStatus.Saved) {
      this._saveStatus = DBSaveStatus.Changed;
    }
  }

  public lock(): void {
    this._locked++;
  }

  public lockedChanges(): boolean {
    return this._locked > 0;
  }

  public unlock(): void {
    this._locked--;
  }

  public clear(): void {}

  public async save(): Promise<Result<any>> {
    GlobalFunctions.objectSave(this, this.id <= 0);
    return await GlobalFunctions.save(this);
  }

  public async delete(): Promise<Result<any>> {
    GlobalFunctions.objectDelete(this);
    return await GlobalFunctions.delete(this);
  }

  public get(field: number): any {}
  public set(field: number, value: any): void {}

  public updateMaster(_master: DBObject, _field: number) {}
  public clearMaster() {}
}
