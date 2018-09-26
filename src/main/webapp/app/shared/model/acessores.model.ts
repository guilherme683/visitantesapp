export interface IAcessores {
    id?: number;
    nomeAssessor?: string;
}

export class Acessores implements IAcessores {
    constructor(public id?: number, public nomeAssessor?: string) {}
}
