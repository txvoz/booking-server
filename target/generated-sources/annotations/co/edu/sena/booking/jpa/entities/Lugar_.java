package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.Foto;
import co.edu.sena.booking.jpa.entities.Municipio;
import co.edu.sena.booking.jpa.entities.Tipolugar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Lugar.class)
public class Lugar_ { 

    public static volatile SingularAttribute<Lugar, Integer> lugId;
    public static volatile SingularAttribute<Lugar, String> lugCorreo;
    public static volatile SingularAttribute<Lugar, String> lugNombre;
    public static volatile SingularAttribute<Lugar, String> lugLatitud;
    public static volatile SingularAttribute<Lugar, String> lugTelefono;
    public static volatile SingularAttribute<Lugar, String> lugLongitud;
    public static volatile SingularAttribute<Lugar, Municipio> fkMunicipio;
    public static volatile SingularAttribute<Lugar, String> lugDescripcion;
    public static volatile SingularAttribute<Lugar, String> lugDireccion;
    public static volatile SingularAttribute<Lugar, Tipolugar> fkTipoLugar;
    public static volatile ListAttribute<Lugar, Foto> fotoList;
    public static volatile ListAttribute<Lugar, Alojamiento> alojamientoList;

}