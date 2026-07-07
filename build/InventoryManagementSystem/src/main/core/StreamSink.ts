export default class StreamSink<S> {
  close(): Promise<S> {
    return null;
  }

  get done(): Promise<S> {
    return null;
  }
}
