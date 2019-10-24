package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Municipio;
import co.edu.sena.booking.jpa.entities.Pais;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Departamento.class)
public class Departamento_ { 

    public static volatile SingularAttribute<Departamento, Pais> fkPais;
    public static volatile SingularAttribute<Departamento, Integer> depId;
    public static volatile ListAttribute<Departamento, Municipio> municipioList;
    public static volatile SingularAttribute<Departamento, String> depNombre;

}