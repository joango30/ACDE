import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssessmentFormService } from './assessment-form.service';
import { AssessmentService } from '../service/assessment.service';
import { IAssessment } from '../assessment.model';
import { IEvaluator } from 'app/entities/evaluator/evaluator.model';
import { EvaluatorService } from 'app/entities/evaluator/service/evaluator.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { AssessmentUpdateComponent } from './assessment-update.component';

describe('Assessment Management Update Component', () => {
  let comp: AssessmentUpdateComponent;
  let fixture: ComponentFixture<AssessmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assessmentFormService: AssessmentFormService;
  let assessmentService: AssessmentService;
  let evaluatorService: EvaluatorService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssessmentUpdateComponent],
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
      .overrideTemplate(AssessmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssessmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assessmentFormService = TestBed.inject(AssessmentFormService);
    assessmentService = TestBed.inject(AssessmentService);
    evaluatorService = TestBed.inject(EvaluatorService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Evaluator query and add missing value', () => {
      const assessment: IAssessment = { id: 456 };
      const evaluator: IEvaluator = { id: 13634 };
      assessment.evaluator = evaluator;

      const evaluatorCollection: IEvaluator[] = [{ id: 52852 }];
      jest.spyOn(evaluatorService, 'query').mockReturnValue(of(new HttpResponse({ body: evaluatorCollection })));
      const additionalEvaluators = [evaluator];
      const expectedCollection: IEvaluator[] = [...additionalEvaluators, ...evaluatorCollection];
      jest.spyOn(evaluatorService, 'addEvaluatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      expect(evaluatorService.query).toHaveBeenCalled();
      expect(evaluatorService.addEvaluatorToCollectionIfMissing).toHaveBeenCalledWith(
        evaluatorCollection,
        ...additionalEvaluators.map(expect.objectContaining)
      );
      expect(comp.evaluatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const assessment: IAssessment = { id: 456 };
      const employee: IEmployee = { id: 41600 };
      assessment.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 70407 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assessment: IAssessment = { id: 456 };
      const evaluator: IEvaluator = { id: 57356 };
      assessment.evaluator = evaluator;
      const employee: IEmployee = { id: 10698 };
      assessment.employee = employee;

      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      expect(comp.evaluatorsSharedCollection).toContain(evaluator);
      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.assessment).toEqual(assessment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssessment>>();
      const assessment = { id: 123 };
      jest.spyOn(assessmentFormService, 'getAssessment').mockReturnValue(assessment);
      jest.spyOn(assessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessment }));
      saveSubject.complete();

      // THEN
      expect(assessmentFormService.getAssessment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assessmentService.update).toHaveBeenCalledWith(expect.objectContaining(assessment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssessment>>();
      const assessment = { id: 123 };
      jest.spyOn(assessmentFormService, 'getAssessment').mockReturnValue({ id: null });
      jest.spyOn(assessmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assessment }));
      saveSubject.complete();

      // THEN
      expect(assessmentFormService.getAssessment).toHaveBeenCalled();
      expect(assessmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssessment>>();
      const assessment = { id: 123 };
      jest.spyOn(assessmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assessment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assessmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEvaluator', () => {
      it('Should forward to evaluatorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(evaluatorService, 'compareEvaluator');
        comp.compareEvaluator(entity, entity2);
        expect(evaluatorService.compareEvaluator).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
