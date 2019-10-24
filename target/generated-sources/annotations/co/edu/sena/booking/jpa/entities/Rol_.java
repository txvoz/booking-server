package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.RolUsuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Integer> rolId;
    public static volatile ListAttribute<Rol, RolUsuario> rolUsuarioList;
    public static volatile SingularAttribute<Rol, String> rolNombre;

}