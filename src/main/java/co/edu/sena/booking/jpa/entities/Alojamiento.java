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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "alojamiento", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alojamiento.findAll", query = "SELECT a FROM Alojamiento a")
    , @NamedQuery(name = "Alojamiento.findByAloId", query = "SELECT a FROM Alojamiento a WHERE a.aloId = :aloId")
    , @NamedQuery(name = "Alojamiento.findByAloCodigo", query = "SELECT a FROM Alojamiento a WHERE a.aloCodigo = :aloCodigo")
    , @NamedQuery(name = "Alojamiento.findByAloCapacidad", query = "SELECT a FROM Alojamiento a WHERE a.aloCapacidad = :aloCapacidad")
    , @NamedQuery(name = "Alojamiento.findByAloPrecio", query = "SELECT a FROM Alojamiento a WHERE a.aloPrecio = :aloPrecio")})
public class Alojamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aloId", nullable = false)
    @Expose
    private Integer aloId;
    @Basic(optional = false)
    @Column(name = "aloCodigo", nullable = false, length = 11)
    @Expose
    private String aloCodigo;
    @Basic(optional = false)
    @Column(name = "aloCapacidad", nullable = false)
    @Expose
    private int aloCapacidad;
    @Basic(optional = false)
    @Column(name = "aloPrecio", nullable = false, length = 45)
    @Expose
    private String aloPrecio;
    @Basic(optional = false)
    @Lob
    @Column(name = "aloDescripcion", nullable = false, length = 16777215)
    @Expose
    private String aloDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAlojamiento", fetch = FetchType.EAGER)
    @Expose
    private List<Calificacion> calificacionList;
    @OneToMany(mappedBy = "fkAlojamiento", fetch = FetchType.EAGER)
    @Expose
    private List<Foto> fotoList;
    @JoinColumn(name = "fkLugar", referencedColumnName = "lugId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Lugar fkLugar;
    @JoinColumn(name = "fkTipoAlojamiento", referencedColumnName = "talId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Tipoalojamiento fkTipoAlojamiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAlojamiento", fetch = FetchType.EAGER)
    @Expose
    private List<Reserva> reservaList;

    public Alojamiento() {
    }

    public Alojamiento(Integer aloId) {
        this.aloId = aloId;
    }

    public Alojamiento(Integer aloId, String aloCodigo, int aloCapacidad, String aloPrecio, String aloDescripcion) {
        this.aloId = aloId;
        this.aloCodigo = aloCodigo;
        this.aloCapacidad = aloCapacidad;
        this.aloPrecio = aloPrecio;
        this.aloDescripcion = aloDescripcion;
    }

    public Integer getAloId() {
        return aloId;
    }

    public void setAloId(Integer aloId) {
        this.aloId = aloId;
    }

    public String getAloCodigo() {
        return aloCodigo;
    }

    public void setAloCodigo(String aloCodigo) {
        this.aloCodigo = aloCodigo;
    }

    public int getAloCapacidad() {
        return aloCapacidad;
    }

    public void setAloCapacidad(int aloCapacidad) {
        this.aloCapacidad = aloCapacidad;
    }

    public String getAloPrecio() {
        return aloPrecio;
    }

    public void setAloPrecio(String aloPrecio) {
        this.aloPrecio = aloPrecio;
    }

    public String getAloDescripcion() {
        return aloDescripcion;
    }

    public void setAloDescripcion(String aloDescripcion) {
        this.aloDescripcion = aloDescripcion;
    }

    @XmlTransient
    public List<Calificacion> getCalificacionList() {
        return calificacionList;
    }

    public void setCalificacionList(List<Calificacion> calificacionList) {
        this.calificacionList = calificacionList;
    }

    @XmlTransient
    public List<Foto> getFotoList() {
        return fotoList;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
    }

    public Lugar getFkLugar() {
        return fkLugar;
    }

    public void setFkLugar(Lugar fkLugar) {
        this.fkLugar = fkLugar;
    }

    public Tipoalojamiento getFkTipoAlojamiento() {
        return fkTipoAlojamiento;
    }

    public void setFkTipoAlojamiento(Tipoalojamiento fkTipoAlojamiento) {
        this.fkTipoAlojamiento = fkTipoAlojamiento;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aloId != null ? aloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alojamiento)) {
            return false;
        }
        Alojamiento other = (Alojamiento) object;
        if ((this.aloId == null && other.aloId != null) || (this.aloId != null && !this.aloId.equals(other.aloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Alojamiento[ aloId=" + aloId + " ]";
    }
    
}
