export default class Random {
  rnd;

  constructor(seed?: number) {
    this.rnd = require("random-seed").create();
    if (seed) {
      this.rnd.seed(seed);
    }
  }

  static secure(): Random {
    return new Random();
  }

  nextInt(max: number): number {
    return this.rnd.range(max);
  }

  nextDouble(): number {
    return this.rnd.random();
  }

  nextBool(): boolean {
    // Naive?
    return this.nextDouble() > 0.5;
  }
}
