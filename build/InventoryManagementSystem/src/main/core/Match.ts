export default class Match {
  constructor(
    public match: string,
    public matchGroups: string[],
    public input: string,
    public start: number,
    public pattern: RegExp
  ) {}

  get end(): number {
    return this.start + this.match.length - 1;
  }

  group(group: number): string {
    return this.matchGroups[group] || "";
  }

  get(group: number): string {
    return this.group(group);
  }

  groups(groupIndices: Array<number>): Array<string> {
    return groupIndices.map((one) => this.group(one)).toList();
  }

  get groupCount(): number {
    return this.matchGroups?.length || 0;
  }
}

// export default Match
