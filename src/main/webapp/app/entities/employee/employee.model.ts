import { IUser } from 'app/entities/user/user.model';
import { ITraining } from 'app/entities/training/training.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IEmployee {
  id: number;
  status?: State | null;
  charge?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  training?: Pick<ITraining, 'id'> | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
