import { Language } from 'app/shared/model/enumerations/language.model';

export interface IFormParams {
  text?: string;
  language?: Language;
}

export class FormParams implements IFormParams {
  constructor(public text?: string, public language?: Language) {}
}
