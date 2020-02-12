import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdminAccount } from 'app/shared/model/admin-account.model';

type EntityResponseType = HttpResponse<IAdminAccount>;
type EntityArrayResponseType = HttpResponse<IAdminAccount[]>;

@Injectable({ providedIn: 'root' })
export class AdminAccountService {
  public resourceUrl = SERVER_API_URL + 'api/admin-accounts';

  constructor(protected http: HttpClient) {}

  create(adminAccount: IAdminAccount): Observable<EntityResponseType> {
    return this.http.post<IAdminAccount>(this.resourceUrl, adminAccount, { observe: 'response' });
  }

  update(adminAccount: IAdminAccount): Observable<EntityResponseType> {
    return this.http.put<IAdminAccount>(this.resourceUrl, adminAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
