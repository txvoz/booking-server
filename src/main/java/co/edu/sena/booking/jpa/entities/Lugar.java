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
@Table(name = "lugar", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lugar.findAll", query = "SELECT l FROM Lugar l")
    , @NamedQuery(name = "Lugar.findByLugId", query = "SELECT l FROM Lugar l WHERE l.lugId = :lugId")
    , @NamedQuery(name = "Lugar.findByLugNombre", query = "SELECT l FROM Lugar l WHERE l.lugNombre = :lugNombre")
    , @NamedQuery(name = "Lugar.findByLugDireccion", query = "SELECT l FROM Lugar l WHERE l.lugDireccion = :lugDireccion")
    , @NamedQuery(name = "Lugar.findByLugTelefono", query = "SELECT l FROM Lugar l WHERE l.lugTelefono = :lugTelefono")
    , @NamedQuery(name = "Lugar.findByLugCorreo", query = "SELECT l FROM Lugar l WHERE l.lugCorreo = :lugCorreo")
    , @NamedQuery(name = "Lugar.findByLugLatitud", query = "SELECT l FROM Lugar l WHERE l.lugLatitud = :lugLatitud")
    , @NamedQuery(name = "Lugar.findByLugLongitud", query = "SELECT l FROM Lugar l WHERE l.lugLongitud = :lugLongitud")})
public class Lugar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lugId", nullable = false)
    @Expose
    private Integer lugId;
    @Basic(optional = false)
    @Column(name = "lugNombre", nullable = false, length = 45)
    @Expose
    private String lugNombre;
    @Basic(optional = false)
    @Column(name = "lugDireccion", nullable = false, length = 45)
    @Expose
    private String lugDireccion;
    @Basic(optional = false)
    @Column(name = "lugTelefono", nullable = false, length = 45)
    @Expose
    private String lugTelefono;
    @Basic(optional = false)
    @Column(name = "lugCorreo", nullable = false, length = 45)
    @Expose
    private String lugCorreo;
    @Basic(optional = false)
    @Column(name = "lugLatitud", nullable = false, length = 45)
    @Expose
    private String lugLatitud;
    @Basic(optional = false)
    @Column(name = "lugLongitud", nullable = false, length = 45)
    @Expose
    private String lugLongitud;
    @Basic(optional = false)
    @Lob
    @Column(name = "lugDescripcion", nullable = false, length = 16777215)
    @Expose
    private String lugDescripcion;
    @JoinColumn(name = "fkMunicipio", referencedColumnName = "munId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Municipio fkMunicipio;
    @JoinColumn(name = "fkTipoLugar", referencedColumnName = "tluId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Tipolugar fkTipoLugar;
    @OneToMany(mappedBy = "fkLugar", fetch = FetchType.EAGER)
    private List<Foto> fotoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkLugar", fetch = FetchType.EAGER)
    private List<Alojamiento> alojamientoList;

    public Lugar() {
    }

    public Lugar(Integer lugId) {
        this.lugId = lugId;
    }

    public Lugar(Integer lugId, String lugNombre, String lugDireccion, String lugTelefono, String lugCorreo, String lugLatitud, String lugLongitud, String lugDescripcion) {
        this.lugId = lugId;
        this.lugNombre = lugNombre;
        this.lugDireccion = lugDireccion;
        this.lugTelefono = lugTelefono;
        this.lugCorreo = lugCorreo;
        this.lugLatitud = lugLatitud;
        this.lugLongitud = lugLongitud;
        this.lugDescripcion = lugDescripcion;
    }

    public Integer getLugId() {
        return lugId;
    }

    public void setLugId(Integer lugId) {
        this.lugId = lugId;
    }

    public String getLugNombre() {
        return lugNombre;
    }

    public void setLugNombre(String lugNombre) {
        this.lugNombre = lugNombre;
    }

    public String getLugDireccion() {
        return lugDireccion;
    }

    public void setLugDireccion(String lugDireccion) {
        this.lugDireccion = lugDireccion;
    }

    public String getLugTelefono() {
        return lugTelefono;
    }

    public void setLugTelefono(String lugTelefono) {
        this.lugTelefono = lugTelefono;
    }

    public String getLugCorreo() {
        return lugCorreo;
    }

    public void setLugCorreo(String lugCorreo) {
        this.lugCorreo = lugCorreo;
    }

    public String getLugLatitud() {
        return lugLatitud;
    }

    public void setLugLatitud(String lugLatitud) {
        this.lugLatitud = lugLatitud;
    }

    public String getLugLongitud() {
        return lugLongitud;
    }

    public void setLugLongitud(String lugLongitud) {
        this.lugLongitud = lugLongitud;
    }

    public String getLugDescripcion() {
        return lugDescripcion;
    }

    public void setLugDescripcion(String lugDescripcion) {
        this.lugDescripcion = lugDescripcion;
    }

    public Municipio getFkMunicipio() {
        return fkMunicipio;
    }

    public void setFkMunicipio(Municipio fkMunicipio) {
        this.fkMunicipio = fkMunicipio;
    }

    public Tipolugar getFkTipoLugar() {
        return fkTipoLugar;
    }

    public void setFkTipoLugar(Tipolugar fkTipoLugar) {
        this.fkTipoLugar = fkTipoLugar;
    }

    @XmlTransient
    public List<Foto> getFotoList() {
        return fotoList;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
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
        hash += (lugId != null ? lugId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugar)) {
            return false;
        }
        Lugar other = (Lugar) object;
        if ((this.lugId == null && other.lugId != null) || (this.lugId != null && !this.lugId.equals(other.lugId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Lugar[ lugId=" + lugId + " ]";
    }
    
}
