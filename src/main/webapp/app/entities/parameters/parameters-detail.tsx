import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './parameters.reducer';
import { IParameters } from 'app/shared/model/parameters.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParametersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParametersDetail = (props: IParametersDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { parametersEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elasticExampleApp.parameters.detail.title">Parameters</Translate> [<b>{parametersEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="paramKey">
              <Translate contentKey="elasticExampleApp.parameters.paramKey">Param Key</Translate>
            </span>
          </dt>
          <dd>{parametersEntity.paramKey}</dd>
          <dt>
            <span id="paramValue">
              <Translate contentKey="elasticExampleApp.parameters.paramValue">Param Value</Translate>
            </span>
          </dt>
          <dd>{parametersEntity.paramValue}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="elasticExampleApp.parameters.description">Description</Translate>
            </span>
          </dt>
          <dd>{parametersEntity.description}</dd>
          <dt>
            <span id="desctest">
              <Translate contentKey="elasticExampleApp.parameters.desctest">Desctest</Translate>
            </span>
          </dt>
          <dd>{parametersEntity.desctest}</dd>
          <dt>
            <Translate contentKey="elasticExampleApp.parameters.parametersType">Parameters Type</Translate>
          </dt>
          <dd>{parametersEntity.parametersType ? parametersEntity.parametersType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/parameters" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parameters/${parametersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ parameters }: IRootState) => ({
  parametersEntity: parameters.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParametersDetail);
