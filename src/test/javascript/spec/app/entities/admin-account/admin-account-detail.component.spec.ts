import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DropMeTestModule } from '../../../test.module';
import { AdminAccountDetailComponent } from 'app/entities/admin-account/admin-account-detail.component';
import { AdminAccount } from 'app/shared/model/admin-account.model';

describe('Component Tests', () => {
  describe('AdminAccount Management Detail Component', () => {
    let comp: AdminAccountDetailComponent;
    let fixture: ComponentFixture<AdminAccountDetailComponent>;
    const route = ({ data: of({ adminAccount: new AdminAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DropMeTestModule],
        declarations: [AdminAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AdminAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdminAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load adminAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adminAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
