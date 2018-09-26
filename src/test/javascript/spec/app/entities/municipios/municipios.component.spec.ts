/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VisitantesappTestModule } from '../../../test.module';
import { MunicipiosComponent } from 'app/entities/municipios/municipios.component';
import { MunicipiosService } from 'app/entities/municipios/municipios.service';
import { Municipios } from 'app/shared/model/municipios.model';

describe('Component Tests', () => {
    describe('Municipios Management Component', () => {
        let comp: MunicipiosComponent;
        let fixture: ComponentFixture<MunicipiosComponent>;
        let service: MunicipiosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [MunicipiosComponent],
                providers: []
            })
                .overrideTemplate(MunicipiosComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MunicipiosComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MunicipiosService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Municipios(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.municipios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
