import dayjs from 'dayjs/esm';

import { IAssessment, NewAssessment } from './assessment.model';

export const sampleWithRequiredData: IAssessment = {
  id: 82522,
  assessmentNumber: 13118,
  assessmentType: 'Belarussian Asturias',
  dateassessment: dayjs('2022-10-29'),
  assessmentTotal: 55108,
};

export const sampleWithPartialData: IAssessment = {
  id: 93284,
  assessmentNumber: 21299,
  assessmentType: 'web-enabled invoice',
  dateassessment: dayjs('2022-10-29'),
  assessmentTotal: 45966,
};

export const sampleWithFullData: IAssessment = {
  id: 75539,
  assessmentNumber: 53587,
  assessmentType: 'bus Genérico Implementación',
  dateassessment: dayjs('2022-10-29'),
  assessmentTotal: 91875,
};

export const sampleWithNewData: NewAssessment = {
  assessmentNumber: 17882,
  assessmentType: 'Entrada Teclado',
  dateassessment: dayjs('2022-10-29'),
  assessmentTotal: 52211,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
