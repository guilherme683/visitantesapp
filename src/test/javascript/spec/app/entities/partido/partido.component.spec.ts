/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VisitantesappTestModule } from '../../../test.module';
import { PartidoComponent } from 'app/entities/partido/partido.component';
import { PartidoService } from 'app/entities/partido/partido.service';
import { Partido } from 'app/shared/model/partido.model';

describe('Component Tests', () => {
    describe('Partido Management Component', () => {
        let comp: PartidoComponent;
        let fixture: ComponentFixture<PartidoComponent>;
        let service: PartidoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [PartidoComponent],
                providers: []
            })
                .overrideTemplate(PartidoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartidoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartidoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Partido(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.partidos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
