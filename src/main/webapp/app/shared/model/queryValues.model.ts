import { Language } from 'app/shared/model/enumerations/language.model';

export interface IQueryValues {
  text?: string;
  language?: Language;
}

export class QueryValues implements IQueryValues {
  constructor(public text?: string, public language?: Language) {}
}
