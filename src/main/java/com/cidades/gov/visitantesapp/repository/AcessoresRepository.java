package com.cidades.gov.visitantesapp.repository;

import com.cidades.gov.visitantesapp.domain.Acessores;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Acessores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcessoresRepository extends JpaRepository<Acessores, Long> {

}
