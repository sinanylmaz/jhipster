package com.examp.elastic.repository;

import com.examp.elastic.domain.Testtable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Testtable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TesttableRepository extends JpaRepository<Testtable, Long> {
}
