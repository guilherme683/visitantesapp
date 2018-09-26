/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisitantesappTestModule } from '../../../test.module';
import { VisitantesDeleteDialogComponent } from 'app/entities/visitantes/visitantes-delete-dialog.component';
import { VisitantesService } from 'app/entities/visitantes/visitantes.service';

describe('Component Tests', () => {
    describe('Visitantes Management Delete Component', () => {
        let comp: VisitantesDeleteDialogComponent;
        let fixture: ComponentFixture<VisitantesDeleteDialogComponent>;
        let service: VisitantesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [VisitantesDeleteDialogComponent]
            })
                .overrideTemplate(VisitantesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VisitantesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitantesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
