import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Municipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';
import { MunicipiosComponent } from './municipios.component';
import { MunicipiosDetailComponent } from './municipios-detail.component';
import { MunicipiosUpdateComponent } from './municipios-update.component';
import { MunicipiosDeletePopupComponent } from './municipios-delete-dialog.component';
import { IMunicipios } from 'app/shared/model/municipios.model';

@Injectable({ providedIn: 'root' })
export class MunicipiosResolve implements Resolve<IMunicipios> {
    constructor(private service: MunicipiosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((municipios: HttpResponse<Municipios>) => municipios.body));
        }
        return of(new Municipios());
    }
}

export const municipiosRoute: Routes = [
    {
        path: 'municipios',
        component: MunicipiosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.municipios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'municipios/:id/view',
        component: MunicipiosDetailComponent,
        resolve: {
            municipios: MunicipiosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.municipios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'municipios/new',
        component: MunicipiosUpdateComponent,
        resolve: {
            municipios: MunicipiosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.municipios.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'municipios/:id/edit',
        component: MunicipiosUpdateComponent,
        resolve: {
            municipios: MunicipiosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.municipios.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const municipiosPopupRoute: Routes = [
    {
        path: 'municipios/:id/delete',
        component: MunicipiosDeletePopupComponent,
        resolve: {
            municipios: MunicipiosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visitantesappApp.municipios.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
