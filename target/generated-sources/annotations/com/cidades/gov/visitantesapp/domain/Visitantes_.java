package com.cidades.gov.visitantesapp.domain;

import com.cidades.gov.visitantesapp.domain.enumeration.Status;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Visitantes.class)
public abstract class Visitantes_ {

	public static volatile SingularAttribute<Visitantes, Status> statusAtendimento;
	public static volatile SingularAttribute<Visitantes, Instant> dataVisita;
	public static volatile SingularAttribute<Visitantes, Municipios> municipios;
	public static volatile SingularAttribute<Visitantes, String> estado;
	public static volatile SingularAttribute<Visitantes, String> observacao;
	public static volatile SingularAttribute<Visitantes, Acessores> acessores;
	public static volatile SingularAttribute<Visitantes, String> local;
	public static volatile SingularAttribute<Visitantes, String> cadastradoPor;
	public static volatile SingularAttribute<Visitantes, Long> id;
	public static volatile SingularAttribute<Visitantes, Cargo> cargo;
	public static volatile SingularAttribute<Visitantes, String> acompanhadoPor;
	public static volatile SingularAttribute<Visitantes, Partido> partido;
	public static volatile SingularAttribute<Visitantes, String> nomeVisitante;

}

