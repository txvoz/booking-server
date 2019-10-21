/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "foto", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Foto.findAll", query = "SELECT f FROM Foto f")
    , @NamedQuery(name = "Foto.findByFotId", query = "SELECT f FROM Foto f WHERE f.fotId = :fotId")
    , @NamedQuery(name = "Foto.findByFotRuta", query = "SELECT f FROM Foto f WHERE f.fotRuta = :fotRuta")
    , @NamedQuery(name = "Foto.findByFotLabel", query = "SELECT f FROM Foto f WHERE f.fotLabel = :fotLabel")})
public class Foto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fotId", nullable = false)
    @Expose
    private Integer fotId;
    @Basic(optional = false)
    @Column(name = "fotRuta", nullable = false, length = 45)
    @Expose
    private String fotRuta;
    @Basic(optional = false)
    @Column(name = "fotLabel", nullable = false, length = 45)
    @Expose
    private String fotLabel;
    @Lob
    @Column(name = "fotDescripcion", length = 16777215)
    @Expose
    private String fotDescripcion;
    @JoinColumn(name = "fkAlojamiento", referencedColumnName = "aloId")
    @ManyToOne(fetch = FetchType.EAGER)
    @Expose
    private Alojamiento fkAlojamiento;
    @JoinColumn(name = "fkLugar", referencedColumnName = "lugId")
    @ManyToOne(fetch = FetchType.EAGER)
    @Expose
    private Lugar fkLugar;

    public Foto() {
    }

    public Foto(Integer fotId) {
        this.fotId = fotId;
    }

    public Foto(Integer fotId, String fotRuta, String fotLabel) {
        this.fotId = fotId;
        this.fotRuta = fotRuta;
        this.fotLabel = fotLabel;
    }

    public Integer getFotId() {
        return fotId;
    }

    public void setFotId(Integer fotId) {
        this.fotId = fotId;
    }

    public String getFotRuta() {
        return fotRuta;
    }

    public void setFotRuta(String fotRuta) {
        this.fotRuta = fotRuta;
    }

    public String getFotLabel() {
        return fotLabel;
    }

    public void setFotLabel(String fotLabel) {
        this.fotLabel = fotLabel;
    }

    public String getFotDescripcion() {
        return fotDescripcion;
    }

    public void setFotDescripcion(String fotDescripcion) {
        this.fotDescripcion = fotDescripcion;
    }

    public Alojamiento getFkAlojamiento() {
        return fkAlojamiento;
    }

    public void setFkAlojamiento(Alojamiento fkAlojamiento) {
        this.fkAlojamiento = fkAlojamiento;
    }

    public Lugar getFkLugar() {
        return fkLugar;
    }

    public void setFkLugar(Lugar fkLugar) {
        this.fkLugar = fkLugar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fotId != null ? fotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foto)) {
            return false;
        }
        Foto other = (Foto) object;
        if ((this.fotId == null && other.fotId != null) || (this.fotId != null && !this.fotId.equals(other.fotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Foto[ fotId=" + fotId + " ]";
    }
    
}
