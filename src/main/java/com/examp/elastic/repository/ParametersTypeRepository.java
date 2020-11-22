package com.examp.elastic.repository;

import com.examp.elastic.domain.ParametersType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParametersType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametersTypeRepository extends JpaRepository<ParametersType, Long> {
}
