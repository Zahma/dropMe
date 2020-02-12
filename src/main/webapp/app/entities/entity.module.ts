import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transporter-account',
        loadChildren: () => import('./transporter-account/transporter-account.module').then(m => m.DropMeTransporterAccountModule)
      },
      {
        path: 'reputation',
        loadChildren: () => import('./reputation/reputation.module').then(m => m.DropMeReputationModule)
      },
      {
        path: 'client-account',
        loadChildren: () => import('./client-account/client-account.module').then(m => m.DropMeClientAccountModule)
      },
      {
        path: 'origin',
        loadChildren: () => import('./origin/origin.module').then(m => m.DropMeOriginModule)
      },
      {
        path: 'destination',
        loadChildren: () => import('./destination/destination.module').then(m => m.DropMeDestinationModule)
      },
      {
        path: 'trip',
        loadChildren: () => import('./trip/trip.module').then(m => m.DropMeTripModule)
      },
      {
        path: 'chat',
        loadChildren: () => import('./chat/chat.module').then(m => m.DropMeChatModule)
      },
      {
        path: 'truck',
        loadChildren: () => import('./truck/truck.module').then(m => m.DropMeTruckModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.DropMeDriverModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.DropMeRegionModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.DropMeCountryModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.DropMeLocationModule)
      },
      {
        path: 'conversation',
        loadChildren: () => import('./conversation/conversation.module').then(m => m.DropMeConversationModule)
      },
      {
        path: 'admin-account',
        loadChildren: () => import('./admin-account/admin-account.module').then(m => m.DropMeAdminAccountModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.DropMeDepartmentModule)
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.DropMeTaskModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.DropMeEmployeeModule)
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.DropMeJobModule)
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.DropMeJobHistoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DropMeEntityModule {}
