import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Parameters from './parameters';
import Testtable from './testtable';
import ParametersType from './parameters-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}parameters`} component={Parameters} />
      <ErrorBoundaryRoute path={`${match.url}testtable`} component={Testtable} />
      <ErrorBoundaryRoute path={`${match.url}parameters-type`} component={ParametersType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
