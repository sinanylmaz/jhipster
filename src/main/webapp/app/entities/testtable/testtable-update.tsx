import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './testtable.reducer';
import { ITesttable } from 'app/shared/model/testtable.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITesttableUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TesttableUpdate = (props: ITesttableUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { testtableEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/testtable');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...testtableEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="elasticExampleApp.testtable.home.createOrEditLabel">
            <Translate contentKey="elasticExampleApp.testtable.home.createOrEditLabel">Create or edit a Testtable</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : testtableEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="testtable-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="testtable-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="paramKeyLabel" for="testtable-paramKey">
                  <Translate contentKey="elasticExampleApp.testtable.paramKey">Param Key</Translate>
                </Label>
                <AvField id="testtable-paramKey" type="text" name="paramKey" />
              </AvGroup>
              <AvGroup>
                <Label id="paramValueLabel" for="testtable-paramValue">
                  <Translate contentKey="elasticExampleApp.testtable.paramValue">Param Value</Translate>
                </Label>
                <AvField id="testtable-paramValue" type="text" name="paramValue" />
              </AvGroup>
              <AvGroup>
                <Label id="parametersTypeLabel" for="testtable-parametersType">
                  <Translate contentKey="elasticExampleApp.testtable.parametersType">Parameters Type</Translate>
                </Label>
                <AvField id="testtable-parametersType" type="text" name="parametersType" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="testtable-description">
                  <Translate contentKey="elasticExampleApp.testtable.description">Description</Translate>
                </Label>
                <AvField id="testtable-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="desctestLabel" for="testtable-desctest">
                  <Translate contentKey="elasticExampleApp.testtable.desctest">Desctest</Translate>
                </Label>
                <AvField id="testtable-desctest" type="text" name="desctest" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/testtable" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  testtableEntity: storeState.testtable.entity,
  loading: storeState.testtable.loading,
  updating: storeState.testtable.updating,
  updateSuccess: storeState.testtable.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TesttableUpdate);
