package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Calificacion.class)
public class Calificacion_ { 

    public static volatile SingularAttribute<Calificacion, String> calValoracion;
    public static volatile SingularAttribute<Calificacion, Usuario> fkUsuario;
    public static volatile SingularAttribute<Calificacion, Alojamiento> fkAlojamiento;
    public static volatile SingularAttribute<Calificacion, Integer> calId;
    public static volatile SingularAttribute<Calificacion, String> calComentario;

}