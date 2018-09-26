import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisitantesappSharedModule } from 'app/shared';
import {
    PartidoComponent,
    PartidoDetailComponent,
    PartidoUpdateComponent,
    PartidoDeletePopupComponent,
    PartidoDeleteDialogComponent,
    partidoRoute,
    partidoPopupRoute
} from './';

const ENTITY_STATES = [...partidoRoute, ...partidoPopupRoute];

@NgModule({
    imports: [VisitantesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartidoComponent,
        PartidoDetailComponent,
        PartidoUpdateComponent,
        PartidoDeleteDialogComponent,
        PartidoDeletePopupComponent
    ],
    entryComponents: [PartidoComponent, PartidoUpdateComponent, PartidoDeleteDialogComponent, PartidoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappPartidoModule {}
