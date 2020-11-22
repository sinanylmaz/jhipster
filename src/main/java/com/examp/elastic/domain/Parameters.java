package com.examp.elastic.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Parameters.
 */
@Entity
@Table(name = "parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "parameters")
public class Parameters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_key")
    private String paramKey;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "parameters_type")
    private String parametersType;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public Parameters paramKey(String paramKey) {
        this.paramKey = paramKey;
        return this;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public Parameters paramValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParametersType() {
        return parametersType;
    }

    public Parameters parametersType(String parametersType) {
        this.parametersType = parametersType;
        return this;
    }

    public void setParametersType(String parametersType) {
        this.parametersType = parametersType;
    }

    public String getDescription() {
        return description;
    }

    public Parameters description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parameters)) {
            return false;
        }
        return id != null && id.equals(((Parameters) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parameters{" +
            "id=" + getId() +
            ", paramKey='" + getParamKey() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            ", parametersType='" + getParametersType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
