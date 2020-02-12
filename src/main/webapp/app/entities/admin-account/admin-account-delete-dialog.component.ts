import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdminAccount } from 'app/shared/model/admin-account.model';
import { AdminAccountService } from './admin-account.service';

@Component({
  templateUrl: './admin-account-delete-dialog.component.html'
})
export class AdminAccountDeleteDialogComponent {
  adminAccount?: IAdminAccount;

  constructor(
    protected adminAccountService: AdminAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('adminAccountListModification');
      this.activeModal.close();
    });
  }
}
