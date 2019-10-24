package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
import co.edu.sena.booking.jpa.entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Reserva.class)
public class Reserva_ { 

    public static volatile SingularAttribute<Reserva, String> resEstado;
    public static volatile SingularAttribute<Reserva, Double> resPago;
    public static volatile SingularAttribute<Reserva, Usuario> fkUsuarioCheckout;
    public static volatile SingularAttribute<Reserva, Alojamiento> fkAlojamiento;
    public static volatile SingularAttribute<Reserva, String> resFechaChecking;
    public static volatile SingularAttribute<Reserva, Integer> resId;
    public static volatile SingularAttribute<Reserva, Date> resFechaRegistro;
    public static volatile SingularAttribute<Reserva, Usuario> fkUsuarioChecking;
    public static volatile SingularAttribute<Reserva, Usuario> fkCliente;
    public static volatile SingularAttribute<Reserva, Date> resFechaLlegada;
    public static volatile SingularAttribute<Reserva, Date> resFechaSalida;
    public static volatile ListAttribute<Reserva, ReservaCliente> reservaClienteList;
    public static volatile SingularAttribute<Reserva, String> resFechaCheckout;

}