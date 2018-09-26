package com.cidades.gov.visitantesapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Partido.
 */
@Entity
@Table(name = "partido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome_partido")
    private String nomePartido;

    @Column(name = "sigla_partido")
    private String siglaPartido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePartido() {
        return nomePartido;
    }

    public Partido nomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
        return this;
    }

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public Partido siglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
        return this;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
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
        Partido partido = (Partido) o;
        if (partido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partido{" +
            "id=" + getId() +
            ", nomePartido='" + getNomePartido() + "'" +
            ", siglaPartido='" + getSiglaPartido() + "'" +
            "}";
    }
}
