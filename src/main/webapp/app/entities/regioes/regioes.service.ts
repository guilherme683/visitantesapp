import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegioes } from 'app/shared/model/regioes.model';

type EntityResponseType = HttpResponse<IRegioes>;
type EntityArrayResponseType = HttpResponse<IRegioes[]>;

@Injectable({ providedIn: 'root' })
export class RegioesService {
    private resourceUrl = SERVER_API_URL + 'api/regioes';

    constructor(private http: HttpClient) {}

    create(regioes: IRegioes): Observable<EntityResponseType> {
        return this.http.post<IRegioes>(this.resourceUrl, regioes, { observe: 'response' });
    }

    update(regioes: IRegioes): Observable<EntityResponseType> {
        return this.http.put<IRegioes>(this.resourceUrl, regioes, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegioes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegioes[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
