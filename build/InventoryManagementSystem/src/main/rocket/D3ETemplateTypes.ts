import { Supplier } from "../classes/core";
import DBObject from "../utils/DBObject";
import { D3ETemplate } from "./D3ETemplate";

export enum D3EFieldType {
  String,
  Integer,
  Double,
  Boolean,
  Date,
  DateTime,
  Time,
  Duration,
  DFile,
  Enum,
  Ref,
  Blob,
  Geolocation,
}

export enum D3ERefType {
  Model,
  Struct,
  Enum,
}

export class D3ETemplateField {
  public name: string;
  public type: number;
  public child: boolean;
  public collection: boolean;
  public inverse: boolean;
  unknown: boolean = false;
  public fieldType: D3EFieldType;
  constructor(
    name: string,
    type: number,
    fieldType: D3EFieldType,
    params?: Partial<{
      child?: boolean;
      collection?: boolean;
      inverse?: boolean;
    }>
  ) {
    this.name = name;
    this.type = type;
    this.fieldType = fieldType;
    this.child = params?.child || false;
    this.collection = params?.collection || false;
    this.inverse = params?.inverse || false;
  }
}

export class D3EUsage {
  constructor(
    public name: string,
    public types: D3ETypeUsage[],
    public hash: string
  ) {}
}

export class D3ETypeUsage {
  constructor(public type: number, public fields: D3EFieldUsage[]) {}
}

export class D3EFieldUsage {
  constructor(public field: number, public types: D3ETypeUsage[]) {}
}

export class D3ETemplateType {
  fieldMap: Map<string, number>;
  public embedded: boolean;
  public parent: number;
  public abstract: boolean;
  public parentFields: number;
  public refType: D3ERefType;
  public creator: Supplier<DBObject>;
  unknown: boolean = false;
  constructor(
    public name: string,
    public hash: string,
    public fields: D3ETemplateField[],
    params?: Partial<{
      embedded: boolean;
      parent: number;
      abstract: boolean;
      refType: D3ERefType;
      parentFields: number;
      creator: Supplier<DBObject>;
    }>
  ) {
    this.name = name;
    this.hash = hash;
    this.fields = fields;
    this.embedded = params?.embedded || false;
    if (params?.parent == undefined) {
      this.parent = -1;
    } else {
      this.parent = params?.parent;
    }
    this.abstract = params?.abstract || false;
    this.refType = params?.refType;
    if (params?.parentFields == undefined) {
      this.parentFields = 0;
    } else {
      this.parentFields = params?.parentFields;
    }
    this.creator = params?.creator;
    this.fieldMap = Map.fromIterables(
      fields.map((x) => x.name),
      Array.generate(fields.length, (index) => index + this.parentFields)
    );
  }

  public get(index: number): D3ETemplateField {
    if (index < this.parentFields) {
      return D3ETemplate.types[this.parent].get(index);
    }
    return this.fields[index - this.parentFields];
  }
}

export class D3ETemplateClass {
  constructor(
    public name: string,
    public hash: string,
    public methods: D3ETemplateMethod[]
  ) {}
}

export class D3ETemplateMethod {
  constructor(public name: string, public params: D3ETemplateParam[]) {}
}

export class D3ETemplateMethodWithReturn extends D3ETemplateMethod {
  public returnCollection: boolean;

  constructor(
    name: string,
    params: D3ETemplateParam[],
    public returnType: number,
    namedParams?: Partial<{ returnCollection: boolean }>
  ) {
    super(name, params);
    this.returnCollection = namedParams?.returnCollection || false;
  }

  // void will be -1
  public isVoidReturn(): boolean {
    return this.returnType == -1;
  }
}

export class D3ETemplateParam {
  public collection: boolean;
  constructor(public type: number, params?: Partial<{ collection: boolean }>) {
    this.collection = params?.collection || false;
  }
}
