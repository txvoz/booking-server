package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Calificacion;
import co.edu.sena.booking.jpa.entities.Foto;
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.entities.Tipoalojamiento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Alojamiento.class)
public class Alojamiento_ { 

    public static volatile SingularAttribute<Alojamiento, String> aloPrecio;
    public static volatile SingularAttribute<Alojamiento, Tipoalojamiento> fkTipoAlojamiento;
    public static volatile ListAttribute<Alojamiento, Reserva> reservaList;
    public static volatile SingularAttribute<Alojamiento, Integer> aloId;
    public static volatile SingularAttribute<Alojamiento, String> aloDescripcion;
    public static volatile SingularAttribute<Alojamiento, Integer> aloCapacidad;
    public static volatile ListAttribute<Alojamiento, Calificacion> calificacionList;
    public static volatile ListAttribute<Alojamiento, Foto> fotoList;
    public static volatile SingularAttribute<Alojamiento, String> aloCodigo;
    public static volatile SingularAttribute<Alojamiento, Lugar> fkLugar;

}