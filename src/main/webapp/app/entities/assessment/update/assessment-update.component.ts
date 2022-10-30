import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AssessmentFormService, AssessmentFormGroup } from './assessment-form.service';
import { IAssessment } from '../assessment.model';
import { AssessmentService } from '../service/assessment.service';
import { IEvaluator } from 'app/entities/evaluator/evaluator.model';
import { EvaluatorService } from 'app/entities/evaluator/service/evaluator.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

@Component({
  selector: 'acde-assessment-update',
  templateUrl: './assessment-update.component.html',
})
export class AssessmentUpdateComponent implements OnInit {
  isSaving = false;
  assessment: IAssessment | null = null;

  evaluatorsSharedCollection: IEvaluator[] = [];
  employeesSharedCollection: IEmployee[] = [];

  editForm: AssessmentFormGroup = this.assessmentFormService.createAssessmentFormGroup();

  constructor(
    protected assessmentService: AssessmentService,
    protected assessmentFormService: AssessmentFormService,
    protected evaluatorService: EvaluatorService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEvaluator = (o1: IEvaluator | null, o2: IEvaluator | null): boolean => this.evaluatorService.compareEvaluator(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assessment }) => {
      this.assessment = assessment;
      if (assessment) {
        this.updateForm(assessment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assessment = this.assessmentFormService.getAssessment(this.editForm);
    if (assessment.id !== null) {
      this.subscribeToSaveResponse(this.assessmentService.update(assessment));
    } else {
      this.subscribeToSaveResponse(this.assessmentService.create(assessment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssessment>>): void {
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

  protected updateForm(assessment: IAssessment): void {
    this.assessment = assessment;
    this.assessmentFormService.resetForm(this.editForm, assessment);

    this.evaluatorsSharedCollection = this.evaluatorService.addEvaluatorToCollectionIfMissing<IEvaluator>(
      this.evaluatorsSharedCollection,
      assessment.evaluator
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      assessment.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.evaluatorService
      .query()
      .pipe(map((res: HttpResponse<IEvaluator[]>) => res.body ?? []))
      .pipe(
        map((evaluators: IEvaluator[]) =>
          this.evaluatorService.addEvaluatorToCollectionIfMissing<IEvaluator>(evaluators, this.assessment?.evaluator)
        )
      )
      .subscribe((evaluators: IEvaluator[]) => (this.evaluatorsSharedCollection = evaluators));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.assessment?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
