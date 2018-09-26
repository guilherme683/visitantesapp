package com.cidades.gov.visitantesapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Municipios.class)
public abstract class Municipios_ {

	public static volatile SingularAttribute<Municipios, Long> codEstadosIbge;
	public static volatile SingularAttribute<Municipios, Long> id;
	public static volatile SingularAttribute<Municipios, String> nomeMunicipio;
	public static volatile SingularAttribute<Municipios, Long> codMunicipiosIbge;
	public static volatile SingularAttribute<Municipios, Estados> estados;

}

