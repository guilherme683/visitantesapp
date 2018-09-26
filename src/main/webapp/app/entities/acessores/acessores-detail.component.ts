import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcessores } from 'app/shared/model/acessores.model';

@Component({
    selector: 'jhi-acessores-detail',
    templateUrl: './acessores-detail.component.html'
})
export class AcessoresDetailComponent implements OnInit {
    acessores: IAcessores;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ acessores }) => {
            this.acessores = acessores;
        });
    }

    previousState() {
        window.history.back();
    }
}
