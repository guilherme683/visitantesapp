import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisitantesappSharedModule } from 'app/shared';
import {
    RegioesComponent,
    RegioesDetailComponent,
    RegioesUpdateComponent,
    RegioesDeletePopupComponent,
    RegioesDeleteDialogComponent,
    regioesRoute,
    regioesPopupRoute
} from './';

const ENTITY_STATES = [...regioesRoute, ...regioesPopupRoute];

@NgModule({
    imports: [VisitantesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegioesComponent,
        RegioesDetailComponent,
        RegioesUpdateComponent,
        RegioesDeleteDialogComponent,
        RegioesDeletePopupComponent
    ],
    entryComponents: [RegioesComponent, RegioesUpdateComponent, RegioesDeleteDialogComponent, RegioesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappRegioesModule {}
