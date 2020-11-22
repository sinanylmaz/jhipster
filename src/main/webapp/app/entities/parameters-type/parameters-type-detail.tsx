import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './parameters-type.reducer';
import { IParametersType } from 'app/shared/model/parameters-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParametersTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParametersTypeDetail = (props: IParametersTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { parametersTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elasticExampleApp.parametersType.detail.title">ParametersType</Translate> [<b>{parametersTypeEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="description">
              <Translate contentKey="elasticExampleApp.parametersType.description">Description</Translate>
            </span>
          </dt>
          <dd>{parametersTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/parameters-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/parameters-type/${parametersTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ parametersType }: IRootState) => ({
  parametersTypeEntity: parametersType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParametersTypeDetail);
