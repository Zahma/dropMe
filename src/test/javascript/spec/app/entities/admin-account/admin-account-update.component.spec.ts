import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DropMeTestModule } from '../../../test.module';
import { AdminAccountUpdateComponent } from 'app/entities/admin-account/admin-account-update.component';
import { AdminAccountService } from 'app/entities/admin-account/admin-account.service';
import { AdminAccount } from 'app/shared/model/admin-account.model';

describe('Component Tests', () => {
  describe('AdminAccount Management Update Component', () => {
    let comp: AdminAccountUpdateComponent;
    let fixture: ComponentFixture<AdminAccountUpdateComponent>;
    let service: AdminAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DropMeTestModule],
        declarations: [AdminAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AdminAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdminAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdminAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdminAccount(123);
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
        const entity = new AdminAccount();
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
