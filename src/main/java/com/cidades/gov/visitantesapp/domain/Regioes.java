package com.cidades.gov.visitantesapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Regioes.
 */
@Entity
@Table(name = "regioes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Regioes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cod_regioes_ibge")
    private Long codRegioesIbge;

    @Column(name = "nome_regiao")
    private String nomeRegiao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodRegioesIbge() {
        return codRegioesIbge;
    }

    public Regioes codRegioesIbge(Long codRegioesIbge) {
        this.codRegioesIbge = codRegioesIbge;
        return this;
    }

    public void setCodRegioesIbge(Long codRegioesIbge) {
        this.codRegioesIbge = codRegioesIbge;
    }

    public String getNomeRegiao() {
        return nomeRegiao;
    }

    public Regioes nomeRegiao(String nomeRegiao) {
        this.nomeRegiao = nomeRegiao;
        return this;
    }

    public void setNomeRegiao(String nomeRegiao) {
        this.nomeRegiao = nomeRegiao;
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
        Regioes regioes = (Regioes) o;
        if (regioes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regioes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regioes{" +
            "id=" + getId() +
            ", codRegioesIbge=" + getCodRegioesIbge() +
            ", nomeRegiao='" + getNomeRegiao() + "'" +
            "}";
    }
}
