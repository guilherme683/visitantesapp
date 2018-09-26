import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartido } from 'app/shared/model/partido.model';

@Component({
    selector: 'jhi-partido-detail',
    templateUrl: './partido-detail.component.html'
})
export class PartidoDetailComponent implements OnInit {
    partido: IPartido;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partido }) => {
            this.partido = partido;
        });
    }

    previousState() {
        window.history.back();
    }
}
