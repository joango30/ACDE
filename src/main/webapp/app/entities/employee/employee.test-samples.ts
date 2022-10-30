import { State } from 'app/entities/enumerations/state.model';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 7813,
  status: State['TRAINED'],
  charge: 'GB scale Mexican',
  email: 'Rubn14@hotmail.com',
  phoneNumber: 'Administrador ADP',
};

export const sampleWithPartialData: IEmployee = {
  id: 97350,
  status: State['TRAINED'],
  charge: 'Lado Gris',
  email: 'Jernimo.Durn93@hotmail.com',
  phoneNumber: 'Nacional Analista Cambridgeshire',
};

export const sampleWithFullData: IEmployee = {
  id: 35453,
  status: State['TRAINED'],
  charge: 'Metal Fundamental Loan',
  email: 'Gonzalo.Noriega44@gmail.com',
  phoneNumber: 'Granito Adelante',
};

export const sampleWithNewData: NewEmployee = {
  status: State['TRAINED'],
  charge: 'Nauru Plástico Videojuegos',
  email: 'Gilberto_Valladares3@yahoo.com',
  phoneNumber: 'Lesotho Bhutanese Violeta',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
