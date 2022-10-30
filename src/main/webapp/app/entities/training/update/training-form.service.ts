import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITraining, NewTraining } from '../training.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITraining for edit and NewTrainingFormGroupInput for create.
 */
type TrainingFormGroupInput = ITraining | PartialWithRequiredKeyOf<NewTraining>;

type TrainingFormDefaults = Pick<NewTraining, 'id'>;

type TrainingFormGroupContent = {
  id: FormControl<ITraining['id'] | NewTraining['id']>;
  trainigNumber: FormControl<ITraining['trainigNumber']>;
  startDate: FormControl<ITraining['startDate']>;
  endDate: FormControl<ITraining['endDate']>;
  trainingName: FormControl<ITraining['trainingName']>;
  statusName: FormControl<ITraining['statusName']>;
  evaluator: FormControl<ITraining['evaluator']>;
};

export type TrainingFormGroup = FormGroup<TrainingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainingFormService {
  createTrainingFormGroup(training: TrainingFormGroupInput = { id: null }): TrainingFormGroup {
    const trainingRawValue = {
      ...this.getFormDefaults(),
      ...training,
    };
    return new FormGroup<TrainingFormGroupContent>({
      id: new FormControl(
        { value: trainingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      trainigNumber: new FormControl(trainingRawValue.trainigNumber, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      startDate: new FormControl(trainingRawValue.startDate, {
        validators: [Validators.required],
      }),
      endDate: new FormControl(trainingRawValue.endDate, {
        validators: [Validators.required],
      }),
      trainingName: new FormControl(trainingRawValue.trainingName, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      statusName: new FormControl(trainingRawValue.statusName, {
        validators: [Validators.required, Validators.maxLength(40)],
      }),
      evaluator: new FormControl(trainingRawValue.evaluator),
    });
  }

  getTraining(form: TrainingFormGroup): ITraining | NewTraining {
    return form.getRawValue() as ITraining | NewTraining;
  }

  resetForm(form: TrainingFormGroup, training: TrainingFormGroupInput): void {
    const trainingRawValue = { ...this.getFormDefaults(), ...training };
    form.reset(
      {
        ...trainingRawValue,
        id: { value: trainingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainingFormDefaults {
    return {
      id: null,
    };
  }
}
