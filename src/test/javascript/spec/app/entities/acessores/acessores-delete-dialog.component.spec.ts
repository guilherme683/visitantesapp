/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VisitantesappTestModule } from '../../../test.module';
import { AcessoresDeleteDialogComponent } from 'app/entities/acessores/acessores-delete-dialog.component';
import { AcessoresService } from 'app/entities/acessores/acessores.service';

describe('Component Tests', () => {
    describe('Acessores Management Delete Component', () => {
        let comp: AcessoresDeleteDialogComponent;
        let fixture: ComponentFixture<AcessoresDeleteDialogComponent>;
        let service: AcessoresService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [AcessoresDeleteDialogComponent]
            })
                .overrideTemplate(AcessoresDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcessoresDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcessoresService);
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
