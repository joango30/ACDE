import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluator } from '../evaluator.model';

@Component({
  selector: 'acde-evaluator-detail',
  templateUrl: './evaluator-detail.component.html',
})
export class EvaluatorDetailComponent implements OnInit {
  evaluator: IEvaluator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluator }) => {
      this.evaluator = evaluator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
