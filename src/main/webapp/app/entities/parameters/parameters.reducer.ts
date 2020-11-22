import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParameters, defaultValue } from 'app/shared/model/parameters.model';

export const ACTION_TYPES = {
  SEARCH_PARAMETERS: 'parameters/SEARCH_PARAMETERS',
  FETCH_PARAMETERS_LIST: 'parameters/FETCH_PARAMETERS_LIST',
  FETCH_PARAMETERS: 'parameters/FETCH_PARAMETERS',
  CREATE_PARAMETERS: 'parameters/CREATE_PARAMETERS',
  UPDATE_PARAMETERS: 'parameters/UPDATE_PARAMETERS',
  DELETE_PARAMETERS: 'parameters/DELETE_PARAMETERS',
  RESET: 'parameters/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParameters>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ParametersState = Readonly<typeof initialState>;

// Reducer

export default (state: ParametersState = initialState, action): ParametersState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAMETERS):
    case REQUEST(ACTION_TYPES.FETCH_PARAMETERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAMETERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAMETERS):
    case REQUEST(ACTION_TYPES.UPDATE_PARAMETERS):
    case REQUEST(ACTION_TYPES.DELETE_PARAMETERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAMETERS):
    case FAILURE(ACTION_TYPES.FETCH_PARAMETERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAMETERS):
    case FAILURE(ACTION_TYPES.CREATE_PARAMETERS):
    case FAILURE(ACTION_TYPES.UPDATE_PARAMETERS):
    case FAILURE(ACTION_TYPES.DELETE_PARAMETERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAMETERS):
    case SUCCESS(ACTION_TYPES.FETCH_PARAMETERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAMETERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAMETERS):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAMETERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAMETERS):
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

const apiUrl = 'api/parameters';
const apiSearchUrl = 'api/_search/parameters';

// Actions

export const getSearchEntities: ICrudSearchAction<IParameters> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAMETERS,
  payload: axios.get<IParameters>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IParameters> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PARAMETERS_LIST,
  payload: axios.get<IParameters>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IParameters> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAMETERS,
    payload: axios.get<IParameters>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IParameters> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAMETERS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IParameters> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAMETERS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParameters> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAMETERS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
