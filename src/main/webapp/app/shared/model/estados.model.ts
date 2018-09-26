import { IRegioes } from 'app/shared/model//regioes.model';

export interface IEstados {
    id?: number;
    codEstadosIbge?: number;
    nomeEstado?: string;
    siglaEstado?: string;
    codRegioes?: number;
    regioes?: IRegioes;
}

export class Estados implements IEstados {
    constructor(
        public id?: number,
        public codEstadosIbge?: number,
        public nomeEstado?: string,
        public siglaEstado?: string,
        public codRegioes?: number,
        public regioes?: IRegioes
    ) {}
}
