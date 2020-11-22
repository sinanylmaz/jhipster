package com.examp.elastic.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Testtable.
 */
@Entity
@Table(name = "parameters")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "testtable")
public class Testtable implements Serializable {

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

    @Column(name = "desctest")
    private String desctest;

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

    public Testtable paramKey(String paramKey) {
        this.paramKey = paramKey;
        return this;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public Testtable paramValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParametersType() {
        return parametersType;
    }

    public Testtable parametersType(String parametersType) {
        this.parametersType = parametersType;
        return this;
    }

    public void setParametersType(String parametersType) {
        this.parametersType = parametersType;
    }

    public String getDescription() {
        return description;
    }

    public Testtable description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesctest() {
        return desctest;
    }

    public Testtable desctest(String desctest) {
        this.desctest = desctest;
        return this;
    }

    public void setDesctest(String desctest) {
        this.desctest = desctest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Testtable)) {
            return false;
        }
        return id != null && id.equals(((Testtable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Testtable{" +
            "id=" + getId() +
            ", paramKey='" + getParamKey() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            ", parametersType='" + getParametersType() + "'" +
            ", description='" + getDescription() + "'" +
            ", desctest='" + getDesctest() + "'" +
            "}";
    }
}
