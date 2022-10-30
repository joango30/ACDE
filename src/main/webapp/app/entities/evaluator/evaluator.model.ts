import { IUser } from 'app/entities/user/user.model';
import { IDocumentType } from 'app/entities/document-type/document-type.model';

export interface IEvaluator {
  id: number;
  phoneNumber?: string | null;
  email?: string | null;
  address?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  documentType?: Pick<IDocumentType, 'id' | 'documentName'> | null;
}

export type NewEvaluator = Omit<IEvaluator, 'id'> & { id: null };
