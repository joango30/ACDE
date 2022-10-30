import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document-type',
        data: { pageTitle: 'acdeApp.documentType.home.title' },
        loadChildren: () => import('./document-type/document-type.module').then(m => m.DocumentTypeModule),
      },
      {
        path: 'evaluator',
        data: { pageTitle: 'acdeApp.evaluator.home.title' },
        loadChildren: () => import('./evaluator/evaluator.module').then(m => m.EvaluatorModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'acdeApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'assessment',
        data: { pageTitle: 'acdeApp.assessment.home.title' },
        loadChildren: () => import('./assessment/assessment.module').then(m => m.AssessmentModule),
      },
      {
        path: 'training',
        data: { pageTitle: 'acdeApp.training.home.title' },
        loadChildren: () => import('./training/training.module').then(m => m.TrainingModule),
      },
      {
        path: 'observation-assessment',
        data: { pageTitle: 'acdeApp.observationAssessment.home.title' },
        loadChildren: () => import('./observation-assessment/observation-assessment.module').then(m => m.ObservationAssessmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
