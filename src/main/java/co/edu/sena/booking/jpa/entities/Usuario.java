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
@Table(name = "usuario", catalog = "db_booking", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuIdentificacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuId", query = "SELECT u FROM Usuario u WHERE u.usuId = :usuId")
    , @NamedQuery(name = "Usuario.findByUsuIdentificacion", query = "SELECT u FROM Usuario u WHERE u.usuIdentificacion = :usuIdentificacion")
    , @NamedQuery(name = "Usuario.findByUsuNombres", query = "SELECT u FROM Usuario u WHERE u.usuNombres = :usuNombres")
    , @NamedQuery(name = "Usuario.findByUsuGenero", query = "SELECT u FROM Usuario u WHERE u.usuGenero = :usuGenero")
    , @NamedQuery(name = "Usuario.findByUsuCorreo", query = "SELECT u FROM Usuario u WHERE u.usuCorreo = :usuCorreo")
    , @NamedQuery(name = "Usuario.findByUsuTelefono", query = "SELECT u FROM Usuario u WHERE u.usuTelefono = :usuTelefono")
    , @NamedQuery(name = "Usuario.findByUsuAvatar", query = "SELECT u FROM Usuario u WHERE u.usuAvatar = :usuAvatar")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuId", nullable = false)
    @Expose
    private Integer usuId;
    @Basic(optional = false)
    @Column(name = "usuIdentificacion", nullable = false, length = 45)
    @Expose
    private String usuIdentificacion;
    @Basic(optional = false)
    @Column(name = "usuNombres", nullable = false, length = 45)
    @Expose
    private String usuNombres;
    @Basic(optional = false)
    @Column(name = "usuGenero", nullable = false, length = 2)
    @Expose
    private String usuGenero;
    @Column(name = "usuCorreo", length = 45)
    @Expose
    private String usuCorreo;
    @Column(name = "usuTelefono", length = 45)
    @Expose
    private String usuTelefono;
    @Column(name = "usuAvatar", length = 45)
    @Expose
    private String usuAvatar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuario", fetch = FetchType.EAGER)
    private List<Calificacion> calificacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuario", fetch = FetchType.EAGER)
    private List<RolUsuario> rolUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCliente", fetch = FetchType.EAGER)
    private List<ReservaCliente> reservaClienteList;
    @JoinColumn(name = "fkTipoIdentificacion", referencedColumnName = "tidId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Tipoidentificacion fkTipoIdentificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCliente", fetch = FetchType.EAGER)
    private List<Reserva> reservaList;
    @OneToMany(mappedBy = "fkUsuarioChecking", fetch = FetchType.EAGER)
    private List<Reserva> reservaList1;
    @OneToMany(mappedBy = "fkUsuarioCheckout", fetch = FetchType.EAGER)
    private List<Reserva> reservaList2;

    public Usuario() {
    }

    public Usuario(Integer usuId) {
        this.usuId = usuId;
    }

    public Usuario(Integer usuId, String usuIdentificacion, String usuNombres, String usuGenero) {
        this.usuId = usuId;
        this.usuIdentificacion = usuIdentificacion;
        this.usuNombres = usuNombres;
        this.usuGenero = usuGenero;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public String getUsuIdentificacion() {
        return usuIdentificacion;
    }

    public void setUsuIdentificacion(String usuIdentificacion) {
        this.usuIdentificacion = usuIdentificacion;
    }

    public String getUsuNombres() {
        return usuNombres;
    }

    public void setUsuNombres(String usuNombres) {
        this.usuNombres = usuNombres;
    }

    public String getUsuGenero() {
        return usuGenero;
    }

    public void setUsuGenero(String usuGenero) {
        this.usuGenero = usuGenero;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public String getUsuAvatar() {
        return usuAvatar;
    }

    public void setUsuAvatar(String usuAvatar) {
        this.usuAvatar = usuAvatar;
    }

    @XmlTransient
    public List<Calificacion> getCalificacionList() {
        return calificacionList;
    }

    public void setCalificacionList(List<Calificacion> calificacionList) {
        this.calificacionList = calificacionList;
    }

    @XmlTransient
    public List<RolUsuario> getRolUsuarioList() {
        return rolUsuarioList;
    }

    public void setRolUsuarioList(List<RolUsuario> rolUsuarioList) {
        this.rolUsuarioList = rolUsuarioList;
    }

    @XmlTransient
    public List<ReservaCliente> getReservaClienteList() {
        return reservaClienteList;
    }

    public void setReservaClienteList(List<ReservaCliente> reservaClienteList) {
        this.reservaClienteList = reservaClienteList;
    }

    public Tipoidentificacion getFkTipoIdentificacion() {
        return fkTipoIdentificacion;
    }

    public void setFkTipoIdentificacion(Tipoidentificacion fkTipoIdentificacion) {
        this.fkTipoIdentificacion = fkTipoIdentificacion;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @XmlTransient
    public List<Reserva> getReservaList1() {
        return reservaList1;
    }

    public void setReservaList1(List<Reserva> reservaList1) {
        this.reservaList1 = reservaList1;
    }

    @XmlTransient
    public List<Reserva> getReservaList2() {
        return reservaList2;
    }

    public void setReservaList2(List<Reserva> reservaList2) {
        this.reservaList2 = reservaList2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Usuario[ usuId=" + usuId + " ]";
    }
    
}
