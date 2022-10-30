import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAssessment, NewAssessment } from '../assessment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssessment for edit and NewAssessmentFormGroupInput for create.
 */
type AssessmentFormGroupInput = IAssessment | PartialWithRequiredKeyOf<NewAssessment>;

type AssessmentFormDefaults = Pick<NewAssessment, 'id'>;

type AssessmentFormGroupContent = {
  id: FormControl<IAssessment['id'] | NewAssessment['id']>;
  assessmentNumber: FormControl<IAssessment['assessmentNumber']>;
  assessmentType: FormControl<IAssessment['assessmentType']>;
  dateassessment: FormControl<IAssessment['dateassessment']>;
  assessmentTotal: FormControl<IAssessment['assessmentTotal']>;
  evaluator: FormControl<IAssessment['evaluator']>;
  employee: FormControl<IAssessment['employee']>;
};

export type AssessmentFormGroup = FormGroup<AssessmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssessmentFormService {
  createAssessmentFormGroup(assessment: AssessmentFormGroupInput = { id: null }): AssessmentFormGroup {
    const assessmentRawValue = {
      ...this.getFormDefaults(),
      ...assessment,
    };
    return new FormGroup<AssessmentFormGroupContent>({
      id: new FormControl(
        { value: assessmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      assessmentNumber: new FormControl(assessmentRawValue.assessmentNumber, {
        validators: [Validators.required],
      }),
      assessmentType: new FormControl(assessmentRawValue.assessmentType, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      dateassessment: new FormControl(assessmentRawValue.dateassessment, {
        validators: [Validators.required],
      }),
      assessmentTotal: new FormControl(assessmentRawValue.assessmentTotal, {
        validators: [Validators.required],
      }),
      evaluator: new FormControl(assessmentRawValue.evaluator),
      employee: new FormControl(assessmentRawValue.employee),
    });
  }

  getAssessment(form: AssessmentFormGroup): IAssessment | NewAssessment {
    return form.getRawValue() as IAssessment | NewAssessment;
  }

  resetForm(form: AssessmentFormGroup, assessment: AssessmentFormGroupInput): void {
    const assessmentRawValue = { ...this.getFormDefaults(), ...assessment };
    form.reset(
      {
        ...assessmentRawValue,
        id: { value: assessmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AssessmentFormDefaults {
    return {
      id: null,
    };
  }
}
