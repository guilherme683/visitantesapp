import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Visitantes } from 'app/shared/model/visitantes.model';
import { VisitantesService } from './visitantes.service';
import { VisitantesComponent } from './visitantes.component';
import { VisitantesDetailComponent } from './visitantes-detail.component';
import { VisitantesUpdateComponent } from './visitantes-update.component';
import { VisitantesDeletePopupComponent } from './visitantes-delete-dialog.component';
import { IVisitantes } from 'app/shared/model/visitantes.model';

@Injectable({ providedIn: 'root' })
export class VisitantesResolve implements Resolve<IVisitantes> {
    constructor(private service: VisitantesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((visitantes: HttpResponse<Visitantes>) => visitantes.body));
        }
        return of(new Visitantes());
    }
}

export const visitantesRoute: Routes = [
    {
        path: 'visitantes',
        component: VisitantesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visitantesappApp.visitantes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visitantes/:id/view',
        component: VisitantesDetailComponent,
        resolve: {
            visitantes: VisitantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.visitantes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visitantes/new',
        component: VisitantesUpdateComponent,
        resolve: {
            visitantes: VisitantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.visitantes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visitantes/:id/edit',
        component: VisitantesUpdateComponent,
        resolve: {
            visitantes: VisitantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.visitantes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitantesPopupRoute: Routes = [
    {
        path: 'visitantes/:id/delete',
        component: VisitantesDeletePopupComponent,
        resolve: {
            visitantes: VisitantesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.visitantes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
