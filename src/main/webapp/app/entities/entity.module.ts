import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VisitantesappVisitantesModule } from './visitantes/visitantes.module';
import { VisitantesappAcessoresModule } from './acessores/acessores.module';
import { VisitantesappCargoModule } from './cargo/cargo.module';
import { VisitantesappMunicipiosModule } from './municipios/municipios.module';
import { VisitantesappEstadosModule } from './estados/estados.module';
import { VisitantesappRegioesModule } from './regioes/regioes.module';
import { VisitantesappPartidoModule } from './partido/partido.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VisitantesappVisitantesModule,
        VisitantesappAcessoresModule,
        VisitantesappCargoModule,
        VisitantesappMunicipiosModule,
        VisitantesappEstadosModule,
        VisitantesappRegioesModule,
        VisitantesappPartidoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisitantesappEntityModule {}
