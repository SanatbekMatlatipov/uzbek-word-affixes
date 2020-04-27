import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { FormBuilder } from '@angular/forms';
import { IQueryValues, QueryValues } from 'app/shared/model/queryValues.model';
import { HttpResponse } from '@angular/common/http';
import { EndingsService } from 'app/entities/endings/endings.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  isSaving = false;
  account: Account | null = null;
  authSubscription?: Subscription;
  submitForm = this.fb.group({
    text: [],
    language: []
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private fb: FormBuilder,
    protected endingsService: EndingsService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  getStem(): void {
    this.isSaving = true;
    const queryValues = this.createFromForm();
    console.warn(queryValues);
    if (queryValues.text !== undefined) {
      this.subscribeToSaveResponse(this.endingsService.getStem(queryValues));
    }
  }

  private createFromForm(): IQueryValues {
    return {
      ...new QueryValues(),
      text: this.submitForm.get(['text'])!.value,
      language: this.submitForm.get(['language'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQueryValues>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
