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
 * @author zt4rm4n
 */
@Entity
@Table(name = "tipolugar", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipolugar.findAll", query = "SELECT t FROM Tipolugar t")
    , @NamedQuery(name = "Tipolugar.findByTluId", query = "SELECT t FROM Tipolugar t WHERE t.tluId = :tluId")
    , @NamedQuery(name = "Tipolugar.findByTluNombre", query = "SELECT t FROM Tipolugar t WHERE t.tluNombre = :tluNombre")})
public class Tipolugar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tluId", nullable = false)
    @Expose
    private Integer tluId;
    @Basic(optional = false)
    @Column(name = "tluNombre", nullable = false, length = 45)
    @Expose
    private String tluNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkTipoLugar", fetch = FetchType.EAGER)
    private List<Lugar> lugarList;

    public Tipolugar() {
    }

    public Tipolugar(Integer tluId) {
        this.tluId = tluId;
    }

    public Tipolugar(Integer tluId, String tluNombre) {
        this.tluId = tluId;
        this.tluNombre = tluNombre;
    }

    public Integer getTluId() {
        return tluId;
    }

    public void setTluId(Integer tluId) {
        this.tluId = tluId;
    }

    public String getTluNombre() {
        return tluNombre;
    }

    public void setTluNombre(String tluNombre) {
        this.tluNombre = tluNombre;
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
        hash += (tluId != null ? tluId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipolugar)) {
            return false;
        }
        Tipolugar other = (Tipolugar) object;
        if ((this.tluId == null && other.tluId != null) || (this.tluId != null && !this.tluId.equals(other.tluId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Tipolugar[ tluId=" + tluId + " ]";
    }
    
}
