import { IAssessment } from 'app/entities/assessment/assessment.model';

export interface IObservationAssessment {
  id: number;
  observationGeneral?: string | null;
  appropriationEvaluation?: string | null;
  observationtraining?: string | null;
  assessment?: Pick<IAssessment, 'id'> | null;
}

export type NewObservationAssessment = Omit<IObservationAssessment, 'id'> & { id: null };
