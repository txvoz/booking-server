package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.Lugar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Foto.class)
public class Foto_ { 

    public static volatile SingularAttribute<Foto, String> fotLabel;
    public static volatile SingularAttribute<Foto, String> fotRuta;
    public static volatile SingularAttribute<Foto, String> fotDescripcion;
    public static volatile SingularAttribute<Foto, Integer> fotId;
    public static volatile SingularAttribute<Foto, Alojamiento> fkAlojamiento;
    public static volatile SingularAttribute<Foto, Lugar> fkLugar;

}