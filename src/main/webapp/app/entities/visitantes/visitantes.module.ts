import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisitantesappSharedModule } from 'app/shared';
import {
    VisitantesComponent,
    VisitantesDetailComponent,
    VisitantesUpdateComponent,
    VisitantesDeletePopupComponent,
    VisitantesDeleteDialogComponent,
    visitantesRoute,
    visitantesPopupRoute
} from './';

const ENTITY_STATES = [...visitantesRoute, ...visitantesPopupRoute];

@NgModule({
    imports: [VisitantesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VisitantesComponent,
        VisitantesDetailComponent,
        VisitantesUpdateComponent,
        VisitantesDeleteDialogComponent,
        VisitantesDeletePopupComponent
    ],
    entryComponents: [VisitantesComponent, VisitantesUpdateComponent, VisitantesDeleteDialogComponent, VisitantesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappVisitantesModule {}
