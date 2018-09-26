package com.cidades.gov.visitantesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Estados.
 */
@Entity
@Table(name = "estados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_estados_ibge")
    private Long codEstadosIbge;

    @Column(name = "nome_estado")
    private String nomeEstado;

    @Column(name = "sigla_estado")
    private String siglaEstado;

    @Column(name = "cod_regioes")
    private Long codRegioes;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Regioes regioes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodEstadosIbge() {
        return codEstadosIbge;
    }

    public Estados codEstadosIbge(Long codEstadosIbge) {
        this.codEstadosIbge = codEstadosIbge;
        return this;
    }

    public void setCodEstadosIbge(Long codEstadosIbge) {
        this.codEstadosIbge = codEstadosIbge;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public Estados nomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
        return this;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public Estados siglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
        return this;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public Long getCodRegioes() {
        return codRegioes;
    }

    public Estados codRegioes(Long codRegioes) {
        this.codRegioes = codRegioes;
        return this;
    }

    public void setCodRegioes(Long codRegioes) {
        this.codRegioes = codRegioes;
    }

    public Regioes getRegioes() {
        return regioes;
    }

    public Estados regioes(Regioes regioes) {
        this.regioes = regioes;
        return this;
    }

    public void setRegioes(Regioes regioes) {
        this.regioes = regioes;
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
        Estados estados = (Estados) o;
        if (estados.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estados.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estados{" +
            "id=" + getId() +
            ", codEstadosIbge=" + getCodEstadosIbge() +
            ", nomeEstado='" + getNomeEstado() + "'" +
            ", siglaEstado='" + getSiglaEstado() + "'" +
            ", codRegioes=" + getCodRegioes() +
            "}";
    }
}
