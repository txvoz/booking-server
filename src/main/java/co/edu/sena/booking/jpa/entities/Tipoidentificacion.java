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
@Table(name = "tipoidentificacion", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoidentificacion.findAll", query = "SELECT t FROM Tipoidentificacion t")
    , @NamedQuery(name = "Tipoidentificacion.findByTidId", query = "SELECT t FROM Tipoidentificacion t WHERE t.tidId = :tidId")
    , @NamedQuery(name = "Tipoidentificacion.findByTipSigla", query = "SELECT t FROM Tipoidentificacion t WHERE t.tipSigla = :tipSigla")
    , @NamedQuery(name = "Tipoidentificacion.findByTipNombre", query = "SELECT t FROM Tipoidentificacion t WHERE t.tipNombre = :tipNombre")})
public class Tipoidentificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tidId", nullable = false)
    @Expose
    private Integer tidId;
    @Basic(optional = false)
    @Column(name = "tipSigla", nullable = false, length = 45)
     @Expose
    private String tipSigla;
    @Basic(optional = false)
    @Column(name = "tipNombre", nullable = false, length = 45)
     @Expose
    private String tipNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTipoIdentificacion", fetch = FetchType.EAGER)
    private List<Usuario> usuarioList;

    public Tipoidentificacion() {
    }

    public Tipoidentificacion(Integer tidId) {
        this.tidId = tidId;
    }

    public Tipoidentificacion(Integer tidId, String tipSigla, String tipNombre) {
        this.tidId = tidId;
        this.tipSigla = tipSigla;
        this.tipNombre = tipNombre;
    }

    public Integer getTidId() {
        return tidId;
    }

    public void setTidId(Integer tidId) {
        this.tidId = tidId;
    }

    public String getTipSigla() {
        return tipSigla;
    }

    public void setTipSigla(String tipSigla) {
        this.tipSigla = tipSigla;
    }

    public String getTipNombre() {
        return tipNombre;
    }

    public void setTipNombre(String tipNombre) {
        this.tipNombre = tipNombre;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tidId != null ? tidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoidentificacion)) {
            return false;
        }
        Tipoidentificacion other = (Tipoidentificacion) object;
        if ((this.tidId == null && other.tidId != null) || (this.tidId != null && !this.tidId.equals(other.tidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Tipoidentificacion[ tidId=" + tidId + " ]";
    }
    
}
