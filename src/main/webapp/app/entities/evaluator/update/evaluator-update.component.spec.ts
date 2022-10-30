import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EvaluatorFormService } from './evaluator-form.service';
import { EvaluatorService } from '../service/evaluator.service';
import { IEvaluator } from '../evaluator.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { DocumentTypeService } from 'app/entities/document-type/service/document-type.service';

import { EvaluatorUpdateComponent } from './evaluator-update.component';

describe('Evaluator Management Update Component', () => {
  let comp: EvaluatorUpdateComponent;
  let fixture: ComponentFixture<EvaluatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let evaluatorFormService: EvaluatorFormService;
  let evaluatorService: EvaluatorService;
  let userService: UserService;
  let documentTypeService: DocumentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EvaluatorUpdateComponent],
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
      .overrideTemplate(EvaluatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EvaluatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    evaluatorFormService = TestBed.inject(EvaluatorFormService);
    evaluatorService = TestBed.inject(EvaluatorService);
    userService = TestBed.inject(UserService);
    documentTypeService = TestBed.inject(DocumentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const evaluator: IEvaluator = { id: 456 };
      const user: IUser = { id: 53258 };
      evaluator.user = user;

      const userCollection: IUser[] = [{ id: 34445 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ evaluator });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DocumentType query and add missing value', () => {
      const evaluator: IEvaluator = { id: 456 };
      const documentType: IDocumentType = { id: 83261 };
      evaluator.documentType = documentType;

      const documentTypeCollection: IDocumentType[] = [{ id: 45052 }];
      jest.spyOn(documentTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: documentTypeCollection })));
      const additionalDocumentTypes = [documentType];
      const expectedCollection: IDocumentType[] = [...additionalDocumentTypes, ...documentTypeCollection];
      jest.spyOn(documentTypeService, 'addDocumentTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ evaluator });
      comp.ngOnInit();

      expect(documentTypeService.query).toHaveBeenCalled();
      expect(documentTypeService.addDocumentTypeToCollectionIfMissing).toHaveBeenCalledWith(
        documentTypeCollection,
        ...additionalDocumentTypes.map(expect.objectContaining)
      );
      expect(comp.documentTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const evaluator: IEvaluator = { id: 456 };
      const user: IUser = { id: 65654 };
      evaluator.user = user;
      const documentType: IDocumentType = { id: 34274 };
      evaluator.documentType = documentType;

      activatedRoute.data = of({ evaluator });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.documentTypesSharedCollection).toContain(documentType);
      expect(comp.evaluator).toEqual(evaluator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvaluator>>();
      const evaluator = { id: 123 };
      jest.spyOn(evaluatorFormService, 'getEvaluator').mockReturnValue(evaluator);
      jest.spyOn(evaluatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evaluator }));
      saveSubject.complete();

      // THEN
      expect(evaluatorFormService.getEvaluator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(evaluatorService.update).toHaveBeenCalledWith(expect.objectContaining(evaluator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvaluator>>();
      const evaluator = { id: 123 };
      jest.spyOn(evaluatorFormService, 'getEvaluator').mockReturnValue({ id: null });
      jest.spyOn(evaluatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: evaluator }));
      saveSubject.complete();

      // THEN
      expect(evaluatorFormService.getEvaluator).toHaveBeenCalled();
      expect(evaluatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEvaluator>>();
      const evaluator = { id: 123 };
      jest.spyOn(evaluatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ evaluator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(evaluatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDocumentType', () => {
      it('Should forward to documentTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentTypeService, 'compareDocumentType');
        comp.compareDocumentType(entity, entity2);
        expect(documentTypeService.compareDocumentType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
