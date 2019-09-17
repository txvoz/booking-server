/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.entities;

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
@Table(name = "rol_usuario", catalog = "db_booking", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolUsuario.findAll", query = "SELECT r FROM RolUsuario r")
    , @NamedQuery(name = "RolUsuario.findByRuId", query = "SELECT r FROM RolUsuario r WHERE r.ruId = :ruId")
    , @NamedQuery(name = "RolUsuario.findByRolEstado", query = "SELECT r FROM RolUsuario r WHERE r.rolEstado = :rolEstado")})
public class RolUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ruId", nullable = false)
    private Integer ruId;
    @Basic(optional = false)
    @Column(name = "rolEstado", nullable = false, length = 2)
    private String rolEstado;
    @JoinColumn(name = "fkRol", referencedColumnName = "rolId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Rol fkRol;
    @JoinColumn(name = "fkUsuario", referencedColumnName = "usuId", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario fkUsuario;

    public RolUsuario() {
    }

    public RolUsuario(Integer ruId) {
        this.ruId = ruId;
    }

    public RolUsuario(Integer ruId, String rolEstado) {
        this.ruId = ruId;
        this.rolEstado = rolEstado;
    }

    public Integer getRuId() {
        return ruId;
    }

    public void setRuId(Integer ruId) {
        this.ruId = ruId;
    }

    public String getRolEstado() {
        return rolEstado;
    }

    public void setRolEstado(String rolEstado) {
        this.rolEstado = rolEstado;
    }

    public Rol getFkRol() {
        return fkRol;
    }

    public void setFkRol(Rol fkRol) {
        this.fkRol = fkRol;
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
        hash += (ruId != null ? ruId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolUsuario)) {
            return false;
        }
        RolUsuario other = (RolUsuario) object;
        if ((this.ruId == null && other.ruId != null) || (this.ruId != null && !this.ruId.equals(other.ruId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.sena.booking.apis.entities.RolUsuario[ ruId=" + ruId + " ]";
    }
    
}
