import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentType, NewDocumentType } from '../document-type.model';

export type PartialUpdateDocumentType = Partial<IDocumentType> & Pick<IDocumentType, 'id'>;

export type EntityResponseType = HttpResponse<IDocumentType>;
export type EntityArrayResponseType = HttpResponse<IDocumentType[]>;

@Injectable({ providedIn: 'root' })
export class DocumentTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentType: NewDocumentType): Observable<EntityResponseType> {
    return this.http.post<IDocumentType>(this.resourceUrl, documentType, { observe: 'response' });
  }

  update(documentType: IDocumentType): Observable<EntityResponseType> {
    return this.http.put<IDocumentType>(`${this.resourceUrl}/${this.getDocumentTypeIdentifier(documentType)}`, documentType, {
      observe: 'response',
    });
  }

  partialUpdate(documentType: PartialUpdateDocumentType): Observable<EntityResponseType> {
    return this.http.patch<IDocumentType>(`${this.resourceUrl}/${this.getDocumentTypeIdentifier(documentType)}`, documentType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentTypeIdentifier(documentType: Pick<IDocumentType, 'id'>): number {
    return documentType.id;
  }

  compareDocumentType(o1: Pick<IDocumentType, 'id'> | null, o2: Pick<IDocumentType, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentTypeIdentifier(o1) === this.getDocumentTypeIdentifier(o2) : o1 === o2;
  }

  addDocumentTypeToCollectionIfMissing<Type extends Pick<IDocumentType, 'id'>>(
    documentTypeCollection: Type[],
    ...documentTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentTypes: Type[] = documentTypesToCheck.filter(isPresent);
    if (documentTypes.length > 0) {
      const documentTypeCollectionIdentifiers = documentTypeCollection.map(
        documentTypeItem => this.getDocumentTypeIdentifier(documentTypeItem)!
      );
      const documentTypesToAdd = documentTypes.filter(documentTypeItem => {
        const documentTypeIdentifier = this.getDocumentTypeIdentifier(documentTypeItem);
        if (documentTypeCollectionIdentifiers.includes(documentTypeIdentifier)) {
          return false;
        }
        documentTypeCollectionIdentifiers.push(documentTypeIdentifier);
        return true;
      });
      return [...documentTypesToAdd, ...documentTypeCollection];
    }
    return documentTypeCollection;
  }
}
