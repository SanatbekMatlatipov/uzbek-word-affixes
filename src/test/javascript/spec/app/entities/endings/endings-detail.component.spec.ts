import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UzKazNlpToolsTestModule } from '../../../test.module';
import { EndingsDetailComponent } from 'app/entities/endings/endings-detail.component';
import { Endings } from 'app/shared/model/endings.model';

describe('Component Tests', () => {
  describe('Endings Management Detail Component', () => {
    let comp: EndingsDetailComponent;
    let fixture: ComponentFixture<EndingsDetailComponent>;
    const route = ({ data: of({ endings: new Endings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UzKazNlpToolsTestModule],
        declarations: [EndingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EndingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load endings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.endings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
