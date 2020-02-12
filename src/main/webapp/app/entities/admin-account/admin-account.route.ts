import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdminAccount, AdminAccount } from 'app/shared/model/admin-account.model';
import { AdminAccountService } from './admin-account.service';
import { AdminAccountComponent } from './admin-account.component';
import { AdminAccountDetailComponent } from './admin-account-detail.component';
import { AdminAccountUpdateComponent } from './admin-account-update.component';

@Injectable({ providedIn: 'root' })
export class AdminAccountResolve implements Resolve<IAdminAccount> {
  constructor(private service: AdminAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((adminAccount: HttpResponse<AdminAccount>) => {
          if (adminAccount.body) {
            return of(adminAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminAccount());
  }
}

export const adminAccountRoute: Routes = [
  {
    path: '',
    component: AdminAccountComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'dropMeApp.adminAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdminAccountDetailComponent,
    resolve: {
      adminAccount: AdminAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.adminAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdminAccountUpdateComponent,
    resolve: {
      adminAccount: AdminAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.adminAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdminAccountUpdateComponent,
    resolve: {
      adminAccount: AdminAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.adminAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
