import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITruck, Truck } from 'app/shared/model/truck.model';
import { TruckService } from './truck.service';
import { TruckComponent } from './truck.component';
import { TruckDetailComponent } from './truck-detail.component';
import { TruckUpdateComponent } from './truck-update.component';

@Injectable({ providedIn: 'root' })
export class TruckResolve implements Resolve<ITruck> {
  constructor(private service: TruckService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITruck> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((truck: HttpResponse<Truck>) => {
          if (truck.body) {
            return of(truck.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Truck());
  }
}

export const truckRoute: Routes = [
  {
    path: '',
    component: TruckComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'dropMeApp.truck.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TruckDetailComponent,
    resolve: {
      truck: TruckResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.truck.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TruckUpdateComponent,
    resolve: {
      truck: TruckResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.truck.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TruckUpdateComponent,
    resolve: {
      truck: TruckResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dropMeApp.truck.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
