import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../shared/material.module';
import { NgxSpinnerModule } from 'ngx-spinner';

import { VisitantesappSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    imports: [VisitantesappSharedModule, RouterModule.forChild([HOME_ROUTE]), MaterialModule, NgxSpinnerModule],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappHomeModule {}
