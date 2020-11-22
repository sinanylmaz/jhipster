import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParametersType, defaultValue } from 'app/shared/model/parameters-type.model';

export const ACTION_TYPES = {
  SEARCH_PARAMETERSTYPES: 'parametersType/SEARCH_PARAMETERSTYPES',
  FETCH_PARAMETERSTYPE_LIST: 'parametersType/FETCH_PARAMETERSTYPE_LIST',
  FETCH_PARAMETERSTYPE: 'parametersType/FETCH_PARAMETERSTYPE',
  CREATE_PARAMETERSTYPE: 'parametersType/CREATE_PARAMETERSTYPE',
  UPDATE_PARAMETERSTYPE: 'parametersType/UPDATE_PARAMETERSTYPE',
  DELETE_PARAMETERSTYPE: 'parametersType/DELETE_PARAMETERSTYPE',
  RESET: 'parametersType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParametersType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ParametersTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ParametersTypeState = initialState, action): ParametersTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAMETERSTYPES):
    case REQUEST(ACTION_TYPES.FETCH_PARAMETERSTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAMETERSTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAMETERSTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_PARAMETERSTYPE):
    case REQUEST(ACTION_TYPES.DELETE_PARAMETERSTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAMETERSTYPES):
    case FAILURE(ACTION_TYPES.FETCH_PARAMETERSTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAMETERSTYPE):
    case FAILURE(ACTION_TYPES.CREATE_PARAMETERSTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_PARAMETERSTYPE):
    case FAILURE(ACTION_TYPES.DELETE_PARAMETERSTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAMETERSTYPES):
    case SUCCESS(ACTION_TYPES.FETCH_PARAMETERSTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAMETERSTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAMETERSTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAMETERSTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAMETERSTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/parameters-types';
const apiSearchUrl = 'api/_search/parameters-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IParametersType> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAMETERSTYPES,
  payload: axios.get<IParametersType>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IParametersType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARAMETERSTYPE_LIST,
  payload: axios.get<IParametersType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IParametersType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAMETERSTYPE,
    payload: axios.get<IParametersType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IParametersType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAMETERSTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IParametersType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAMETERSTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParametersType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAMETERSTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
