import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObservationAssessmentDetailComponent } from './observation-assessment-detail.component';

describe('ObservationAssessment Management Detail Component', () => {
  let comp: ObservationAssessmentDetailComponent;
  let fixture: ComponentFixture<ObservationAssessmentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ObservationAssessmentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ observationAssessment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ObservationAssessmentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ObservationAssessmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load observationAssessment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.observationAssessment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
