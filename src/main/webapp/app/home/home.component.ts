import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { FormBuilder } from '@angular/forms';
import { IQueryValues } from 'app/shared/model/query-values.model';
import { HttpResponse } from '@angular/common/http';
import { EndingsService } from 'app/entities/endings/endings.service';
import { FormParams, IFormParams } from 'app/shared/model/form-params.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  isSaving = false;
  queryValues?: IQueryValues[];
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
    const formParams = this.createFromForm();
    if (formParams.text !== undefined) {
      this.subscribeToSaveResponse(this.endingsService.getStem(formParams));
    }
  }

  private createFromForm(): IFormParams {
    return {
      ...new FormParams(),
      text: this.submitForm.get(['text'])!.value,
      language: this.submitForm.get(['language'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQueryValues[]>>): void {
    result.subscribe(
      (res: HttpResponse<IQueryValues[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  protected onSuccess(data: IQueryValues[] | null): void {
    this.isSaving = false;
    this.queryValues = data || [];
  }

  protected onError(): void {
    this.isSaving = false;
  }

  trackId(index: number, item: IQueryValues): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.root!;
  }

  clearInput(): void {
    this.queryValues = [];
    this.submitForm.patchValue({
      text: [],
      language: []
    });
  }
}
