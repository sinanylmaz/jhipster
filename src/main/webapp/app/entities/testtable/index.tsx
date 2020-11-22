import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Testtable from './testtable';
import TesttableDetail from './testtable-detail';
import TesttableUpdate from './testtable-update';
import TesttableDeleteDialog from './testtable-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TesttableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TesttableUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TesttableDetail} />
      <ErrorBoundaryRoute path={match.url} component={Testtable} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TesttableDeleteDialog} />
  </>
);

export default Routes;
