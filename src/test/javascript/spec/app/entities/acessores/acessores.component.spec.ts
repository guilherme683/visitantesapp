/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VisitantesappTestModule } from '../../../test.module';
import { AcessoresComponent } from 'app/entities/acessores/acessores.component';
import { AcessoresService } from 'app/entities/acessores/acessores.service';
import { Acessores } from 'app/shared/model/acessores.model';

describe('Component Tests', () => {
    describe('Acessores Management Component', () => {
        let comp: AcessoresComponent;
        let fixture: ComponentFixture<AcessoresComponent>;
        let service: AcessoresService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [AcessoresComponent],
                providers: []
            })
                .overrideTemplate(AcessoresComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcessoresComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcessoresService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Acessores(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.acessores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
