import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ObservationAssessmentFormService, ObservationAssessmentFormGroup } from './observation-assessment-form.service';
import { IObservationAssessment } from '../observation-assessment.model';
import { ObservationAssessmentService } from '../service/observation-assessment.service';
import { IAssessment } from 'app/entities/assessment/assessment.model';
import { AssessmentService } from 'app/entities/assessment/service/assessment.service';

@Component({
  selector: 'acde-observation-assessment-update',
  templateUrl: './observation-assessment-update.component.html',
})
export class ObservationAssessmentUpdateComponent implements OnInit {
  isSaving = false;
  observationAssessment: IObservationAssessment | null = null;

  assessmentsSharedCollection: IAssessment[] = [];

  editForm: ObservationAssessmentFormGroup = this.observationAssessmentFormService.createObservationAssessmentFormGroup();

  constructor(
    protected observationAssessmentService: ObservationAssessmentService,
    protected observationAssessmentFormService: ObservationAssessmentFormService,
    protected assessmentService: AssessmentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAssessment = (o1: IAssessment | null, o2: IAssessment | null): boolean => this.assessmentService.compareAssessment(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observationAssessment }) => {
      this.observationAssessment = observationAssessment;
      if (observationAssessment) {
        this.updateForm(observationAssessment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observationAssessment = this.observationAssessmentFormService.getObservationAssessment(this.editForm);
    if (observationAssessment.id !== null) {
      this.subscribeToSaveResponse(this.observationAssessmentService.update(observationAssessment));
    } else {
      this.subscribeToSaveResponse(this.observationAssessmentService.create(observationAssessment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservationAssessment>>): void {
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

  protected updateForm(observationAssessment: IObservationAssessment): void {
    this.observationAssessment = observationAssessment;
    this.observationAssessmentFormService.resetForm(this.editForm, observationAssessment);

    this.assessmentsSharedCollection = this.assessmentService.addAssessmentToCollectionIfMissing<IAssessment>(
      this.assessmentsSharedCollection,
      observationAssessment.assessment
    );
  }

  protected loadRelationshipsOptions(): void {
    this.assessmentService
      .query()
      .pipe(map((res: HttpResponse<IAssessment[]>) => res.body ?? []))
      .pipe(
        map((assessments: IAssessment[]) =>
          this.assessmentService.addAssessmentToCollectionIfMissing<IAssessment>(assessments, this.observationAssessment?.assessment)
        )
      )
      .subscribe((assessments: IAssessment[]) => (this.assessmentsSharedCollection = assessments));
  }
}
