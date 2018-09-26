import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMunicipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';
import { IEstados } from 'app/shared/model/estados.model';
import { EstadosService } from 'app/entities/estados';

@Component({
    selector: 'jhi-municipios-update',
    templateUrl: './municipios-update.component.html'
})
export class MunicipiosUpdateComponent implements OnInit {
    private _municipios: IMunicipios;
    isSaving: boolean;

    estados: IEstados[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private municipiosService: MunicipiosService,
        private estadosService: EstadosService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ municipios }) => {
            this.municipios = municipios;
        });
        this.estadosService.query().subscribe(
            (res: HttpResponse<IEstados[]>) => {
                this.estados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.municipios.id !== undefined) {
            this.subscribeToSaveResponse(this.municipiosService.update(this.municipios));
        } else {
            this.subscribeToSaveResponse(this.municipiosService.create(this.municipios));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipios>>) {
        result.subscribe((res: HttpResponse<IMunicipios>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstadosById(index: number, item: IEstados) {
        return item.id;
    }
    get municipios() {
        return this._municipios;
    }

    set municipios(municipios: IMunicipios) {
        this._municipios = municipios;
    }
}
