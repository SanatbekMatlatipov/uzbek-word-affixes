import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndings } from 'app/shared/model/endings.model';

@Component({
  selector: 'jhi-endings-detail',
  templateUrl: './endings-detail.component.html'
})
export class EndingsDetailComponent implements OnInit {
  endings: IEndings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endings }) => (this.endings = endings));
  }

  previousState(): void {
    window.history.back();
  }
}
