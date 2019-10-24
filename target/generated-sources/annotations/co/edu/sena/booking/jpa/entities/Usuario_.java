package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Calificacion;
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
import co.edu.sena.booking.jpa.entities.RolUsuario;
import co.edu.sena.booking.jpa.entities.Tipoidentificacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> usuCorreo;
    public static volatile ListAttribute<Usuario, RolUsuario> rolUsuarioList;
    public static volatile ListAttribute<Usuario, Reserva> reservaList2;
    public static volatile ListAttribute<Usuario, Reserva> reservaList1;
    public static volatile SingularAttribute<Usuario, String> usuNombres;
    public static volatile SingularAttribute<Usuario, String> usuTelefono;
    public static volatile SingularAttribute<Usuario, String> usuAvatar;
    public static volatile SingularAttribute<Usuario, Integer> usuId;
    public static volatile SingularAttribute<Usuario, Tipoidentificacion> fkTipoIdentificacion;
    public static volatile ListAttribute<Usuario, Reserva> reservaList;
    public static volatile SingularAttribute<Usuario, String> usuGenero;
    public static volatile SingularAttribute<Usuario, String> usuIdentificacion;
    public static volatile ListAttribute<Usuario, Calificacion> calificacionList;
    public static volatile ListAttribute<Usuario, ReservaCliente> reservaClienteList;

}