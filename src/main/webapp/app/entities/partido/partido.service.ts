import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartido } from 'app/shared/model/partido.model';

type EntityResponseType = HttpResponse<IPartido>;
type EntityArrayResponseType = HttpResponse<IPartido[]>;

@Injectable({ providedIn: 'root' })
export class PartidoService {
    private resourceUrl = SERVER_API_URL + 'api/partidos';

    constructor(private http: HttpClient) {}

    create(partido: IPartido): Observable<EntityResponseType> {
        return this.http.post<IPartido>(this.resourceUrl, partido, { observe: 'response' });
    }

    update(partido: IPartido): Observable<EntityResponseType> {
        return this.http.put<IPartido>(this.resourceUrl, partido, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPartido[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
