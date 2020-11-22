import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './testtable.reducer';
import { ITesttable } from 'app/shared/model/testtable.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITesttableDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TesttableDetail = (props: ITesttableDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { testtableEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elasticExampleApp.testtable.detail.title">Testtable</Translate> [<b>{testtableEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="paramKey">
              <Translate contentKey="elasticExampleApp.testtable.paramKey">Param Key</Translate>
            </span>
          </dt>
          <dd>{testtableEntity.paramKey}</dd>
          <dt>
            <span id="paramValue">
              <Translate contentKey="elasticExampleApp.testtable.paramValue">Param Value</Translate>
            </span>
          </dt>
          <dd>{testtableEntity.paramValue}</dd>
          <dt>
            <span id="parametersType">
              <Translate contentKey="elasticExampleApp.testtable.parametersType">Parameters Type</Translate>
            </span>
          </dt>
          <dd>{testtableEntity.parametersType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="elasticExampleApp.testtable.description">Description</Translate>
            </span>
          </dt>
          <dd>{testtableEntity.description}</dd>
          <dt>
            <span id="desctest">
              <Translate contentKey="elasticExampleApp.testtable.desctest">Desctest</Translate>
            </span>
          </dt>
          <dd>{testtableEntity.desctest}</dd>
        </dl>
        <Button tag={Link} to="/testtable" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/testtable/${testtableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ testtable }: IRootState) => ({
  testtableEntity: testtable.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TesttableDetail);
