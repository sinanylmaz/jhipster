import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITesttable, defaultValue } from 'app/shared/model/testtable.model';

export const ACTION_TYPES = {
  SEARCH_TESTTABLES: 'testtable/SEARCH_TESTTABLES',
  FETCH_TESTTABLE_LIST: 'testtable/FETCH_TESTTABLE_LIST',
  FETCH_TESTTABLE: 'testtable/FETCH_TESTTABLE',
  CREATE_TESTTABLE: 'testtable/CREATE_TESTTABLE',
  UPDATE_TESTTABLE: 'testtable/UPDATE_TESTTABLE',
  DELETE_TESTTABLE: 'testtable/DELETE_TESTTABLE',
  RESET: 'testtable/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITesttable>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TesttableState = Readonly<typeof initialState>;

// Reducer

export default (state: TesttableState = initialState, action): TesttableState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TESTTABLES):
    case REQUEST(ACTION_TYPES.FETCH_TESTTABLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TESTTABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TESTTABLE):
    case REQUEST(ACTION_TYPES.UPDATE_TESTTABLE):
    case REQUEST(ACTION_TYPES.DELETE_TESTTABLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_TESTTABLES):
    case FAILURE(ACTION_TYPES.FETCH_TESTTABLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TESTTABLE):
    case FAILURE(ACTION_TYPES.CREATE_TESTTABLE):
    case FAILURE(ACTION_TYPES.UPDATE_TESTTABLE):
    case FAILURE(ACTION_TYPES.DELETE_TESTTABLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TESTTABLES):
    case SUCCESS(ACTION_TYPES.FETCH_TESTTABLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TESTTABLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TESTTABLE):
    case SUCCESS(ACTION_TYPES.UPDATE_TESTTABLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TESTTABLE):
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

const apiUrl = 'api/testtables';
const apiSearchUrl = 'api/_search/testtables';

// Actions

export const getSearchEntities: ICrudSearchAction<ITesttable> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TESTTABLES,
  payload: axios.get<ITesttable>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ITesttable> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TESTTABLE_LIST,
  payload: axios.get<ITesttable>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITesttable> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TESTTABLE,
    payload: axios.get<ITesttable>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITesttable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TESTTABLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITesttable> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TESTTABLE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITesttable> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TESTTABLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
