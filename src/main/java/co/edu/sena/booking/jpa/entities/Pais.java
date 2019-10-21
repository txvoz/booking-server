/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "pais", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")
    , @NamedQuery(name = "Pais.findByPaiId", query = "SELECT p FROM Pais p WHERE p.paiId = :paiId")
    , @NamedQuery(name = "Pais.findByPaiNombre", query = "SELECT p FROM Pais p WHERE p.paiNombre = :paiNombre")
    , @NamedQuery(name = "Pais.findByPaiCodigo", query = "SELECT p FROM Pais p WHERE p.paiCodigo = :paiCodigo")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "paiId", nullable = false)
    @Expose
    private Integer paiId;
    @Basic(optional = false)
    @Column(name = "paiNombre", nullable = false, length = 45)
    @Expose
    private String paiNombre;
    @Basic(optional = false)
    @Column(name = "paiCodigo", nullable = false, length = 45)
    private String paiCodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkPais", fetch = FetchType.EAGER)
    private List<Departamento> departamentoList;

    public Pais() {
    }

    public Pais(Integer paiId) {
        this.paiId = paiId;
    }

    public Pais(Integer paiId, String paiNombre, String paiCodigo) {
        this.paiId = paiId;
        this.paiNombre = paiNombre;
        this.paiCodigo = paiCodigo;
    }

    public Integer getPaiId() {
        return paiId;
    }

    public void setPaiId(Integer paiId) {
        this.paiId = paiId;
    }

    public String getPaiNombre() {
        return paiNombre;
    }

    public void setPaiNombre(String paiNombre) {
        this.paiNombre = paiNombre;
    }

    public String getPaiCodigo() {
        return paiCodigo;
    }

    public void setPaiCodigo(String paiCodigo) {
        this.paiCodigo = paiCodigo;
    }

    @XmlTransient
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> departamentoList) {
        this.departamentoList = departamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paiId != null ? paiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.paiId == null && other.paiId != null) || (this.paiId != null && !this.paiId.equals(other.paiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Pais[ paiId=" + paiId + " ]";
    }

}
