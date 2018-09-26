export interface IRegioes {
    id?: number;
    codRegioesIbge?: number;
    nomeRegiao?: string;
}

export class Regioes implements IRegioes {
    constructor(public id?: number, public codRegioesIbge?: number, public nomeRegiao?: string) {}
}
