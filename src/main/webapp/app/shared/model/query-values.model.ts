export interface IQueryValues {
  root?: string;
  ending?: string;
  hasCyrToLat?: boolean;
}

export class QueryValues implements IQueryValues {
  constructor(public root?: string, public ending?: string, public hasCyrToLat?: boolean) {}
}
