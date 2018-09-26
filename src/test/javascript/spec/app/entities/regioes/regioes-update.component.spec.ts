/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { RegioesUpdateComponent } from 'app/entities/regioes/regioes-update.component';
import { RegioesService } from 'app/entities/regioes/regioes.service';
import { Regioes } from 'app/shared/model/regioes.model';

describe('Component Tests', () => {
    describe('Regioes Management Update Component', () => {
        let comp: RegioesUpdateComponent;
        let fixture: ComponentFixture<RegioesUpdateComponent>;
        let service: RegioesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [RegioesUpdateComponent]
            })
                .overrideTemplate(RegioesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegioesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegioesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Regioes(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regioes = entity;
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
                    const entity = new Regioes();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regioes = entity;
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
