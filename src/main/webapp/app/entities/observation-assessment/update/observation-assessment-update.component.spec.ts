import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ObservationAssessmentFormService } from './observation-assessment-form.service';
import { ObservationAssessmentService } from '../service/observation-assessment.service';
import { IObservationAssessment } from '../observation-assessment.model';
import { IAssessment } from 'app/entities/assessment/assessment.model';
import { AssessmentService } from 'app/entities/assessment/service/assessment.service';

import { ObservationAssessmentUpdateComponent } from './observation-assessment-update.component';

describe('ObservationAssessment Management Update Component', () => {
  let comp: ObservationAssessmentUpdateComponent;
  let fixture: ComponentFixture<ObservationAssessmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let observationAssessmentFormService: ObservationAssessmentFormService;
  let observationAssessmentService: ObservationAssessmentService;
  let assessmentService: AssessmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ObservationAssessmentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ObservationAssessmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ObservationAssessmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    observationAssessmentFormService = TestBed.inject(ObservationAssessmentFormService);
    observationAssessmentService = TestBed.inject(ObservationAssessmentService);
    assessmentService = TestBed.inject(AssessmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Assessment query and add missing value', () => {
      const observationAssessment: IObservationAssessment = { id: 456 };
      const assessment: IAssessment = { id: 80287 };
      observationAssessment.assessment = assessment;

      const assessmentCollection: IAssessment[] = [{ id: 48675 }];
      jest.spyOn(assessmentService, 'query').mockReturnValue(of(new HttpResponse({ body: assessmentCollection })));
      const additionalAssessments = [assessment];
      const expectedCollection: IAssessment[] = [...additionalAssessments, ...assessmentCollection];
      jest.spyOn(assessmentService, 'addAssessmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ observationAssessment });
      comp.ngOnInit();

      expect(assessmentService.query).toHaveBeenCalled();
      expect(assessmentService.addAssessmentToCollectionIfMissing).toHaveBeenCalledWith(
        assessmentCollection,
        ...additionalAssessments.map(expect.objectContaining)
      );
      expect(comp.assessmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const observationAssessment: IObservationAssessment = { id: 456 };
      const assessment: IAssessment = { id: 60188 };
      observationAssessment.assessment = assessment;

      activatedRoute.data = of({ observationAssessment });
      comp.ngOnInit();

      expect(comp.assessmentsSharedCollection).toContain(assessment);
      expect(comp.observationAssessment).toEqual(observationAssessment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservationAssessment>>();
      const observationAssessment = { id: 123 };
      jest.spyOn(observationAssessmentFormService, 'getObservationAssessment').mockReturnValue(observationAssessment);
      jest.spyOn(observationAssessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observationAssessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observationAssessment }));
      saveSubject.complete();

      // THEN
      expect(observationAssessmentFormService.getObservationAssessment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(observationAssessmentService.update).toHaveBeenCalledWith(expect.objectContaining(observationAssessment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservationAssessment>>();
      const observationAssessment = { id: 123 };
      jest.spyOn(observationAssessmentFormService, 'getObservationAssessment').mockReturnValue({ id: null });
      jest.spyOn(observationAssessmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observationAssessment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: observationAssessment }));
      saveSubject.complete();

      // THEN
      expect(observationAssessmentFormService.getObservationAssessment).toHaveBeenCalled();
      expect(observationAssessmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IObservationAssessment>>();
      const observationAssessment = { id: 123 };
      jest.spyOn(observationAssessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ observationAssessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(observationAssessmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAssessment', () => {
      it('Should forward to assessmentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assessmentService, 'compareAssessment');
        comp.compareAssessment(entity, entity2);
        expect(assessmentService.compareAssessment).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
