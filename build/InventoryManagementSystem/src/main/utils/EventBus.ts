import { Observable, Subject } from "rxjs";
import { filter } from "rxjs/operators";
import Type from "../core/Type";
/// Dispatches events to listeners using the Dart [Stream] API. The [EventBus]
/// enables decoupled applications. It allows objects to interact without
/// requiring to explicitly define listeners and keeping track of them.
///
/// Not all events should be broadcasted through the [EventBus] but only those of
/// general interest.
///
/// Events are normal Dart objects. By specifying a class, listeners can
/// filter events.
///
export default class EventBus {
  private static _instance: EventBus;
  public static get(): EventBus {
    if (EventBus._instance == null) {
      EventBus._instance = new EventBus();
    }
    return EventBus._instance;
  }

  private _obs: Subject<any>;

  /// Controller for the event bus stream.
  public get obs(): Observable<any> {
    return this._obs;
  }

  /// Creates an [EventBus].
  ///
  /// If [sync] is true, events are passed directly to the stream's listeners
  /// during a [fire] call. If false (the default), the event will be passed to
  /// the listeners at a later time, after the code creating the event has
  /// completed.
  private constructor(params?: Partial<{ sync: boolean }>) {
    this._obs = new Subject();
  }

  /// Instead of using the default [StreamController] you can use this constructor
  /// to pass your own controller.
  ///
  /// An example would be to use an RxDart Subject as the controller.
  // EventBus.customController(StreamController controller)
  //     : _streamController = controller;

  /// Listens for events of Type [T] and its subtypes.
  ///
  /// The method is called like this: myEventBus.on<MyType>();
  ///
  /// If the method is called without a type parameter, the [Stream] contains every
  /// event of this [EventBus].
  ///
  /// The returned [Stream] is a broadcast stream so multiple subscriptions are
  /// allowed.
  ///
  /// Each listener is handled independently, and if they pause, only the pausing
  /// listener is affected. A paused listener will buffer events internally until
  /// unpaused or canceled. So it's usually better to just cancel and later
  /// subscribe again (avoids memory leak).
  ///

  public on<T>(typeCheck: Type): Observable<T> {
    if (!typeCheck) {
      return this._obs;
    } else {
      let t: any = typeCheck;
      return this._obs.pipe(filter((event) => event instanceof t));
    }
  }

  /// Fires a new event on the event bus with the specified [event].
  ///
  public fire(event): void {
    this._obs.next(event);
  }

  /// Destroy this [EventBus]. This is generally only in a testing context.
  ///
  public destroy(): void {
    // TODO
  }
}
