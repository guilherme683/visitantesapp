import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEstados } from 'app/shared/model/estados.model';
import { EstadosService } from './estados.service';
import { IRegioes } from 'app/shared/model/regioes.model';
import { RegioesService } from 'app/entities/regioes';

@Component({
    selector: 'jhi-estados-update',
    templateUrl: './estados-update.component.html'
})
export class EstadosUpdateComponent implements OnInit {
    private _estados: IEstados;
    isSaving: boolean;

    regioes: IRegioes[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private estadosService: EstadosService,
        private regioesService: RegioesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estados }) => {
            this.estados = estados;
        });
        this.regioesService.query().subscribe(
            (res: HttpResponse<IRegioes[]>) => {
                this.regioes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estados.id !== undefined) {
            this.subscribeToSaveResponse(this.estadosService.update(this.estados));
        } else {
            this.subscribeToSaveResponse(this.estadosService.create(this.estados));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEstados>>) {
        result.subscribe((res: HttpResponse<IEstados>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRegioesById(index: number, item: IRegioes) {
        return item.id;
    }
    get estados() {
        return this._estados;
    }

    set estados(estados: IEstados) {
        this._estados = estados;
    }
}
