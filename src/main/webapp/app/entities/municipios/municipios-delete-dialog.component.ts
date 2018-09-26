import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMunicipios } from 'app/shared/model/municipios.model';
import { MunicipiosService } from './municipios.service';

@Component({
    selector: 'jhi-municipios-delete-dialog',
    templateUrl: './municipios-delete-dialog.component.html'
})
export class MunicipiosDeleteDialogComponent {
    municipios: IMunicipios;

    constructor(private municipiosService: MunicipiosService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.municipiosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'municipiosListModification',
                content: 'Deleted an municipios'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-municipios-delete-popup',
    template: ''
})
export class MunicipiosDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ municipios }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MunicipiosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.municipios = municipios;
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
