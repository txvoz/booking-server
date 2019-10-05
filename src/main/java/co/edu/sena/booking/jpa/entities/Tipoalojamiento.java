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
@Table(name = "tipoalojamiento", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoalojamiento.findAll", query = "SELECT t FROM Tipoalojamiento t")
    , @NamedQuery(name = "Tipoalojamiento.findByTalId", query = "SELECT t FROM Tipoalojamiento t WHERE t.talId = :talId")
    , @NamedQuery(name = "Tipoalojamiento.findByTalNombre", query = "SELECT t FROM Tipoalojamiento t WHERE t.talNombre = :talNombre")})
public class Tipoalojamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "talId", nullable = false)
    @Expose
    private Integer talId;
    @Basic(optional = false)
    @Column(name = "talNombre", nullable = false, length = 45)
    @Expose
    private String talNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTipoAlojamiento", fetch = FetchType.EAGER)
    private List<Alojamiento> alojamientoList;

    public Tipoalojamiento() {
    }

    public Tipoalojamiento(Integer talId) {
        this.talId = talId;
    }

    public Tipoalojamiento(Integer talId, String talNombre) {
        this.talId = talId;
        this.talNombre = talNombre;
    }

    public Integer getTalId() {
        return talId;
    }

    public void setTalId(Integer talId) {
        this.talId = talId;
    }

    public String getTalNombre() {
        return talNombre;
    }

    public void setTalNombre(String talNombre) {
        this.talNombre = talNombre;
    }

    @XmlTransient
    public List<Alojamiento> getAlojamientoList() {
        return alojamientoList;
    }

    public void setAlojamientoList(List<Alojamiento> alojamientoList) {
        this.alojamientoList = alojamientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (talId != null ? talId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoalojamiento)) {
            return false;
        }
        Tipoalojamiento other = (Tipoalojamiento) object;
        if ((this.talId == null && other.talId != null) || (this.talId != null && !this.talId.equals(other.talId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Tipoalojamiento[ talId=" + talId + " ]";
    }
    
}
