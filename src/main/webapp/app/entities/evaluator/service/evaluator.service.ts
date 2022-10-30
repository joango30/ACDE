import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluator, NewEvaluator } from '../evaluator.model';

export type PartialUpdateEvaluator = Partial<IEvaluator> & Pick<IEvaluator, 'id'>;

export type EntityResponseType = HttpResponse<IEvaluator>;
export type EntityArrayResponseType = HttpResponse<IEvaluator[]>;

@Injectable({ providedIn: 'root' })
export class EvaluatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(evaluator: NewEvaluator): Observable<EntityResponseType> {
    return this.http.post<IEvaluator>(this.resourceUrl, evaluator, { observe: 'response' });
  }

  update(evaluator: IEvaluator): Observable<EntityResponseType> {
    return this.http.put<IEvaluator>(`${this.resourceUrl}/${this.getEvaluatorIdentifier(evaluator)}`, evaluator, { observe: 'response' });
  }

  partialUpdate(evaluator: PartialUpdateEvaluator): Observable<EntityResponseType> {
    return this.http.patch<IEvaluator>(`${this.resourceUrl}/${this.getEvaluatorIdentifier(evaluator)}`, evaluator, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEvaluatorIdentifier(evaluator: Pick<IEvaluator, 'id'>): number {
    return evaluator.id;
  }

  compareEvaluator(o1: Pick<IEvaluator, 'id'> | null, o2: Pick<IEvaluator, 'id'> | null): boolean {
    return o1 && o2 ? this.getEvaluatorIdentifier(o1) === this.getEvaluatorIdentifier(o2) : o1 === o2;
  }

  addEvaluatorToCollectionIfMissing<Type extends Pick<IEvaluator, 'id'>>(
    evaluatorCollection: Type[],
    ...evaluatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const evaluators: Type[] = evaluatorsToCheck.filter(isPresent);
    if (evaluators.length > 0) {
      const evaluatorCollectionIdentifiers = evaluatorCollection.map(evaluatorItem => this.getEvaluatorIdentifier(evaluatorItem)!);
      const evaluatorsToAdd = evaluators.filter(evaluatorItem => {
        const evaluatorIdentifier = this.getEvaluatorIdentifier(evaluatorItem);
        if (evaluatorCollectionIdentifiers.includes(evaluatorIdentifier)) {
          return false;
        }
        evaluatorCollectionIdentifiers.push(evaluatorIdentifier);
        return true;
      });
      return [...evaluatorsToAdd, ...evaluatorCollection];
    }
    return evaluatorCollection;
  }
}
