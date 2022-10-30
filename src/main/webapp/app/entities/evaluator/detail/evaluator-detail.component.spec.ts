import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluatorDetailComponent } from './evaluator-detail.component';

describe('Evaluator Management Detail Component', () => {
  let comp: EvaluatorDetailComponent;
  let fixture: ComponentFixture<EvaluatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvaluatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ evaluator: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EvaluatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EvaluatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load evaluator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.evaluator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
