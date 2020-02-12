import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminAccount } from 'app/shared/model/admin-account.model';

@Component({
  selector: 'jhi-admin-account-detail',
  templateUrl: './admin-account-detail.component.html'
})
export class AdminAccountDetailComponent implements OnInit {
  adminAccount: IAdminAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminAccount }) => (this.adminAccount = adminAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
