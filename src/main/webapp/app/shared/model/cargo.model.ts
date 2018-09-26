export interface ICargo {
    id?: number;
    nomeCargo?: string;
}

export class Cargo implements ICargo {
    constructor(public id?: number, public nomeCargo?: string) {}
}
