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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "municipio", catalog = "db_booking", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"munCodigo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m")
    , @NamedQuery(name = "Municipio.findByMunId", query = "SELECT m FROM Municipio m WHERE m.munId = :munId")
    , @NamedQuery(name = "Municipio.findByMunNombre", query = "SELECT m FROM Municipio m WHERE m.munNombre = :munNombre")
    , @NamedQuery(name = "Municipio.findByMunCodigo", query = "SELECT m FROM Municipio m WHERE m.munCodigo = :munCodigo")})
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "munId", nullable = false)
    @Expose
    private Integer munId;
    @Basic(optional = false)
    @Column(name = "munNombre", nullable = false, length = 45)
    @Expose
    private String munNombre;
    @Basic(optional = false)
    @Column(name = "munCodigo", nullable = false, length = 10)
    @Expose
    private String munCodigo;
    @JoinColumn(name = "fkDepartamento", referencedColumnName = "depId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Departamento fkDepartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkMunicipio", fetch = FetchType.EAGER)
    private List<Lugar> lugarList;

    public Municipio() {
    }

    public Municipio(Integer munId) {
        this.munId = munId;
    }

    public Municipio(Integer munId, String munNombre, String munCodigo) {
        this.munId = munId;
        this.munNombre = munNombre;
        this.munCodigo = munCodigo;
    }

    public Integer getMunId() {
        return munId;
    }

    public void setMunId(Integer munId) {
        this.munId = munId;
    }

    public String getMunNombre() {
        return munNombre;
    }

    public void setMunNombre(String munNombre) {
        this.munNombre = munNombre;
    }

    public String getMunCodigo() {
        return munCodigo;
    }

    public void setMunCodigo(String munCodigo) {
        this.munCodigo = munCodigo;
    }

    public Departamento getFkDepartamento() {
        return fkDepartamento;
    }

    public void setFkDepartamento(Departamento fkDepartamento) {
        this.fkDepartamento = fkDepartamento;
    }

    @XmlTransient
    public List<Lugar> getLugarList() {
        return lugarList;
    }

    public void setLugarList(List<Lugar> lugarList) {
        this.lugarList = lugarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (munId != null ? munId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.munId == null && other.munId != null) || (this.munId != null && !this.munId.equals(other.munId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Municipio[ munId=" + munId + " ]";
    }
    
}
