import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObservationAssessment, NewObservationAssessment } from '../observation-assessment.model';

export type PartialUpdateObservationAssessment = Partial<IObservationAssessment> & Pick<IObservationAssessment, 'id'>;

export type EntityResponseType = HttpResponse<IObservationAssessment>;
export type EntityArrayResponseType = HttpResponse<IObservationAssessment[]>;

@Injectable({ providedIn: 'root' })
export class ObservationAssessmentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/observation-assessments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(observationAssessment: NewObservationAssessment): Observable<EntityResponseType> {
    return this.http.post<IObservationAssessment>(this.resourceUrl, observationAssessment, { observe: 'response' });
  }

  update(observationAssessment: IObservationAssessment): Observable<EntityResponseType> {
    return this.http.put<IObservationAssessment>(
      `${this.resourceUrl}/${this.getObservationAssessmentIdentifier(observationAssessment)}`,
      observationAssessment,
      { observe: 'response' }
    );
  }

  partialUpdate(observationAssessment: PartialUpdateObservationAssessment): Observable<EntityResponseType> {
    return this.http.patch<IObservationAssessment>(
      `${this.resourceUrl}/${this.getObservationAssessmentIdentifier(observationAssessment)}`,
      observationAssessment,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObservationAssessment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObservationAssessment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getObservationAssessmentIdentifier(observationAssessment: Pick<IObservationAssessment, 'id'>): number {
    return observationAssessment.id;
  }

  compareObservationAssessment(o1: Pick<IObservationAssessment, 'id'> | null, o2: Pick<IObservationAssessment, 'id'> | null): boolean {
    return o1 && o2 ? this.getObservationAssessmentIdentifier(o1) === this.getObservationAssessmentIdentifier(o2) : o1 === o2;
  }

  addObservationAssessmentToCollectionIfMissing<Type extends Pick<IObservationAssessment, 'id'>>(
    observationAssessmentCollection: Type[],
    ...observationAssessmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const observationAssessments: Type[] = observationAssessmentsToCheck.filter(isPresent);
    if (observationAssessments.length > 0) {
      const observationAssessmentCollectionIdentifiers = observationAssessmentCollection.map(
        observationAssessmentItem => this.getObservationAssessmentIdentifier(observationAssessmentItem)!
      );
      const observationAssessmentsToAdd = observationAssessments.filter(observationAssessmentItem => {
        const observationAssessmentIdentifier = this.getObservationAssessmentIdentifier(observationAssessmentItem);
        if (observationAssessmentCollectionIdentifiers.includes(observationAssessmentIdentifier)) {
          return false;
        }
        observationAssessmentCollectionIdentifiers.push(observationAssessmentIdentifier);
        return true;
      });
      return [...observationAssessmentsToAdd, ...observationAssessmentCollection];
    }
    return observationAssessmentCollection;
  }
}
