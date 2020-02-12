import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConversation, Conversation } from 'app/shared/model/conversation.model';
import { ConversationService } from './conversation.service';
import { ITrip } from 'app/shared/model/trip.model';
import { TripService } from 'app/entities/trip/trip.service';
import { ITruck } from 'app/shared/model/truck.model';
import { TruckService } from 'app/entities/truck/truck.service';

type SelectableEntity = ITrip | ITruck;

@Component({
  selector: 'jhi-conversation-update',
  templateUrl: './conversation-update.component.html'
})
export class ConversationUpdateComponent implements OnInit {
  isSaving = false;
  trips: ITrip[] = [];
  trucks: ITruck[] = [];

  editForm = this.fb.group({
    id: [],
    tripId: [],
    truckId: []
  });

  constructor(
    protected conversationService: ConversationService,
    protected tripService: TripService,
    protected truckService: TruckService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conversation }) => {
      this.updateForm(conversation);

      this.tripService.query().subscribe((res: HttpResponse<ITrip[]>) => (this.trips = res.body || []));

      this.truckService.query().subscribe((res: HttpResponse<ITruck[]>) => (this.trucks = res.body || []));
    });
  }

  updateForm(conversation: IConversation): void {
    this.editForm.patchValue({
      id: conversation.id,
      tripId: conversation.tripId,
      truckId: conversation.truckId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conversation = this.createFromForm();
    if (conversation.id !== undefined) {
      this.subscribeToSaveResponse(this.conversationService.update(conversation));
    } else {
      this.subscribeToSaveResponse(this.conversationService.create(conversation));
    }
  }

  private createFromForm(): IConversation {
    return {
      ...new Conversation(),
      id: this.editForm.get(['id'])!.value,
      tripId: this.editForm.get(['tripId'])!.value,
      truckId: this.editForm.get(['truckId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversation>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
