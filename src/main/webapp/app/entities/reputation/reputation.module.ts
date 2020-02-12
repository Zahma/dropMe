import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DropMeSharedModule } from 'app/shared/shared.module';
import { ReputationComponent } from './reputation.component';
import { ReputationDetailComponent } from './reputation-detail.component';
import { ReputationUpdateComponent } from './reputation-update.component';
import { ReputationDeleteDialogComponent } from './reputation-delete-dialog.component';
import { reputationRoute } from './reputation.route';

@NgModule({
  imports: [DropMeSharedModule, RouterModule.forChild(reputationRoute)],
  declarations: [ReputationComponent, ReputationDetailComponent, ReputationUpdateComponent, ReputationDeleteDialogComponent],
  entryComponents: [ReputationDeleteDialogComponent]
})
export class DropMeReputationModule {}
