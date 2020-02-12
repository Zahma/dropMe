import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdminAccount, AdminAccount } from 'app/shared/model/admin-account.model';
import { AdminAccountService } from './admin-account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-admin-account-update',
  templateUrl: './admin-account-update.component.html'
})
export class AdminAccountUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected adminAccountService: AdminAccountService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminAccount }) => {
      this.updateForm(adminAccount);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(adminAccount: IAdminAccount): void {
    this.editForm.patchValue({
      id: adminAccount.id,
      role: adminAccount.role,
      userId: adminAccount.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminAccount = this.createFromForm();
    if (adminAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.adminAccountService.update(adminAccount));
    } else {
      this.subscribeToSaveResponse(this.adminAccountService.create(adminAccount));
    }
  }

  private createFromForm(): IAdminAccount {
    return {
      ...new AdminAccount(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminAccount>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
