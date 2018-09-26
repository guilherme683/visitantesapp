export interface IPartido {
    id?: number;
    nomePartido?: string;
    siglaPartido?: string;
}

export class Partido implements IPartido {
    constructor(public id?: number, public nomePartido?: string, public siglaPartido?: string) {}
}
