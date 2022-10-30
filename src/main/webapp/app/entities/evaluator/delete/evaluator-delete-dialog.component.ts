import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluator } from '../evaluator.model';
import { EvaluatorService } from '../service/evaluator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './evaluator-delete-dialog.component.html',
})
export class EvaluatorDeleteDialogComponent {
  evaluator?: IEvaluator;

  constructor(protected evaluatorService: EvaluatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
