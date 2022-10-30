import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IObservationAssessment } from '../observation-assessment.model';
import { ObservationAssessmentService } from '../service/observation-assessment.service';

@Injectable({ providedIn: 'root' })
export class ObservationAssessmentRoutingResolveService implements Resolve<IObservationAssessment | null> {
  constructor(protected service: ObservationAssessmentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObservationAssessment | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((observationAssessment: HttpResponse<IObservationAssessment>) => {
          if (observationAssessment.body) {
            return of(observationAssessment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
