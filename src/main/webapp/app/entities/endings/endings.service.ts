import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEndings } from 'app/shared/model/endings.model';
import { IQueryValues } from 'app/shared/model/queryValues.model';

type EntityResponseType = HttpResponse<IEndings>;
type EntityArrayResponseType = HttpResponse<IEndings[]>;

@Injectable({ providedIn: 'root' })
export class EndingsService {
  public resourceUrl = SERVER_API_URL + 'api/endings';

  constructor(protected http: HttpClient) {}

  create(endings: IEndings): Observable<EntityResponseType> {
    return this.http.post<IEndings>(this.resourceUrl, endings, { observe: 'response' });
  }

  update(endings: IEndings): Observable<EntityResponseType> {
    return this.http.put<IEndings>(this.resourceUrl, endings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEndings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStem(queryValues: IQueryValues): Observable<HttpResponse<IQueryValues>> {
    let options: HttpParams = new HttpParams();
    Object.keys(queryValues).forEach(key => {
      options = options.set(key, queryValues[key].toString());
    });
    console.warn(`${this.resourceUrl}/getStem?` + options);
    return this.http.get<IQueryValues>(`${this.resourceUrl}/getStem?`, { observe: 'response', params: options });
    // return this.http.get<IQueryValues>(`${this.resourceUrl}/getStem`, queryValues, {observe: 'response' });
  }
}
