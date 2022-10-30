import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EvaluatorFormService, EvaluatorFormGroup } from './evaluator-form.service';
import { IEvaluator } from '../evaluator.model';
import { EvaluatorService } from '../service/evaluator.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocumentType } from 'app/entities/document-type/document-type.model';
import { DocumentTypeService } from 'app/entities/document-type/service/document-type.service';

@Component({
  selector: 'acde-evaluator-update',
  templateUrl: './evaluator-update.component.html',
})
export class EvaluatorUpdateComponent implements OnInit {
  isSaving = false;
  evaluator: IEvaluator | null = null;

  usersSharedCollection: IUser[] = [];
  documentTypesSharedCollection: IDocumentType[] = [];

  editForm: EvaluatorFormGroup = this.evaluatorFormService.createEvaluatorFormGroup();

  constructor(
    protected evaluatorService: EvaluatorService,
    protected evaluatorFormService: EvaluatorFormService,
    protected userService: UserService,
    protected documentTypeService: DocumentTypeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDocumentType = (o1: IDocumentType | null, o2: IDocumentType | null): boolean =>
    this.documentTypeService.compareDocumentType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluator }) => {
      this.evaluator = evaluator;
      if (evaluator) {
        this.updateForm(evaluator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluator = this.evaluatorFormService.getEvaluator(this.editForm);
    if (evaluator.id !== null) {
      this.subscribeToSaveResponse(this.evaluatorService.update(evaluator));
    } else {
      this.subscribeToSaveResponse(this.evaluatorService.create(evaluator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluator>>): void {
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

  protected updateForm(evaluator: IEvaluator): void {
    this.evaluator = evaluator;
    this.evaluatorFormService.resetForm(this.editForm, evaluator);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, evaluator.user);
    this.documentTypesSharedCollection = this.documentTypeService.addDocumentTypeToCollectionIfMissing<IDocumentType>(
      this.documentTypesSharedCollection,
      evaluator.documentType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.evaluator?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.documentTypeService
      .query()
      .pipe(map((res: HttpResponse<IDocumentType[]>) => res.body ?? []))
      .pipe(
        map((documentTypes: IDocumentType[]) =>
          this.documentTypeService.addDocumentTypeToCollectionIfMissing<IDocumentType>(documentTypes, this.evaluator?.documentType)
        )
      )
      .subscribe((documentTypes: IDocumentType[]) => (this.documentTypesSharedCollection = documentTypes));
  }
}
