import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Regioes } from 'app/shared/model/regioes.model';
import { RegioesService } from './regioes.service';
import { RegioesComponent } from './regioes.component';
import { RegioesDetailComponent } from './regioes-detail.component';
import { RegioesUpdateComponent } from './regioes-update.component';
import { RegioesDeletePopupComponent } from './regioes-delete-dialog.component';
import { IRegioes } from 'app/shared/model/regioes.model';

@Injectable({ providedIn: 'root' })
export class RegioesResolve implements Resolve<IRegioes> {
    constructor(private service: RegioesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((regioes: HttpResponse<Regioes>) => regioes.body));
        }
        return of(new Regioes());
    }
}

export const regioesRoute: Routes = [
    {
        path: 'regioes',
        component: RegioesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.regioes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regioes/:id/view',
        component: RegioesDetailComponent,
        resolve: {
            regioes: RegioesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.regioes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regioes/new',
        component: RegioesUpdateComponent,
        resolve: {
            regioes: RegioesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.regioes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'regioes/:id/edit',
        component: RegioesUpdateComponent,
        resolve: {
            regioes: RegioesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.regioes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regioesPopupRoute: Routes = [
    {
        path: 'regioes/:id/delete',
        component: RegioesDeletePopupComponent,
        resolve: {
            regioes: RegioesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.regioes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
