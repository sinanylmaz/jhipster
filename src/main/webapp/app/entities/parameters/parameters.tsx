import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './parameters.reducer';
import { IParameters } from 'app/shared/model/parameters.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParametersProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Parameters = (props: IParametersProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { parametersList, match, loading } = props;
  return (
    <div>
      <h2 id="parameters-heading">
        <Translate contentKey="elasticExampleApp.parameters.home.title">Parameters</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="elasticExampleApp.parameters.home.createLabel">Create new Parameters</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elasticExampleApp.parameters.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {parametersList && parametersList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="elasticExampleApp.parameters.paramKey">Param Key</Translate>
                </th>
                <th>
                  <Translate contentKey="elasticExampleApp.parameters.paramValue">Param Value</Translate>
                </th>
                <th>
                  <Translate contentKey="elasticExampleApp.parameters.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="elasticExampleApp.parameters.desctest">Desctest</Translate>
                </th>
                <th>
                  <Translate contentKey="elasticExampleApp.parameters.parametersType">Parameters Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parametersList.map((parameters, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${parameters.id}`} color="link" size="sm">
                      {parameters.id}
                    </Button>
                  </td>
                  <td>{parameters.paramKey}</td>
                  <td>{parameters.paramValue}</td>
                  <td>{parameters.description}</td>
                  <td>{parameters.desctest}</td>
                  <td>
                    {parameters.parametersType ? (
                      <Link to={`parameters-type/${parameters.parametersType.id}`}>{parameters.parametersType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${parameters.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${parameters.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${parameters.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="elasticExampleApp.parameters.home.notFound">No Parameters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ parameters }: IRootState) => ({
  parametersList: parameters.entities,
  loading: parameters.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Parameters);
