import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IParametersType } from 'app/shared/model/parameters-type.model';
import { getEntities as getParametersTypes } from 'app/entities/parameters-type/parameters-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './parameters.reducer';
import { IParameters } from 'app/shared/model/parameters.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParametersUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ParametersUpdate = (props: IParametersUpdateProps) => {
  const [parametersTypeId, setParametersTypeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { parametersEntity, parametersTypes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/parameters');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getParametersTypes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...parametersEntity,
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
          <h2 id="elasticExampleApp.parameters.home.createOrEditLabel">
            <Translate contentKey="elasticExampleApp.parameters.home.createOrEditLabel">Create or edit a Parameters</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : parametersEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="parameters-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="parameters-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="paramKeyLabel" for="parameters-paramKey">
                  <Translate contentKey="elasticExampleApp.parameters.paramKey">Param Key</Translate>
                </Label>
                <AvField id="parameters-paramKey" type="text" name="paramKey" validate={{}} />
              </AvGroup>
              <AvGroup>
                <Label id="paramValueLabel" for="parameters-paramValue">
                  <Translate contentKey="elasticExampleApp.parameters.paramValue">Param Value</Translate>
                </Label>
                <AvField id="parameters-paramValue" type="text" name="paramValue" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="parameters-description">
                  <Translate contentKey="elasticExampleApp.parameters.description">Description</Translate>
                </Label>
                <AvField id="parameters-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="desctestLabel" for="parameters-desctest">
                  <Translate contentKey="elasticExampleApp.parameters.desctest">Desctest</Translate>
                </Label>
                <AvField id="parameters-desctest" type="text" name="desctest" />
              </AvGroup>
              <AvGroup>
                <Label for="parameters-parametersType">
                  <Translate contentKey="elasticExampleApp.parameters.parametersType">Parameters Type</Translate>
                </Label>
                <AvInput id="parameters-parametersType" type="select" className="form-control" name="parametersType.id">
                  <option value="" key="0" />
                  {parametersTypes
                    ? parametersTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.description}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/parameters" replace color="info">
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
  parametersTypes: storeState.parametersType.entities,
  parametersEntity: storeState.parameters.entity,
  loading: storeState.parameters.loading,
  updating: storeState.parameters.updating,
  updateSuccess: storeState.parameters.updateSuccess,
});

const mapDispatchToProps = {
  getParametersTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ParametersUpdate);
