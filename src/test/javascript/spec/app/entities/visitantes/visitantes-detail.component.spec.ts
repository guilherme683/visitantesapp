/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { VisitantesDetailComponent } from 'app/entities/visitantes/visitantes-detail.component';
import { Visitantes } from 'app/shared/model/visitantes.model';

describe('Component Tests', () => {
    describe('Visitantes Management Detail Component', () => {
        let comp: VisitantesDetailComponent;
        let fixture: ComponentFixture<VisitantesDetailComponent>;
        const route = ({ data: of({ visitantes: new Visitantes(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [VisitantesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VisitantesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VisitantesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.visitantes).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
