package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Tipoalojamiento.class)
public class Tipoalojamiento_ { 

    public static volatile SingularAttribute<Tipoalojamiento, String> talNombre;
    public static volatile SingularAttribute<Tipoalojamiento, Integer> talId;
    public static volatile ListAttribute<Tipoalojamiento, Alojamiento> alojamientoList;

}