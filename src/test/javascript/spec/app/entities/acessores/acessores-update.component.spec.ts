/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { AcessoresUpdateComponent } from 'app/entities/acessores/acessores-update.component';
import { AcessoresService } from 'app/entities/acessores/acessores.service';
import { Acessores } from 'app/shared/model/acessores.model';

describe('Component Tests', () => {
    describe('Acessores Management Update Component', () => {
        let comp: AcessoresUpdateComponent;
        let fixture: ComponentFixture<AcessoresUpdateComponent>;
        let service: AcessoresService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [AcessoresUpdateComponent]
            })
                .overrideTemplate(AcessoresUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcessoresUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcessoresService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Acessores(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.acessores = entity;
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
                    const entity = new Acessores();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.acessores = entity;
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
