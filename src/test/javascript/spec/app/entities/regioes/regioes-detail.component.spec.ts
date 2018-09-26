/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { RegioesDetailComponent } from 'app/entities/regioes/regioes-detail.component';
import { Regioes } from 'app/shared/model/regioes.model';

describe('Component Tests', () => {
    describe('Regioes Management Detail Component', () => {
        let comp: RegioesDetailComponent;
        let fixture: ComponentFixture<RegioesDetailComponent>;
        const route = ({ data: of({ regioes: new Regioes(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [RegioesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegioesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegioesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regioes).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
