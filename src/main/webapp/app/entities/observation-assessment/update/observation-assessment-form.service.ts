import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IObservationAssessment, NewObservationAssessment } from '../observation-assessment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IObservationAssessment for edit and NewObservationAssessmentFormGroupInput for create.
 */
type ObservationAssessmentFormGroupInput = IObservationAssessment | PartialWithRequiredKeyOf<NewObservationAssessment>;

type ObservationAssessmentFormDefaults = Pick<NewObservationAssessment, 'id'>;

type ObservationAssessmentFormGroupContent = {
  id: FormControl<IObservationAssessment['id'] | NewObservationAssessment['id']>;
  observationGeneral: FormControl<IObservationAssessment['observationGeneral']>;
  appropriationEvaluation: FormControl<IObservationAssessment['appropriationEvaluation']>;
  observationtraining: FormControl<IObservationAssessment['observationtraining']>;
  assessment: FormControl<IObservationAssessment['assessment']>;
};

export type ObservationAssessmentFormGroup = FormGroup<ObservationAssessmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ObservationAssessmentFormService {
  createObservationAssessmentFormGroup(
    observationAssessment: ObservationAssessmentFormGroupInput = { id: null }
  ): ObservationAssessmentFormGroup {
    const observationAssessmentRawValue = {
      ...this.getFormDefaults(),
      ...observationAssessment,
    };
    return new FormGroup<ObservationAssessmentFormGroupContent>({
      id: new FormControl(
        { value: observationAssessmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      observationGeneral: new FormControl(observationAssessmentRawValue.observationGeneral, {
        validators: [Validators.required, Validators.maxLength(250)],
      }),
      appropriationEvaluation: new FormControl(observationAssessmentRawValue.appropriationEvaluation, {
        validators: [Validators.required, Validators.maxLength(250)],
      }),
      observationtraining: new FormControl(observationAssessmentRawValue.observationtraining, {
        validators: [Validators.required, Validators.maxLength(250)],
      }),
      assessment: new FormControl(observationAssessmentRawValue.assessment),
    });
  }

  getObservationAssessment(form: ObservationAssessmentFormGroup): IObservationAssessment | NewObservationAssessment {
    return form.getRawValue() as IObservationAssessment | NewObservationAssessment;
  }

  resetForm(form: ObservationAssessmentFormGroup, observationAssessment: ObservationAssessmentFormGroupInput): void {
    const observationAssessmentRawValue = { ...this.getFormDefaults(), ...observationAssessment };
    form.reset(
      {
        ...observationAssessmentRawValue,
        id: { value: observationAssessmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ObservationAssessmentFormDefaults {
    return {
      id: null,
    };
  }
}
