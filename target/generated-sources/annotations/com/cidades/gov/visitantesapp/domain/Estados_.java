package com.cidades.gov.visitantesapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estados.class)
public abstract class Estados_ {

	public static volatile SingularAttribute<Estados, Long> codEstadosIbge;
	public static volatile SingularAttribute<Estados, String> siglaEstado;
	public static volatile SingularAttribute<Estados, Regioes> regioes;
	public static volatile SingularAttribute<Estados, String> nomeEstado;
	public static volatile SingularAttribute<Estados, Long> id;
	public static volatile SingularAttribute<Estados, Long> codRegioes;

}

