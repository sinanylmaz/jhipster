import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Parameters from './parameters';
import ParametersDetail from './parameters-detail';
import ParametersUpdate from './parameters-update';
import ParametersDeleteDialog from './parameters-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParametersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParametersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParametersDetail} />
      <ErrorBoundaryRoute path={match.url} component={Parameters} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParametersDeleteDialog} />
  </>
);

export default Routes;
