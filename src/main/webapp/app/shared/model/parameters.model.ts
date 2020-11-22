export interface IParameters {
  id?: number;
  paramKey?: string;
  paramValue?: string;
  parametersType?: string;
  description?: string;
}

export const defaultValue: Readonly<IParameters> = {};
