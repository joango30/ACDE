import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TrainingFormService, TrainingFormGroup } from './training-form.service';
import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';
import { IEvaluator } from 'app/entities/evaluator/evaluator.model';
import { EvaluatorService } from 'app/entities/evaluator/service/evaluator.service';

@Component({
  selector: 'acde-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;
  training: ITraining | null = null;

  evaluatorsSharedCollection: IEvaluator[] = [];

  editForm: TrainingFormGroup = this.trainingFormService.createTrainingFormGroup();

  constructor(
    protected trainingService: TrainingService,
    protected trainingFormService: TrainingFormService,
    protected evaluatorService: EvaluatorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvaluator = (o1: IEvaluator | null, o2: IEvaluator | null): boolean => this.evaluatorService.compareEvaluator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.training = training;
      if (training) {
        this.updateForm(training);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.trainingFormService.getTraining(this.editForm);
    if (training.id !== null) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(training: ITraining): void {
    this.training = training;
    this.trainingFormService.resetForm(this.editForm, training);

    this.evaluatorsSharedCollection = this.evaluatorService.addEvaluatorToCollectionIfMissing<IEvaluator>(
      this.evaluatorsSharedCollection,
      training.evaluator
    );
  }

  protected loadRelationshipsOptions(): void {
    this.evaluatorService
      .query()
      .pipe(map((res: HttpResponse<IEvaluator[]>) => res.body ?? []))
      .pipe(
        map((evaluators: IEvaluator[]) =>
          this.evaluatorService.addEvaluatorToCollectionIfMissing<IEvaluator>(evaluators, this.training?.evaluator)
        )
      )
      .subscribe((evaluators: IEvaluator[]) => (this.evaluatorsSharedCollection = evaluators));
  }
}
