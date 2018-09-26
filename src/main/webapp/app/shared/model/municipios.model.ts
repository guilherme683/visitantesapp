import { IEstados } from 'app/shared/model//estados.model';

export interface IMunicipios {
    id?: number;
    codMunicipiosIbge?: number;
    nomeMunicipio?: string;
    codEstadosIbge?: number;
    estados?: IEstados;
}

export class Municipios implements IMunicipios {
    constructor(
        public id?: number,
        public codMunicipiosIbge?: number,
        public nomeMunicipio?: string,
        public codEstadosIbge?: number,
        public estados?: IEstados
    ) {}
}
