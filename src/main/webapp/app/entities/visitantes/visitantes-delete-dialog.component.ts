import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVisitantes } from 'app/shared/model/visitantes.model';
import { VisitantesService } from './visitantes.service';

@Component({
    selector: 'jhi-visitantes-delete-dialog',
    templateUrl: './visitantes-delete-dialog.component.html'
})
export class VisitantesDeleteDialogComponent {
    visitantes: IVisitantes;

    constructor(private visitantesService: VisitantesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visitantesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'visitantesListModification',
                content: 'Deleted an visitantes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visitantes-delete-popup',
    template: ''
})
export class VisitantesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ visitantes }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VisitantesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.visitantes = visitantes;
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
