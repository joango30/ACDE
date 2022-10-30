import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluator } from '../evaluator.model';
import { EvaluatorService } from '../service/evaluator.service';

@Injectable({ providedIn: 'root' })
export class EvaluatorRoutingResolveService implements Resolve<IEvaluator | null> {
  constructor(protected service: EvaluatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluator: HttpResponse<IEvaluator>) => {
          if (evaluator.body) {
            return of(evaluator.body);
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
