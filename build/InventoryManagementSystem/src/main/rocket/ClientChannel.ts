import MessageDispatch from "./MessageDispatch";

export type DisconnectCallBack = () => void;

export default class ClientChannel<S, C> {
  _client: C;

  public constructor(
    public channelIdx: number,
    public server: S,
    public onDisconnect: DisconnectCallBack
  ) {}

  public async connect(client: C): Promise<S> {
    this._client = client;
    await MessageDispatch.get().connect(this.channelIdx);
    return this.server;
  }

  public async disconnect(): Promise<boolean> {
    await MessageDispatch.get().disconnect(this.channelIdx);
    this.onDisconnect();
    return true;
  }

  public get client(): C {
    return this._client;
  }
}
