import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVisitantes } from 'app/shared/model/visitantes.model';

type EntityResponseType = HttpResponse<IVisitantes>;
type EntityArrayResponseType = HttpResponse<IVisitantes[]>;

@Injectable({ providedIn: 'root' })
export class VisitantesService {
    private resourceUrl = SERVER_API_URL + 'api/visitantes';

    constructor(private http: HttpClient) {}

    create(visitantes: IVisitantes): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(visitantes);
        return this.http
            .post<IVisitantes>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(visitantes: IVisitantes): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(visitantes);
        return this.http
            .put<IVisitantes>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVisitantes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVisitantes[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(visitantes: IVisitantes): IVisitantes {
        const copy: IVisitantes = Object.assign({}, visitantes, {
            dataVisita: visitantes.dataVisita != null && visitantes.dataVisita.isValid() ? visitantes.dataVisita.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dataVisita = res.body.dataVisita != null ? moment(res.body.dataVisita) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((visitantes: IVisitantes) => {
            visitantes.dataVisita = visitantes.dataVisita != null ? moment(visitantes.dataVisita) : null;
        });
        return res;
    }
}
