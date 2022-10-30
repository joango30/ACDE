import dayjs from 'dayjs/esm';

import { ITraining, NewTraining } from './training.model';

export const sampleWithRequiredData: ITraining = {
  id: 91438,
  trainigNumber: 'withdrawal Ensalada',
  startDate: dayjs('2022-10-29'),
  endDate: dayjs('2022-10-30'),
  trainingName: 'quantifying',
  statusName: 'regional CSS',
};

export const sampleWithPartialData: ITraining = {
  id: 47506,
  trainigNumber: 'orchestrate firewall coherente',
  startDate: dayjs('2022-10-29'),
  endDate: dayjs('2022-10-29'),
  trainingName: 'Acero Joyería Gris',
  statusName: 'infomediaries Ladrillo Arroyo',
};

export const sampleWithFullData: ITraining = {
  id: 20846,
  trainigNumber: 'SAS B2C groupware',
  startDate: dayjs('2022-10-29'),
  endDate: dayjs('2022-10-29'),
  trainingName: 'Especialista parse',
  statusName: 'Sorprendente strategize Rojo',
};

export const sampleWithNewData: NewTraining = {
  trainigNumber: 'Expandido next-generation solid',
  startDate: dayjs('2022-10-29'),
  endDate: dayjs('2022-10-29'),
  trainingName: 'Técnico Borders',
  statusName: 'Genérico',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
