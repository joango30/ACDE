import { IDocumentType, NewDocumentType } from './document-type.model';

export const sampleWithRequiredData: IDocumentType = {
  id: 98901,
  initials: 'Granada Pi',
  documentName: 'Marroquinería',
};

export const sampleWithPartialData: IDocumentType = {
  id: 62313,
  initials: 'Travesía c',
  documentName: 'Respuesta',
};

export const sampleWithFullData: IDocumentType = {
  id: 41015,
  initials: 'IB',
  documentName: 'Navarra harness',
};

export const sampleWithNewData: NewDocumentType = {
  initials: 'Centraliza',
  documentName: 'Centralizado Rústico',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
