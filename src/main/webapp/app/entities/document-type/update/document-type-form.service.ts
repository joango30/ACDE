import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDocumentType, NewDocumentType } from '../document-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentType for edit and NewDocumentTypeFormGroupInput for create.
 */
type DocumentTypeFormGroupInput = IDocumentType | PartialWithRequiredKeyOf<NewDocumentType>;

type DocumentTypeFormDefaults = Pick<NewDocumentType, 'id'>;

type DocumentTypeFormGroupContent = {
  id: FormControl<IDocumentType['id'] | NewDocumentType['id']>;
  initials: FormControl<IDocumentType['initials']>;
  documentName: FormControl<IDocumentType['documentName']>;
};

export type DocumentTypeFormGroup = FormGroup<DocumentTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentTypeFormService {
  createDocumentTypeFormGroup(documentType: DocumentTypeFormGroupInput = { id: null }): DocumentTypeFormGroup {
    const documentTypeRawValue = {
      ...this.getFormDefaults(),
      ...documentType,
    };
    return new FormGroup<DocumentTypeFormGroupContent>({
      id: new FormControl(
        { value: documentTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      initials: new FormControl(documentTypeRawValue.initials, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      documentName: new FormControl(documentTypeRawValue.documentName, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
    });
  }

  getDocumentType(form: DocumentTypeFormGroup): IDocumentType | NewDocumentType {
    return form.getRawValue() as IDocumentType | NewDocumentType;
  }

  resetForm(form: DocumentTypeFormGroup, documentType: DocumentTypeFormGroupInput): void {
    const documentTypeRawValue = { ...this.getFormDefaults(), ...documentType };
    form.reset(
      {
        ...documentTypeRawValue,
        id: { value: documentTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentTypeFormDefaults {
    return {
      id: null,
    };
  }
}
