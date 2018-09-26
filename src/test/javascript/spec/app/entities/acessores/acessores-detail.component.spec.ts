/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisitantesappTestModule } from '../../../test.module';
import { AcessoresDetailComponent } from 'app/entities/acessores/acessores-detail.component';
import { Acessores } from 'app/shared/model/acessores.model';

describe('Component Tests', () => {
    describe('Acessores Management Detail Component', () => {
        let comp: AcessoresDetailComponent;
        let fixture: ComponentFixture<AcessoresDetailComponent>;
        const route = ({ data: of({ acessores: new Acessores(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisitantesappTestModule],
                declarations: [AcessoresDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AcessoresDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcessoresDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.acessores).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
