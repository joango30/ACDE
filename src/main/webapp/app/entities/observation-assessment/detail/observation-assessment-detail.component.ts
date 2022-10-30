import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObservationAssessment } from '../observation-assessment.model';

@Component({
  selector: 'acde-observation-assessment-detail',
  templateUrl: './observation-assessment-detail.component.html',
})
export class ObservationAssessmentDetailComponent implements OnInit {
  observationAssessment: IObservationAssessment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observationAssessment }) => {
      this.observationAssessment = observationAssessment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
