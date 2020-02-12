import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DropMeSharedModule } from 'app/shared/shared.module';
import { OriginComponent } from './origin.component';
import { OriginDetailComponent } from './origin-detail.component';
import { OriginUpdateComponent } from './origin-update.component';
import { OriginDeleteDialogComponent } from './origin-delete-dialog.component';
import { originRoute } from './origin.route';

@NgModule({
  imports: [DropMeSharedModule, RouterModule.forChild(originRoute)],
  declarations: [OriginComponent, OriginDetailComponent, OriginUpdateComponent, OriginDeleteDialogComponent],
  entryComponents: [OriginDeleteDialogComponent]
})
export class DropMeOriginModule {}
