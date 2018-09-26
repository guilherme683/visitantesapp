import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVisitantes } from 'app/shared/model/visitantes.model';

@Component({
    selector: 'jhi-visitantes-detail',
    templateUrl: './visitantes-detail.component.html'
})
export class VisitantesDetailComponent implements OnInit {
    visitantes: IVisitantes;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ visitantes }) => {
            this.visitantes = visitantes;
        });
    }

    previousState() {
        window.history.back();
    }
}
