import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../observation-assessment.test-samples';

import { ObservationAssessmentFormService } from './observation-assessment-form.service';

describe('ObservationAssessment Form Service', () => {
  let service: ObservationAssessmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObservationAssessmentFormService);
  });

  describe('Service methods', () => {
    describe('createObservationAssessmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createObservationAssessmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            observationGeneral: expect.any(Object),
            appropriationEvaluation: expect.any(Object),
            observationtraining: expect.any(Object),
            assessment: expect.any(Object),
          })
        );
      });

      it('passing IObservationAssessment should create a new form with FormGroup', () => {
        const formGroup = service.createObservationAssessmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            observationGeneral: expect.any(Object),
            appropriationEvaluation: expect.any(Object),
            observationtraining: expect.any(Object),
            assessment: expect.any(Object),
          })
        );
      });
    });

    describe('getObservationAssessment', () => {
      it('should return NewObservationAssessment for default ObservationAssessment initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createObservationAssessmentFormGroup(sampleWithNewData);

        const observationAssessment = service.getObservationAssessment(formGroup) as any;

        expect(observationAssessment).toMatchObject(sampleWithNewData);
      });

      it('should return NewObservationAssessment for empty ObservationAssessment initial value', () => {
        const formGroup = service.createObservationAssessmentFormGroup();

        const observationAssessment = service.getObservationAssessment(formGroup) as any;

        expect(observationAssessment).toMatchObject({});
      });

      it('should return IObservationAssessment', () => {
        const formGroup = service.createObservationAssessmentFormGroup(sampleWithRequiredData);

        const observationAssessment = service.getObservationAssessment(formGroup) as any;

        expect(observationAssessment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IObservationAssessment should not enable id FormControl', () => {
        const formGroup = service.createObservationAssessmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewObservationAssessment should disable id FormControl', () => {
        const formGroup = service.createObservationAssessmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
