import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminAccountService } from 'app/entities/admin-account/admin-account.service';
import { IAdminAccount, AdminAccount } from 'app/shared/model/admin-account.model';
import { ERole } from 'app/shared/model/enumerations/e-role.model';

describe('Service Tests', () => {
  describe('AdminAccount Service', () => {
    let injector: TestBed;
    let service: AdminAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdminAccount;
    let expectedResult: IAdminAccount | IAdminAccount[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdminAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AdminAccount(0, ERole.SUPERADMIN);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AdminAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AdminAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AdminAccount', () => {
        const returnedFromService = Object.assign(
          {
            role: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AdminAccount', () => {
        const returnedFromService = Object.assign(
          {
            role: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AdminAccount', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
