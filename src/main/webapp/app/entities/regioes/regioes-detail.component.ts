import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegioes } from 'app/shared/model/regioes.model';

@Component({
    selector: 'jhi-regioes-detail',
    templateUrl: './regioes-detail.component.html'
})
export class RegioesDetailComponent implements OnInit {
    regioes: IRegioes;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regioes }) => {
            this.regioes = regioes;
        });
    }

    previousState() {
        window.history.back();
    }
}
