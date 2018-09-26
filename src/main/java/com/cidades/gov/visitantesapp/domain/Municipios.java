package com.cidades.gov.visitantesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Municipios.
 */
@Entity
@Table(name = "municipios")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_municipios_ibge")
    private Long codMunicipiosIbge;

    @Column(name = "nome_municipio")
    private String nomeMunicipio;

    @Column(name = "cod_estados_ibge")
    private Long codEstadosIbge;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Estados estados;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodMunicipiosIbge() {
        return codMunicipiosIbge;
    }

    public Municipios codMunicipiosIbge(Long codMunicipiosIbge) {
        this.codMunicipiosIbge = codMunicipiosIbge;
        return this;
    }

    public void setCodMunicipiosIbge(Long codMunicipiosIbge) {
        this.codMunicipiosIbge = codMunicipiosIbge;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public Municipios nomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
        return this;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public Long getCodEstadosIbge() {
        return codEstadosIbge;
    }

    public Municipios codEstadosIbge(Long codEstadosIbge) {
        this.codEstadosIbge = codEstadosIbge;
        return this;
    }

    public void setCodEstadosIbge(Long codEstadosIbge) {
        this.codEstadosIbge = codEstadosIbge;
    }

    public Estados getEstados() {
        return estados;
    }

    public Municipios estados(Estados estados) {
        this.estados = estados;
        return this;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Municipios municipios = (Municipios) o;
        if (municipios.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), municipios.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Municipios{" +
            "id=" + getId() +
            ", codMunicipiosIbge=" + getCodMunicipiosIbge() +
            ", nomeMunicipio='" + getNomeMunicipio() + "'" +
            ", codEstadosIbge=" + getCodEstadosIbge() +
            "}";
    }
}
