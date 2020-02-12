import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DropMeSharedModule } from 'app/shared/shared.module';
import { AdminAccountComponent } from './admin-account.component';
import { AdminAccountDetailComponent } from './admin-account-detail.component';
import { AdminAccountUpdateComponent } from './admin-account-update.component';
import { AdminAccountDeleteDialogComponent } from './admin-account-delete-dialog.component';
import { adminAccountRoute } from './admin-account.route';

@NgModule({
  imports: [DropMeSharedModule, RouterModule.forChild(adminAccountRoute)],
  declarations: [AdminAccountComponent, AdminAccountDetailComponent, AdminAccountUpdateComponent, AdminAccountDeleteDialogComponent],
  entryComponents: [AdminAccountDeleteDialogComponent]
})
export class DropMeAdminAccountModule {}
