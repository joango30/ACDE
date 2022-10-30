import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumentTypeFormService } from './document-type-form.service';
import { DocumentTypeService } from '../service/document-type.service';
import { IDocumentType } from '../document-type.model';

import { DocumentTypeUpdateComponent } from './document-type-update.component';

describe('DocumentType Management Update Component', () => {
  let comp: DocumentTypeUpdateComponent;
  let fixture: ComponentFixture<DocumentTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentTypeFormService: DocumentTypeFormService;
  let documentTypeService: DocumentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumentTypeUpdateComponent],
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
      .overrideTemplate(DocumentTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentTypeFormService = TestBed.inject(DocumentTypeFormService);
    documentTypeService = TestBed.inject(DocumentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const documentType: IDocumentType = { id: 456 };

      activatedRoute.data = of({ documentType });
      comp.ngOnInit();

      expect(comp.documentType).toEqual(documentType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentType>>();
      const documentType = { id: 123 };
      jest.spyOn(documentTypeFormService, 'getDocumentType').mockReturnValue(documentType);
      jest.spyOn(documentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentType }));
      saveSubject.complete();

      // THEN
      expect(documentTypeFormService.getDocumentType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentTypeService.update).toHaveBeenCalledWith(expect.objectContaining(documentType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentType>>();
      const documentType = { id: 123 };
      jest.spyOn(documentTypeFormService, 'getDocumentType').mockReturnValue({ id: null });
      jest.spyOn(documentTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentType }));
      saveSubject.complete();

      // THEN
      expect(documentTypeFormService.getDocumentType).toHaveBeenCalled();
      expect(documentTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentType>>();
      const documentType = { id: 123 };
      jest.spyOn(documentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
