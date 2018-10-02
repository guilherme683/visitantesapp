import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAcessores } from 'app/shared/model/acessores.model';
import { Principal } from 'app/core';
import { AcessoresService } from './acessores.service';

@Component({
    selector: 'jhi-acessores',
    templateUrl: './acessores.component.html'
})
export class AcessoresComponent implements OnInit, OnDestroy {
    acessores: IAcessores[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private acessoresService: AcessoresService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.acessoresService.query().subscribe(
            (res: HttpResponse<IAcessores[]>) => {
                this.acessores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAcessores();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAcessores) {
        return item.id;
    }

    registerChangeInAcessores() {
        this.eventSubscriber = this.eventManager.subscribe('acessoresListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
