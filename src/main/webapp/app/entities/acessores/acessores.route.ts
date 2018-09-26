import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Acessores } from 'app/shared/model/acessores.model';
import { AcessoresService } from './acessores.service';
import { AcessoresComponent } from './acessores.component';
import { AcessoresDetailComponent } from './acessores-detail.component';
import { AcessoresUpdateComponent } from './acessores-update.component';
import { AcessoresDeletePopupComponent } from './acessores-delete-dialog.component';
import { IAcessores } from 'app/shared/model/acessores.model';

@Injectable({ providedIn: 'root' })
export class AcessoresResolve implements Resolve<IAcessores> {
    constructor(private service: AcessoresService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((acessores: HttpResponse<Acessores>) => acessores.body));
        }
        return of(new Acessores());
    }
}

export const acessoresRoute: Routes = [
    {
        path: 'acessores',
        component: AcessoresComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visitantesappApp.acessores.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'acessores/:id/view',
        component: AcessoresDetailComponent,
        resolve: {
            acessores: AcessoresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.acessores.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'acessores/new',
        component: AcessoresUpdateComponent,
        resolve: {
            acessores: AcessoresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.acessores.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'acessores/:id/edit',
        component: AcessoresUpdateComponent,
        resolve: {
            acessores: AcessoresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.acessores.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const acessoresPopupRoute: Routes = [
    {
        path: 'acessores/:id/delete',
        component: AcessoresDeletePopupComponent,
        resolve: {
            acessores: AcessoresResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.acessores.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
