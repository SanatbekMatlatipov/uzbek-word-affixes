import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndings } from 'app/shared/model/endings.model';
import { EndingsService } from './endings.service';

@Component({
  templateUrl: './endings-delete-dialog.component.html'
})
export class EndingsDeleteDialogComponent {
  endings?: IEndings;

  constructor(protected endingsService: EndingsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.endingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('endingsListModification');
      this.activeModal.close();
    });
  }
}
