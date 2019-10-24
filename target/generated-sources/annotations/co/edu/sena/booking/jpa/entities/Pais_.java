package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Departamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Pais.class)
public class Pais_ { 

    public static volatile SingularAttribute<Pais, String> paiCodigo;
    public static volatile ListAttribute<Pais, Departamento> departamentoList;
    public static volatile SingularAttribute<Pais, Integer> paiId;
    public static volatile SingularAttribute<Pais, String> paiNombre;

}