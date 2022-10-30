import { IEvaluator, NewEvaluator } from './evaluator.model';

export const sampleWithRequiredData: IEvaluator = {
  id: 71561,
  phoneNumber: 'target Bedfordshire',
  email: 'Patricia.Fernndez45@yahoo.com',
};

export const sampleWithPartialData: IEvaluator = {
  id: 19542,
  phoneNumber: 'orientada',
  email: 'Blanca40@gmail.com',
  address: 'Central',
};

export const sampleWithFullData: IEvaluator = {
  id: 99718,
  phoneNumber: 'Increible Programa Gorro',
  email: 'Ignacio72@gmail.com',
  address: 'Taka',
};

export const sampleWithNewData: NewEvaluator = {
  phoneNumber: 'Reducido Cuentas',
  email: 'Irene.Muiz27@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
