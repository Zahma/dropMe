import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClientAccount, ClientAccount } from 'app/shared/model/client-account.model';
import { ClientAccountService } from './client-account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-client-account-update',
  templateUrl: './client-account-update.component.html'
})
export class ClientAccountUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    phone: [],
    referredBy: [],
    referal: [],
    activated: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected clientAccountService: ClientAccountService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientAccount }) => {
      this.updateForm(clientAccount);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(clientAccount: IClientAccount): void {
    this.editForm.patchValue({
      id: clientAccount.id,
      firstName: clientAccount.firstName,
      lastName: clientAccount.lastName,
      phone: clientAccount.phone,
      referredBy: clientAccount.referredBy,
      referal: clientAccount.referal,
      activated: clientAccount.activated,
      userId: clientAccount.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clientAccount = this.createFromForm();
    if (clientAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.clientAccountService.update(clientAccount));
    } else {
      this.subscribeToSaveResponse(this.clientAccountService.create(clientAccount));
    }
  }

  private createFromForm(): IClientAccount {
    return {
      ...new ClientAccount(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      referredBy: this.editForm.get(['referredBy'])!.value,
      referal: this.editForm.get(['referal'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientAccount>>): void {
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
