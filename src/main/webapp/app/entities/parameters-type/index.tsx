import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ParametersType from './parameters-type';
import ParametersTypeDetail from './parameters-type-detail';
import ParametersTypeUpdate from './parameters-type-update';
import ParametersTypeDeleteDialog from './parameters-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParametersTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParametersTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParametersTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ParametersType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParametersTypeDeleteDialog} />
  </>
);

export default Routes;
