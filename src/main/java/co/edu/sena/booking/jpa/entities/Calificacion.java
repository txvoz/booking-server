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
@Table(name = "calificacion", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")
    , @NamedQuery(name = "Calificacion.findByCalId", query = "SELECT c FROM Calificacion c WHERE c.calId = :calId")
    , @NamedQuery(name = "Calificacion.findByCalValoracion", query = "SELECT c FROM Calificacion c WHERE c.calValoracion = :calValoracion")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calId", nullable = false)
    @Expose
    private Integer calId;
    @Basic(optional = false)
    @Column(name = "calValoracion", nullable = false, length = 2)
    @Expose
    private String calValoracion;
    @Lob
    @Column(name = "calComentario", length = 16777215)
    @Expose
    private String calComentario;
    @JoinColumn(name = "fkAlojamiento", referencedColumnName = "aloId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Alojamiento fkAlojamiento;
    @JoinColumn(name = "fkUsuario", referencedColumnName = "usuId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario fkUsuario;

    public Calificacion() {
    }

    public Calificacion(Integer calId) {
        this.calId = calId;
    }

    public Calificacion(Integer calId, String calValoracion) {
        this.calId = calId;
        this.calValoracion = calValoracion;
    }

    public Integer getCalId() {
        return calId;
    }

    public void setCalId(Integer calId) {
        this.calId = calId;
    }

    public String getCalValoracion() {
        return calValoracion;
    }

    public void setCalValoracion(String calValoracion) {
        this.calValoracion = calValoracion;
    }

    public String getCalComentario() {
        return calComentario;
    }

    public void setCalComentario(String calComentario) {
        this.calComentario = calComentario;
    }

    public Alojamiento getFkAlojamiento() {
        return fkAlojamiento;
    }

    public void setFkAlojamiento(Alojamiento fkAlojamiento) {
        this.fkAlojamiento = fkAlojamiento;
    }

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calId != null ? calId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.calId == null && other.calId != null) || (this.calId != null && !this.calId.equals(other.calId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Calificacion[ calId=" + calId + " ]";
    }
    
}
