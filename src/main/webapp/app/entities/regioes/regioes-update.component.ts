import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRegioes } from 'app/shared/model/regioes.model';
import { RegioesService } from './regioes.service';

@Component({
    selector: 'jhi-regioes-update',
    templateUrl: './regioes-update.component.html'
})
export class RegioesUpdateComponent implements OnInit {
    private _regioes: IRegioes;
    isSaving: boolean;

    constructor(private regioesService: RegioesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regioes }) => {
            this.regioes = regioes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.regioes.id !== undefined) {
            this.subscribeToSaveResponse(this.regioesService.update(this.regioes));
        } else {
            this.subscribeToSaveResponse(this.regioesService.create(this.regioes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegioes>>) {
        result.subscribe((res: HttpResponse<IRegioes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get regioes() {
        return this._regioes;
    }

    set regioes(regioes: IRegioes) {
        this._regioes = regioes;
    }
}
