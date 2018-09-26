import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAcessores } from 'app/shared/model/acessores.model';

type EntityResponseType = HttpResponse<IAcessores>;
type EntityArrayResponseType = HttpResponse<IAcessores[]>;

@Injectable({ providedIn: 'root' })
export class AcessoresService {
    private resourceUrl = SERVER_API_URL + 'api/acessores';

    constructor(private http: HttpClient) {}

    create(acessores: IAcessores): Observable<EntityResponseType> {
        return this.http.post<IAcessores>(this.resourceUrl, acessores, { observe: 'response' });
    }

    update(acessores: IAcessores): Observable<EntityResponseType> {
        return this.http.put<IAcessores>(this.resourceUrl, acessores, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAcessores>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcessores[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
