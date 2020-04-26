import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEndings, Endings } from 'app/shared/model/endings.model';
import { EndingsService } from './endings.service';

@Component({
  selector: 'jhi-endings-update',
  templateUrl: './endings-update.component.html'
})
export class EndingsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    numberOfTypes: [],
    language: []
  });

  constructor(protected endingsService: EndingsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endings }) => {
      this.updateForm(endings);
    });
  }

  updateForm(endings: IEndings): void {
    this.editForm.patchValue({
      id: endings.id,
      name: endings.name,
      numberOfTypes: endings.numberOfTypes,
      language: endings.language
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endings = this.createFromForm();
    if (endings.id !== undefined) {
      this.subscribeToSaveResponse(this.endingsService.update(endings));
    } else {
      this.subscribeToSaveResponse(this.endingsService.create(endings));
    }
  }

  private createFromForm(): IEndings {
    return {
      ...new Endings(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      numberOfTypes: this.editForm.get(['numberOfTypes'])!.value,
      language: this.editForm.get(['language'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndings>>): void {
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
}
