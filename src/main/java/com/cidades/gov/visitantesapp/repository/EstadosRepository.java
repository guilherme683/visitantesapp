package com.cidades.gov.visitantesapp.repository;

import com.cidades.gov.visitantesapp.domain.Estados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Estados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadosRepository extends JpaRepository<Estados, Long> {

}
