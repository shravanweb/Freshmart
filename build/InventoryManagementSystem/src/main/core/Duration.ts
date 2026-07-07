class Duration {
  static readonly microsecondsPerMillisecond = 1000;

  static readonly millisecondsPerSecond = 1000;

  static readonly secondsPerMinute = 60;

  static readonly minutesPerHour = 60;

  static readonly hoursPerDay = 24;

  static readonly microsecondsPerSecond =
    Duration.microsecondsPerMillisecond * Duration.millisecondsPerSecond;

  static readonly microsecondsPerMinute =
    Duration.microsecondsPerSecond * Duration.secondsPerMinute;

  static readonly microsecondsPerHour =
    Duration.microsecondsPerMinute * Duration.minutesPerHour;

  static readonly microsecondsPerDay =
    Duration.microsecondsPerHour * Duration.hoursPerDay;

  static readonly millisecondsPerMinute =
    Duration.millisecondsPerSecond * Duration.secondsPerMinute;

  static readonly millisecondsPerHour =
    Duration.millisecondsPerMinute * Duration.minutesPerHour;

  static readonly millisecondsPerDay =
    Duration.millisecondsPerHour * Duration.hoursPerDay;

  static readonly secondsPerHour =
    Duration.secondsPerMinute * Duration.minutesPerHour;

  static readonly secondsPerDay =
    Duration.secondsPerHour * Duration.hoursPerDay;

  static readonly minutesPerDay =
    Duration.minutesPerHour * Duration.hoursPerDay;

  static readonly zero: Duration = new Duration({});

  // The total microseconds of this [Duration] object.
  private duration: number;

  constructor(props?: {
    days?: number;
    hours?: number;
    minutes?: number;
    seconds?: number;
    milliseconds?: number;
    microseconds?: number;
  }) {
    this.duration = 0;
    if (props) {
      if (props.microseconds) {
        this.duration = props.microseconds;
      }
      if (props.milliseconds) {
        this.duration +=
          Duration.microsecondsPerMillisecond * props.milliseconds;
      }
      if (props.seconds) {
        this.duration += Duration.microsecondsPerSecond * props.seconds;
      }
      if (props.minutes) {
        this.duration += Duration.microsecondsPerMinute * props.minutes;
      }
      if (props.hours) {
        this.duration += Duration.microsecondsPerHour * props.hours;
      }
      if (props.days) {
        this.duration += Duration.microsecondsPerDay * props.days;
      }
    }
  }

  static _milliseconds(milli: number) {
    return new Duration({
      milliseconds: milli,
    });
  }

  static _microseconds(micro: number) {
    return new Duration({
      microseconds: micro,
    });
  }

  plus(other: Duration): Duration {
    return Duration._microseconds(this.duration + other.duration);
  }

  minus(other: Duration): Duration {
    return Duration._microseconds(this.duration - other.duration);
  }

  times(factor: number): Duration {
    return Duration._microseconds(this.duration * factor);
  }

  divToInt(quotient: number): Duration {
    if (quotient === 0) {
      return this;
    }
    return Duration._microseconds((this.duration / quotient).toInt());
  }

  get inDays(): number {
    return this.inHours / 24;
  }

  get inHours(): number {
    return this.inMinutes / 60;
  }

  get inMinutes(): number {
    return this.inSeconds / 60;
  }

  get inSeconds(): number {
    return this.inMilliseconds / 1000;
  }

  get inMilliseconds(): number {
    return this.inMicroseconds / 1000;
  }

  get inMicroseconds(): number {
    return this.duration;
  }

  get hashCode(): number {
    return 0;
  }

  compareTo(other: Duration): number {
    return 0;
  }

  toString(): string {
    return "";
  }

  get isNegative(): boolean {
    return this.inMicroseconds < 0;
  }

  abs(): Duration {
    return this.isNegative
      ? new Duration({ microseconds: this.inMicroseconds })
      : this;
  }

  negate(): Duration {
    return this.times(-1);
  }
  isEqual(other: Duration): boolean {
    return this.toMilliseconds() === other.toMilliseconds();
  }
  toMilliseconds(): number {
    return (
      this.inHours * 3600000 + this.inMinutes * 60000 + this.inSeconds * 1000
    );
  }
}
export default Duration;
