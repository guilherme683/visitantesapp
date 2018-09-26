import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisitantesappSharedModule } from 'app/shared';
import {
    AcessoresComponent,
    AcessoresDetailComponent,
    AcessoresUpdateComponent,
    AcessoresDeletePopupComponent,
    AcessoresDeleteDialogComponent,
    acessoresRoute,
    acessoresPopupRoute
} from './';

const ENTITY_STATES = [...acessoresRoute, ...acessoresPopupRoute];

@NgModule({
    imports: [VisitantesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AcessoresComponent,
        AcessoresDetailComponent,
        AcessoresUpdateComponent,
        AcessoresDeleteDialogComponent,
        AcessoresDeletePopupComponent
    ],
    entryComponents: [AcessoresComponent, AcessoresUpdateComponent, AcessoresDeleteDialogComponent, AcessoresDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappAcessoresModule {}
