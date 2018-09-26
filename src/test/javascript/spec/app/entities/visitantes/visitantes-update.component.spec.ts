/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { VisitantesUpdateComponent } from 'app/entities/visitantes/visitantes-update.component';
import { VisitantesService } from 'app/entities/visitantes/visitantes.service';
import { Visitantes } from 'app/shared/model/visitantes.model';

describe('Component Tests', () => {
    describe('Visitantes Management Update Component', () => {
        let comp: VisitantesUpdateComponent;
        let fixture: ComponentFixture<VisitantesUpdateComponent>;
        let service: VisitantesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [VisitantesUpdateComponent]
            })
                .overrideTemplate(VisitantesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VisitantesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitantesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Visitantes(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.visitantes = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Visitantes();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.visitantes = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
