import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IObservationAssessment } from '../observation-assessment.model';
import { ObservationAssessmentService } from '../service/observation-assessment.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './observation-assessment-delete-dialog.component.html',
})
export class ObservationAssessmentDeleteDialogComponent {
  observationAssessment?: IObservationAssessment;

  constructor(protected observationAssessmentService: ObservationAssessmentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.observationAssessmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
