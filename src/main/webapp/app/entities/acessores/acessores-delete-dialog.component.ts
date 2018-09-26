import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcessores } from 'app/shared/model/acessores.model';
import { AcessoresService } from './acessores.service';

@Component({
    selector: 'jhi-acessores-delete-dialog',
    templateUrl: './acessores-delete-dialog.component.html'
})
export class AcessoresDeleteDialogComponent {
    acessores: IAcessores;

    constructor(private acessoresService: AcessoresService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.acessoresService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'acessoresListModification',
                content: 'Deleted an acessores'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-acessores-delete-popup',
    template: ''
})
export class AcessoresDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ acessores }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AcessoresDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.acessores = acessores;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
