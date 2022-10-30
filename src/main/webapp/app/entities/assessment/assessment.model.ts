import dayjs from 'dayjs/esm';
import { IEvaluator } from 'app/entities/evaluator/evaluator.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IAssessment {
  id: number;
  assessmentNumber?: number | null;
  assessmentType?: string | null;
  dateassessment?: dayjs.Dayjs | null;
  assessmentTotal?: number | null;
  evaluator?: Pick<IEvaluator, 'id'> | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewAssessment = Omit<IAssessment, 'id'> & { id: null };
