import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegioes } from 'app/shared/model/regioes.model';
import { Principal } from 'app/core';
import { RegioesService } from './regioes.service';

@Component({
    selector: 'jhi-regioes',
    templateUrl: './regioes.component.html'
})
export class RegioesComponent implements OnInit, OnDestroy {
    regioes: IRegioes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private regioesService: RegioesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.regioesService.query().subscribe(
            (res: HttpResponse<IRegioes[]>) => {
                this.regioes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRegioes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRegioes) {
        return item.id;
    }

    registerChangeInRegioes() {
        this.eventSubscriber = this.eventManager.subscribe('regioesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
