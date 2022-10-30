import { IObservationAssessment, NewObservationAssessment } from './observation-assessment.model';

export const sampleWithRequiredData: IObservationAssessment = {
  id: 72352,
  observationGeneral: 'China Actualizable',
  appropriationEvaluation: 'Unit turn-key innovative',
  observationtraining: 'Acero',
};

export const sampleWithPartialData: IObservationAssessment = {
  id: 17364,
  observationGeneral: 'Riera Central',
  appropriationEvaluation: 'Marroquinería Informática',
  observationtraining: 'utilización Credit Datos',
};

export const sampleWithFullData: IObservationAssessment = {
  id: 27950,
  observationGeneral: 'infraestructura Avon metodologías',
  appropriationEvaluation: 'functionalities deposit',
  observationtraining: 'Account',
};

export const sampleWithNewData: NewObservationAssessment = {
  observationGeneral: 'Account innovative',
  appropriationEvaluation: 'TCP',
  observationtraining: 'Sierra',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
