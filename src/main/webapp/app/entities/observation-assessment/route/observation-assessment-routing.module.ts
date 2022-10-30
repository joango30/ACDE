import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ObservationAssessmentComponent } from '../list/observation-assessment.component';
import { ObservationAssessmentDetailComponent } from '../detail/observation-assessment-detail.component';
import { ObservationAssessmentUpdateComponent } from '../update/observation-assessment-update.component';
import { ObservationAssessmentRoutingResolveService } from './observation-assessment-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const observationAssessmentRoute: Routes = [
  {
    path: '',
    component: ObservationAssessmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObservationAssessmentDetailComponent,
    resolve: {
      observationAssessment: ObservationAssessmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObservationAssessmentUpdateComponent,
    resolve: {
      observationAssessment: ObservationAssessmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObservationAssessmentUpdateComponent,
    resolve: {
      observationAssessment: ObservationAssessmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(observationAssessmentRoute)],
  exports: [RouterModule],
})
export class ObservationAssessmentRoutingModule {}
