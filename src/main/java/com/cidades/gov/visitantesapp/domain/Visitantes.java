package com.cidades.gov.visitantesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.cidades.gov.visitantesapp.domain.enumeration.Status;

/**
 * A Visitantes.
 */
@Entity
@Table(name = "visitantes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visitantes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_visita")
    private Instant dataVisita;

    @Column(name = "nome_visitante")
    private String nomeVisitante;

    @Column(name = "jhi_local")
    private String local;

    @Column(name = "acompanhado_por")
    private String acompanhadoPor;

    @Column(name = "estado")
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_atendimento")
    private Status statusAtendimento;

    @Column(name = "cadastrado_por")
    private String cadastradoPor;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Acessores acessores;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Municipios municipios;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Cargo cargo;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Partido partido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataVisita() {
        return dataVisita;
    }

    public Visitantes dataVisita(Instant dataVisita) {
        this.dataVisita = dataVisita;
        return this;
    }

    public void setDataVisita(Instant dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public Visitantes nomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
        return this;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public String getLocal() {
        return local;
    }

    public Visitantes local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAcompanhadoPor() {
        return acompanhadoPor;
    }

    public Visitantes acompanhadoPor(String acompanhadoPor) {
        this.acompanhadoPor = acompanhadoPor;
        return this;
    }

    public void setAcompanhadoPor(String acompanhadoPor) {
        this.acompanhadoPor = acompanhadoPor;
    }

    public String getEstado() {
        return estado;
    }

    public Visitantes estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Status getStatusAtendimento() {
        return statusAtendimento;
    }

    public Visitantes statusAtendimento(Status statusAtendimento) {
        this.statusAtendimento = statusAtendimento;
        return this;
    }

    public void setStatusAtendimento(Status statusAtendimento) {
        this.statusAtendimento = statusAtendimento;
    }

    public String getCadastradoPor() {
        return cadastradoPor;
    }

    public Visitantes cadastradoPor(String cadastradoPor) {
        this.cadastradoPor = cadastradoPor;
        return this;
    }

    public void setCadastradoPor(String cadastradoPor) {
        this.cadastradoPor = cadastradoPor;
    }

    public String getObservacao() {
        return observacao;
    }

    public Visitantes observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Acessores getAcessores() {
        return acessores;
    }

    public Visitantes acessores(Acessores acessores) {
        this.acessores = acessores;
        return this;
    }

    public void setAcessores(Acessores acessores) {
        this.acessores = acessores;
    }

    public Municipios getMunicipios() {
        return municipios;
    }

    public Visitantes municipios(Municipios municipios) {
        this.municipios = municipios;
        return this;
    }

    public void setMunicipios(Municipios municipios) {
        this.municipios = municipios;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Visitantes cargo(Cargo cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Partido getPartido() {
        return partido;
    }

    public Visitantes partido(Partido partido) {
        this.partido = partido;
        return this;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
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
        Visitantes visitantes = (Visitantes) o;
        if (visitantes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitantes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visitantes{" +
            "id=" + getId() +
            ", dataVisita='" + getDataVisita() + "'" +
            ", nomeVisitante='" + getNomeVisitante() + "'" +
            ", local='" + getLocal() + "'" +
            ", acompanhadoPor='" + getAcompanhadoPor() + "'" +
            ", estado='" + getEstado() + "'" +
            ", statusAtendimento='" + getStatusAtendimento() + "'" +
            ", cadastradoPor='" + getCadastradoPor() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
