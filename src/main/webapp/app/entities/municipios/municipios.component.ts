import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMunicipios } from 'app/shared/model/municipios.model';
import { Principal } from 'app/core';
import { MunicipiosService } from './municipios.service';

@Component({
    selector: 'jhi-municipios',
    templateUrl: './municipios.component.html'
})
export class MunicipiosComponent implements OnInit, OnDestroy {
    municipios: IMunicipios[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private municipiosService: MunicipiosService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.municipiosService.query().subscribe(
            (res: HttpResponse<IMunicipios[]>) => {
                this.municipios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMunicipios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMunicipios) {
        return item.id;
    }

    registerChangeInMunicipios() {
        this.eventSubscriber = this.eventManager.subscribe('municipiosListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
