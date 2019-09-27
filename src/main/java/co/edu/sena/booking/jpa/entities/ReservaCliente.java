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
@Table(name = "reserva_cliente", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaCliente.findAll", query = "SELECT r FROM ReservaCliente r")
    , @NamedQuery(name = "ReservaCliente.findByRuId", query = "SELECT r FROM ReservaCliente r WHERE r.ruId = :ruId")})
public class ReservaCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ruId", nullable = false)
    @Expose
    private Integer ruId;
    @JoinColumn(name = "fkReserva", referencedColumnName = "resId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Reserva fkReserva;
    @JoinColumn(name = "fkCliente", referencedColumnName = "usuId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Expose
    private Usuario fkCliente;

    public ReservaCliente() {
    }

    public ReservaCliente(Integer ruId) {
        this.ruId = ruId;
    }

    public Integer getRuId() {
        return ruId;
    }

    public void setRuId(Integer ruId) {
        this.ruId = ruId;
    }

    public Reserva getFkReserva() {
        return fkReserva;
    }

    public void setFkReserva(Reserva fkReserva) {
        this.fkReserva = fkReserva;
    }

    public Usuario getFkCliente() {
        return fkCliente;
    }

    public void setFkCliente(Usuario fkCliente) {
        this.fkCliente = fkCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruId != null ? ruId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaCliente)) {
            return false;
        }
        ReservaCliente other = (ReservaCliente) object;
        if ((this.ruId == null && other.ruId != null) || (this.ruId != null && !this.ruId.equals(other.ruId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.ReservaCliente[ ruId=" + ruId + " ]";
    }
    
}
