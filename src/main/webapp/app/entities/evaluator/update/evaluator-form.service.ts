import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvaluator, NewEvaluator } from '../evaluator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvaluator for edit and NewEvaluatorFormGroupInput for create.
 */
type EvaluatorFormGroupInput = IEvaluator | PartialWithRequiredKeyOf<NewEvaluator>;

type EvaluatorFormDefaults = Pick<NewEvaluator, 'id'>;

type EvaluatorFormGroupContent = {
  id: FormControl<IEvaluator['id'] | NewEvaluator['id']>;
  phoneNumber: FormControl<IEvaluator['phoneNumber']>;
  email: FormControl<IEvaluator['email']>;
  address: FormControl<IEvaluator['address']>;
  user: FormControl<IEvaluator['user']>;
  documentType: FormControl<IEvaluator['documentType']>;
};

export type EvaluatorFormGroup = FormGroup<EvaluatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EvaluatorFormService {
  createEvaluatorFormGroup(evaluator: EvaluatorFormGroupInput = { id: null }): EvaluatorFormGroup {
    const evaluatorRawValue = {
      ...this.getFormDefaults(),
      ...evaluator,
    };
    return new FormGroup<EvaluatorFormGroupContent>({
      id: new FormControl(
        { value: evaluatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      phoneNumber: new FormControl(evaluatorRawValue.phoneNumber, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      email: new FormControl(evaluatorRawValue.email, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      address: new FormControl(evaluatorRawValue.address, {
        validators: [Validators.maxLength(20)],
      }),
      user: new FormControl(evaluatorRawValue.user, {
        validators: [Validators.required],
      }),
      documentType: new FormControl(evaluatorRawValue.documentType),
    });
  }

  getEvaluator(form: EvaluatorFormGroup): IEvaluator | NewEvaluator {
    return form.getRawValue() as IEvaluator | NewEvaluator;
  }

  resetForm(form: EvaluatorFormGroup, evaluator: EvaluatorFormGroupInput): void {
    const evaluatorRawValue = { ...this.getFormDefaults(), ...evaluator };
    form.reset(
      {
        ...evaluatorRawValue,
        id: { value: evaluatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EvaluatorFormDefaults {
    return {
      id: null,
    };
  }
}
