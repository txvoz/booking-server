/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "reserva", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByResId", query = "SELECT r FROM Reserva r WHERE r.resId = :resId")
    , @NamedQuery(name = "Reserva.findByResFechaRegistro", query = "SELECT r FROM Reserva r WHERE r.resFechaRegistro = :resFechaRegistro")
    , @NamedQuery(name = "Reserva.findByResFechaLlegada", query = "SELECT r FROM Reserva r WHERE r.resFechaLlegada = :resFechaLlegada")
    , @NamedQuery(name = "Reserva.findByResFechaSalida", query = "SELECT r FROM Reserva r WHERE r.resFechaSalida = :resFechaSalida")
    , @NamedQuery(name = "Reserva.findByResFechaChecking", query = "SELECT r FROM Reserva r WHERE r.resFechaChecking = :resFechaChecking")
    , @NamedQuery(name = "Reserva.findByResFechaCheckout", query = "SELECT r FROM Reserva r WHERE r.resFechaCheckout = :resFechaCheckout")
    , @NamedQuery(name = "Reserva.findByResEstado", query = "SELECT r FROM Reserva r WHERE r.resEstado = :resEstado")
    , @NamedQuery(name = "Reserva.findByResPago", query = "SELECT r FROM Reserva r WHERE r.resPago = :resPago")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)    
    @Column(name = "resId", nullable = false)
    @Expose
    private Integer resId;
    @Basic(optional = false)
    @Column(name = "resFechaRegistro", nullable = false)
    @Temporal(TemporalType.DATE)
    @Expose
    private Date resFechaRegistro;
    @Basic(optional = false)
    @Column(name = "resFechaLlegada", nullable = false)
    @Temporal(TemporalType.DATE)
    @Expose
    private Date resFechaLlegada;
    @Basic(optional = false)
    @Column(name = "resFechaSalida", nullable = false)
    @Temporal(TemporalType.DATE)
    @Expose
    private Date resFechaSalida;
    @Column(name = "resFechaChecking", length = 45)
    @Expose
    private String resFechaChecking;
    @Column(name = "resFechaCheckout", length = 45)
    @Expose
    private String resFechaCheckout;
    @Basic(optional = false)
    @Column(name = "resEstado", nullable = false, length = 2)
    @Expose
    private String resEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "resPago", precision = 22)
    @Expose
    private Double resPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkReserva", fetch = FetchType.EAGER)
    @Expose
    private List<ReservaCliente> reservaClienteList;
    @JoinColumn(name = "fkAlojamiento", referencedColumnName = "aloId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Alojamiento fkAlojamiento;
    @JoinColumn(name = "fkCliente", referencedColumnName = "usuId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Usuario fkCliente;
    @JoinColumn(name = "fkUsuarioChecking", referencedColumnName = "usuId")
    @ManyToOne(fetch = FetchType.EAGER)
    @Expose
    private Usuario fkUsuarioChecking;
    @JoinColumn(name = "fkUsuarioCheckout", referencedColumnName = "usuId")
    @ManyToOne(fetch = FetchType.EAGER)
    @Expose
    private Usuario fkUsuarioCheckout;

    public Reserva() {
    }

    public Reserva(Integer resId) {
        this.resId = resId;
    }

    public Reserva(Integer resId, Date resFechaRegistro, Date resFechaLlegada, Date resFechaSalida, String resEstado) {
        this.resId = resId;
        this.resFechaRegistro = resFechaRegistro;
        this.resFechaLlegada = resFechaLlegada;
        this.resFechaSalida = resFechaSalida;
        this.resEstado = resEstado;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Date getResFechaRegistro() {
        return resFechaRegistro;
    }

    public void setResFechaRegistro(Date resFechaRegistro) {
        this.resFechaRegistro = resFechaRegistro;
    }

    public Date getResFechaLlegada() {
        return resFechaLlegada;
    }

    public void setResFechaLlegada(Date resFechaLlegada) {
        this.resFechaLlegada = resFechaLlegada;
    }

    public Date getResFechaSalida() {
        return resFechaSalida;
    }

    public void setResFechaSalida(Date resFechaSalida) {
        this.resFechaSalida = resFechaSalida;
    }

    public String getResFechaChecking() {
        return resFechaChecking;
    }

    public void setResFechaChecking(String resFechaChecking) {
        this.resFechaChecking = resFechaChecking;
    }

    public String getResFechaCheckout() {
        return resFechaCheckout;
    }

    public void setResFechaCheckout(String resFechaCheckout) {
        this.resFechaCheckout = resFechaCheckout;
    }

    public String getResEstado() {
        return resEstado;
    }

    public void setResEstado(String resEstado) {
        this.resEstado = resEstado;
    }

    public Double getResPago() {
        return resPago;
    }

    public void setResPago(Double resPago) {
        this.resPago = resPago;
    }

    @XmlTransient
    public List<ReservaCliente> getReservaClienteList() {
        return reservaClienteList;
    }

    public void setReservaClienteList(List<ReservaCliente> reservaClienteList) {
        this.reservaClienteList = reservaClienteList;
    }

    public Alojamiento getFkAlojamiento() {
        return fkAlojamiento;
    }

    public void setFkAlojamiento(Alojamiento fkAlojamiento) {
        this.fkAlojamiento = fkAlojamiento;
    }

    public Usuario getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Usuario fkCliente) {
        this.fkCliente = fkCliente;
    }

    public Usuario getFkUsuarioChecking() {
        return fkUsuarioChecking;
    }

    public void setFkUsuarioChecking(Usuario fkUsuarioChecking) {
        this.fkUsuarioChecking = fkUsuarioChecking;
    }

    public Usuario getFkUsuarioCheckout() {
        return fkUsuarioCheckout;
    }

    public void setFkUsuarioCheckout(Usuario fkUsuarioCheckout) {
        this.fkUsuarioCheckout = fkUsuarioCheckout;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resId != null ? resId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.resId == null && other.resId != null) || (this.resId != null && !this.resId.equals(other.resId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.Reserva[ resId=" + resId + " ]";
    }
    
}
