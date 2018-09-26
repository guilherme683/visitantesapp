/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { CargoUpdateComponent } from 'app/entities/cargo/cargo-update.component';
import { CargoService } from 'app/entities/cargo/cargo.service';
import { Cargo } from 'app/shared/model/cargo.model';

describe('Component Tests', () => {
    describe('Cargo Management Update Component', () => {
        let comp: CargoUpdateComponent;
        let fixture: ComponentFixture<CargoUpdateComponent>;
        let service: CargoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [CargoUpdateComponent]
            })
                .overrideTemplate(CargoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CargoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CargoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cargo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cargo = entity;
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
                    const entity = new Cargo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cargo = entity;
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