import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Partido } from 'app/shared/model/partido.model';
import { PartidoService } from './partido.service';
import { PartidoComponent } from './partido.component';
import { PartidoDetailComponent } from './partido-detail.component';
import { PartidoUpdateComponent } from './partido-update.component';
import { PartidoDeletePopupComponent } from './partido-delete-dialog.component';
import { IPartido } from 'app/shared/model/partido.model';

@Injectable({ providedIn: 'root' })
export class PartidoResolve implements Resolve<IPartido> {
    constructor(private service: PartidoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partido: HttpResponse<Partido>) => partido.body));
        }
        return of(new Partido());
    }
}

export const partidoRoute: Routes = [
    {
        path: 'partido',
        component: PartidoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.partido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partido/:id/view',
        component: PartidoDetailComponent,
        resolve: {
            partido: PartidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.partido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partido/new',
        component: PartidoUpdateComponent,
        resolve: {
            partido: PartidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.partido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partido/:id/edit',
        component: PartidoUpdateComponent,
        resolve: {
            partido: PartidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.partido.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partidoPopupRoute: Routes = [
    {
        path: 'partido/:id/delete',
        component: PartidoDeletePopupComponent,
        resolve: {
            partido: PartidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.partido.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
