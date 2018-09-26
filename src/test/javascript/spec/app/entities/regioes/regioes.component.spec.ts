/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VisitantesappTestModule } from '../../../test.module';
import { RegioesComponent } from 'app/entities/regioes/regioes.component';
import { RegioesService } from 'app/entities/regioes/regioes.service';
import { Regioes } from 'app/shared/model/regioes.model';

describe('Component Tests', () => {
    describe('Regioes Management Component', () => {
        let comp: RegioesComponent;
        let fixture: ComponentFixture<RegioesComponent>;
        let service: RegioesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [RegioesComponent],
                providers: []
            })
                .overrideTemplate(RegioesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegioesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegioesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Regioes(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.regioes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
