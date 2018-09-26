import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAcessores } from 'app/shared/model/acessores.model';
import { AcessoresService } from './acessores.service';

@Component({
    selector: 'jhi-acessores-update',
    templateUrl: './acessores-update.component.html'
})
export class AcessoresUpdateComponent implements OnInit {
    private _acessores: IAcessores;
    isSaving: boolean;

    constructor(private acessoresService: AcessoresService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ acessores }) => {
            this.acessores = acessores;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.acessores.id !== undefined) {
            this.subscribeToSaveResponse(this.acessoresService.update(this.acessores));
        } else {
            this.subscribeToSaveResponse(this.acessoresService.create(this.acessores));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAcessores>>) {
        result.subscribe((res: HttpResponse<IAcessores>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get acessores() {
        return this._acessores;
    }

    set acessores(acessores: IAcessores) {
        this._acessores = acessores;
    }
}
