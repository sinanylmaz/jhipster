import { IParametersType } from 'app/shared/model/parameters-type.model';

export interface IParameters {
  id?: number;
  paramKey?: string;
  paramValue?: string;
  description?: string;
  desctest?: string;
  parametersType?: IParametersType;
}

export const defaultValue: Readonly<IParameters> = {};
