import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../document-type.test-samples';

import { DocumentTypeFormService } from './document-type-form.service';

describe('DocumentType Form Service', () => {
  let service: DocumentTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentTypeFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            initials: expect.any(Object),
            documentName: expect.any(Object),
          })
        );
      });

      it('passing IDocumentType should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            initials: expect.any(Object),
            documentName: expect.any(Object),
          })
        );
      });
    });

    describe('getDocumentType', () => {
      it('should return NewDocumentType for default DocumentType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocumentTypeFormGroup(sampleWithNewData);

        const documentType = service.getDocumentType(formGroup) as any;

        expect(documentType).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentType for empty DocumentType initial value', () => {
        const formGroup = service.createDocumentTypeFormGroup();

        const documentType = service.getDocumentType(formGroup) as any;

        expect(documentType).toMatchObject({});
      });

      it('should return IDocumentType', () => {
        const formGroup = service.createDocumentTypeFormGroup(sampleWithRequiredData);

        const documentType = service.getDocumentType(formGroup) as any;

        expect(documentType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentType should not enable id FormControl', () => {
        const formGroup = service.createDocumentTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentType should disable id FormControl', () => {
        const formGroup = service.createDocumentTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
