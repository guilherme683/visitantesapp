import { Moment } from 'moment';
import { IAcessores } from 'app/shared/model//acessores.model';
import { IMunicipios } from 'app/shared/model//municipios.model';
import { ICargo } from 'app/shared/model//cargo.model';
import { IPartido } from 'app/shared/model//partido.model';

export const enum Status {
    AGUARDANDO = 'AGUARDANDO',
    ATENDIDO = 'ATENDIDO',
    NAO_ATENDIDO = 'NAO_ATENDIDO'
}

export interface IVisitantes {
    id?: number;
    dataVisita?: Moment;
    nomeVisitante?: string;
    local?: string;
    acompanhadoPor?: string;
    estado?: string;
    statusAtendimento?: Status;
    cadastradoPor?: string;
    observacao?: string;
    acessores?: IAcessores;
    municipios?: IMunicipios;
    cargo?: ICargo;
    partido?: IPartido;
}

export class Visitantes implements IVisitantes {
    constructor(
        public id?: number,
        public dataVisita?: Moment,
        public nomeVisitante?: string,
        public local?: string,
        public acompanhadoPor?: string,
        public estado?: string,
        public statusAtendimento?: Status,
        public cadastradoPor?: string,
        public observacao?: string,
        public acessores?: IAcessores,
        public municipios?: IMunicipios,
        public cargo?: ICargo,
        public partido?: IPartido
    ) {}
}
