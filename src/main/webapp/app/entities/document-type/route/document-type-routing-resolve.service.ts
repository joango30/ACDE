import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentType } from '../document-type.model';
import { DocumentTypeService } from '../service/document-type.service';

@Injectable({ providedIn: 'root' })
export class DocumentTypeRoutingResolveService implements Resolve<IDocumentType | null> {
  constructor(protected service: DocumentTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentType: HttpResponse<IDocumentType>) => {
          if (documentType.body) {
            return of(documentType.body);
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
