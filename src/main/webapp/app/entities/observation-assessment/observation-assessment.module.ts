import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ObservationAssessmentComponent } from './list/observation-assessment.component';
import { ObservationAssessmentDetailComponent } from './detail/observation-assessment-detail.component';
import { ObservationAssessmentUpdateComponent } from './update/observation-assessment-update.component';
import { ObservationAssessmentDeleteDialogComponent } from './delete/observation-assessment-delete-dialog.component';
import { ObservationAssessmentRoutingModule } from './route/observation-assessment-routing.module';

@NgModule({
  imports: [SharedModule, ObservationAssessmentRoutingModule],
  declarations: [
    ObservationAssessmentComponent,
    ObservationAssessmentDetailComponent,
    ObservationAssessmentUpdateComponent,
    ObservationAssessmentDeleteDialogComponent,
  ],
})
export class ObservationAssessmentModule {}
