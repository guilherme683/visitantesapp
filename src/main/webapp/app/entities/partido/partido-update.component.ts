import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPartido } from 'app/shared/model/partido.model';
import { PartidoService } from './partido.service';

@Component({
    selector: 'jhi-partido-update',
    templateUrl: './partido-update.component.html'
})
export class PartidoUpdateComponent implements OnInit {
    private _partido: IPartido;
    isSaving: boolean;

    constructor(private partidoService: PartidoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partido }) => {
            this.partido = partido;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partido.id !== undefined) {
            this.subscribeToSaveResponse(this.partidoService.update(this.partido));
        } else {
            this.subscribeToSaveResponse(this.partidoService.create(this.partido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartido>>) {
        result.subscribe((res: HttpResponse<IPartido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get partido() {
        return this._partido;
    }

    set partido(partido: IPartido) {
        this._partido = partido;
    }
}
