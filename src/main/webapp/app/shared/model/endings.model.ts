import { Language } from 'app/shared/model/enumerations/language.model';

export interface IEndings {
  id?: number;
  name?: string;
  numberOfTypes?: number;
  language?: Language;
}

export class Endings implements IEndings {
  constructor(public id?: number, public name?: string, public numberOfTypes?: number, public language?: Language) {}
}
