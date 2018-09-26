/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisitantesappTestModule } from '../../../test.module';
import { RegioesDeleteDialogComponent } from 'app/entities/regioes/regioes-delete-dialog.component';
import { RegioesService } from 'app/entities/regioes/regioes.service';

describe('Component Tests', () => {
    describe('Regioes Management Delete Component', () => {
        let comp: RegioesDeleteDialogComponent;
        let fixture: ComponentFixture<RegioesDeleteDialogComponent>;
        let service: RegioesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [RegioesDeleteDialogComponent]
            })
                .overrideTemplate(RegioesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegioesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegioesService);
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
