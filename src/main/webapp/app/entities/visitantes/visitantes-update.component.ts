import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IVisitantes } from 'app/shared/model/visitantes.model';
import { VisitantesService } from './visitantes.service';
import { IAcessores } from 'app/shared/model/acessores.model';
import { AcessoresService } from 'app/entities/acessores';
import { IMunicipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from 'app/entities/municipios';
import { ICargo } from 'app/shared/model/cargo.model';
import { CargoService } from 'app/entities/cargo';
import { IPartido } from 'app/shared/model/partido.model';
import { PartidoService } from 'app/entities/partido';

@Component({
    selector: 'jhi-visitantes-update',
    templateUrl: './visitantes-update.component.html'
})
export class VisitantesUpdateComponent implements OnInit {
    private _visitantes: IVisitantes;
    isSaving: boolean;

    acessores: IAcessores[];

    municipios: IMunicipios[];

    cargos: ICargo[];

    partidos: IPartido[];
    dataVisita: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private visitantesService: VisitantesService,
        private acessoresService: AcessoresService,
        private municipiosService: MunicipiosService,
        private cargoService: CargoService,
        private partidoService: PartidoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ visitantes }) => {
            this.visitantes = visitantes;
        });
        this.acessoresService.query().subscribe(
            (res: HttpResponse<IAcessores[]>) => {
                this.acessores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.municipiosService.query().subscribe(
            (res: HttpResponse<IMunicipios[]>) => {
                this.municipios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cargoService.query().subscribe(
            (res: HttpResponse<ICargo[]>) => {
                this.cargos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.partidoService.query().subscribe(
            (res: HttpResponse<IPartido[]>) => {
                this.partidos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.visitantes.dataVisita = moment(this.dataVisita, DATE_TIME_FORMAT);
        if (this.visitantes.id !== undefined) {
            this.subscribeToSaveResponse(this.visitantesService.update(this.visitantes));
        } else {
            this.subscribeToSaveResponse(this.visitantesService.create(this.visitantes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVisitantes>>) {
        result.subscribe((res: HttpResponse<IVisitantes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAcessoresById(index: number, item: IAcessores) {
        return item.id;
    }

    trackMunicipiosById(index: number, item: IMunicipios) {
        return item.id;
    }

    trackCargoById(index: number, item: ICargo) {
        return item.id;
    }

    trackPartidoById(index: number, item: IPartido) {
        return item.id;
    }
    get visitantes() {
        return this._visitantes;
    }

    set visitantes(visitantes: IVisitantes) {
        this._visitantes = visitantes;
        this.dataVisita = moment(visitantes.dataVisita).format(DATE_TIME_FORMAT);
    }
}
