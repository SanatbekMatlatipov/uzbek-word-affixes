import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { UzKazNlpToolsTestModule } from '../../../test.module';
import { EndingsUpdateComponent } from 'app/entities/endings/endings-update.component';
import { EndingsService } from 'app/entities/endings/endings.service';
import { Endings } from 'app/shared/model/endings.model';

describe('Component Tests', () => {
  describe('Endings Management Update Component', () => {
    let comp: EndingsUpdateComponent;
    let fixture: ComponentFixture<EndingsUpdateComponent>;
    let service: EndingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UzKazNlpToolsTestModule],
        declarations: [EndingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EndingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EndingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Endings(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Endings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
