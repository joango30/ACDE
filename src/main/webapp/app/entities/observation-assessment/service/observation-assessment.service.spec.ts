import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IObservationAssessment } from '../observation-assessment.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../observation-assessment.test-samples';

import { ObservationAssessmentService } from './observation-assessment.service';

const requireRestSample: IObservationAssessment = {
  ...sampleWithRequiredData,
};

describe('ObservationAssessment Service', () => {
  let service: ObservationAssessmentService;
  let httpMock: HttpTestingController;
  let expectedResult: IObservationAssessment | IObservationAssessment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ObservationAssessmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ObservationAssessment', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const observationAssessment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(observationAssessment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ObservationAssessment', () => {
      const observationAssessment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(observationAssessment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ObservationAssessment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ObservationAssessment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ObservationAssessment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addObservationAssessmentToCollectionIfMissing', () => {
      it('should add a ObservationAssessment to an empty array', () => {
        const observationAssessment: IObservationAssessment = sampleWithRequiredData;
        expectedResult = service.addObservationAssessmentToCollectionIfMissing([], observationAssessment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observationAssessment);
      });

      it('should not add a ObservationAssessment to an array that contains it', () => {
        const observationAssessment: IObservationAssessment = sampleWithRequiredData;
        const observationAssessmentCollection: IObservationAssessment[] = [
          {
            ...observationAssessment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addObservationAssessmentToCollectionIfMissing(observationAssessmentCollection, observationAssessment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ObservationAssessment to an array that doesn't contain it", () => {
        const observationAssessment: IObservationAssessment = sampleWithRequiredData;
        const observationAssessmentCollection: IObservationAssessment[] = [sampleWithPartialData];
        expectedResult = service.addObservationAssessmentToCollectionIfMissing(observationAssessmentCollection, observationAssessment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observationAssessment);
      });

      it('should add only unique ObservationAssessment to an array', () => {
        const observationAssessmentArray: IObservationAssessment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const observationAssessmentCollection: IObservationAssessment[] = [sampleWithRequiredData];
        expectedResult = service.addObservationAssessmentToCollectionIfMissing(
          observationAssessmentCollection,
          ...observationAssessmentArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const observationAssessment: IObservationAssessment = sampleWithRequiredData;
        const observationAssessment2: IObservationAssessment = sampleWithPartialData;
        expectedResult = service.addObservationAssessmentToCollectionIfMissing([], observationAssessment, observationAssessment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(observationAssessment);
        expect(expectedResult).toContain(observationAssessment2);
      });

      it('should accept null and undefined values', () => {
        const observationAssessment: IObservationAssessment = sampleWithRequiredData;
        expectedResult = service.addObservationAssessmentToCollectionIfMissing([], null, observationAssessment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(observationAssessment);
      });

      it('should return initial array if no ObservationAssessment is added', () => {
        const observationAssessmentCollection: IObservationAssessment[] = [sampleWithRequiredData];
        expectedResult = service.addObservationAssessmentToCollectionIfMissing(observationAssessmentCollection, undefined, null);
        expect(expectedResult).toEqual(observationAssessmentCollection);
      });
    });

    describe('compareObservationAssessment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareObservationAssessment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareObservationAssessment(entity1, entity2);
        const compareResult2 = service.compareObservationAssessment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareObservationAssessment(entity1, entity2);
        const compareResult2 = service.compareObservationAssessment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareObservationAssessment(entity1, entity2);
        const compareResult2 = service.compareObservationAssessment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
