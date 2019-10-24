package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Departamento;
import co.edu.sena.booking.jpa.entities.Lugar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Municipio.class)
public class Municipio_ { 

    public static volatile SingularAttribute<Municipio, Departamento> fkDepartamento;
    public static volatile SingularAttribute<Municipio, String> munCodigo;
    public static volatile SingularAttribute<Municipio, Integer> munId;
    public static volatile ListAttribute<Municipio, Lugar> lugarList;
    public static volatile SingularAttribute<Municipio, String> munNombre;

}