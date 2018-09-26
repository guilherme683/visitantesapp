package com.cidades.gov.visitantesapp.repository;

import com.cidades.gov.visitantesapp.domain.Regioes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Regioes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegioesRepository extends JpaRepository<Regioes, Long> {

}
