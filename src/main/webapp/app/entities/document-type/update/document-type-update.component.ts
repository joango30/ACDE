import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DocumentTypeFormService, DocumentTypeFormGroup } from './document-type-form.service';
import { IDocumentType } from '../document-type.model';
import { DocumentTypeService } from '../service/document-type.service';

@Component({
  selector: 'acde-document-type-update',
  templateUrl: './document-type-update.component.html',
})
export class DocumentTypeUpdateComponent implements OnInit {
  isSaving = false;
  documentType: IDocumentType | null = null;

  editForm: DocumentTypeFormGroup = this.documentTypeFormService.createDocumentTypeFormGroup();

  constructor(
    protected documentTypeService: DocumentTypeService,
    protected documentTypeFormService: DocumentTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentType }) => {
      this.documentType = documentType;
      if (documentType) {
        this.updateForm(documentType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentType = this.documentTypeFormService.getDocumentType(this.editForm);
    if (documentType.id !== null) {
      this.subscribeToSaveResponse(this.documentTypeService.update(documentType));
    } else {
      this.subscribeToSaveResponse(this.documentTypeService.create(documentType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(documentType: IDocumentType): void {
    this.documentType = documentType;
    this.documentTypeFormService.resetForm(this.editForm, documentType);
  }
}
