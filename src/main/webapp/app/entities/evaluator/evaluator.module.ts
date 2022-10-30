import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EvaluatorComponent } from './list/evaluator.component';
import { EvaluatorDetailComponent } from './detail/evaluator-detail.component';
import { EvaluatorUpdateComponent } from './update/evaluator-update.component';
import { EvaluatorDeleteDialogComponent } from './delete/evaluator-delete-dialog.component';
import { EvaluatorRoutingModule } from './route/evaluator-routing.module';

@NgModule({
  imports: [SharedModule, EvaluatorRoutingModule],
  declarations: [EvaluatorComponent, EvaluatorDetailComponent, EvaluatorUpdateComponent, EvaluatorDeleteDialogComponent],
})
export class EvaluatorModule {}
