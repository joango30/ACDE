export interface IDocumentType {
  id: number;
  initials?: string | null;
  documentName?: string | null;
}

export type NewDocumentType = Omit<IDocumentType, 'id'> & { id: null };
