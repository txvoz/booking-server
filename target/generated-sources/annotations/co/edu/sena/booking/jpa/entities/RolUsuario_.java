package co.edu.sena.booking.jpa.entities;

import co.edu.sena.booking.jpa.entities.Rol;
import co.edu.sena.booking.jpa.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-18T20:54:15")
@StaticMetamodel(RolUsuario.class)
public class RolUsuario_ { 

    public static volatile SingularAttribute<RolUsuario, String> rolEstado;
    public static volatile SingularAttribute<RolUsuario, Rol> fkRol;
    public static volatile SingularAttribute<RolUsuario, Usuario> fkUsuario;
    public static volatile SingularAttribute<RolUsuario, Integer> ruId;

}