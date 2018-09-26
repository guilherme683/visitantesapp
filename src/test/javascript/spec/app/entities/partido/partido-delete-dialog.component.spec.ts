/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisitantesappTestModule } from '../../../test.module';
import { PartidoDeleteDialogComponent } from 'app/entities/partido/partido-delete-dialog.component';
import { PartidoService } from 'app/entities/partido/partido.service';

describe('Component Tests', () => {
    describe('Partido Management Delete Component', () => {
        let comp: PartidoDeleteDialogComponent;
        let fixture: ComponentFixture<PartidoDeleteDialogComponent>;
        let service: PartidoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [PartidoDeleteDialogComponent]
            })
                .overrideTemplate(PartidoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartidoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartidoService);
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
