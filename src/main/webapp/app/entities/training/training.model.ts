import dayjs from 'dayjs/esm';
import { IEvaluator } from 'app/entities/evaluator/evaluator.model';

export interface ITraining {
  id: number;
  trainigNumber?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  trainingName?: string | null;
  statusName?: string | null;
  evaluator?: Pick<IEvaluator, 'id'> | null;
}

export type NewTraining = Omit<ITraining, 'id'> & { id: null };
