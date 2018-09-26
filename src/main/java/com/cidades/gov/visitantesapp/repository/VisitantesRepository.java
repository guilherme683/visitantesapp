package com.cidades.gov.visitantesapp.repository;

import com.cidades.gov.visitantesapp.domain.Visitantes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Visitantes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitantesRepository extends JpaRepository<Visitantes, Long> {

}
