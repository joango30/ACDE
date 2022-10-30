import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluatorComponent } from '../list/evaluator.component';
import { EvaluatorDetailComponent } from '../detail/evaluator-detail.component';
import { EvaluatorUpdateComponent } from '../update/evaluator-update.component';
import { EvaluatorRoutingResolveService } from './evaluator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const evaluatorRoute: Routes = [
  {
    path: '',
    component: EvaluatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluatorDetailComponent,
    resolve: {
      evaluator: EvaluatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluatorUpdateComponent,
    resolve: {
      evaluator: EvaluatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluatorUpdateComponent,
    resolve: {
      evaluator: EvaluatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluatorRoute)],
  exports: [RouterModule],
})
export class EvaluatorRoutingModule {}
