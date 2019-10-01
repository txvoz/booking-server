package co.edu.sena.booking.init;

import co.edu.sena.booking.apis.ApiDepartamento;
import co.edu.sena.booking.apis.ApiReservaCliente;
import co.edu.sena.booking.apis.ApiLugar;
import co.edu.sena.booking.apis.ApiPais;
import co.edu.sena.booking.apis.ApiReserva;
import co.edu.sena.booking.apis.ApiCalificacion;
import co.edu.sena.booking.apis.ApiRol;
import co.edu.sena.booking.apis.ApiRolUsuario;
import co.edu.sena.booking.apis.ApiTipoAlojamiento;
import co.edu.sena.booking.apis.ApiTipoIdentificacion;
import co.edu.sena.booking.apis.ApiUsuario;
import spark.Spark;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {
        //Ruta de archivos estaticos
        Spark.staticFiles.location("/public");
        //*************
        Spark.port(88);
        //*************
        Spark.init();
        //Publicacion de Apis/Servicios
        ApiCalificacion.singleton();
        ApiDepartamento.singleton();
        ApiLugar.singleton();
        ApiPais.singleton();
        ApiReserva.singleton();
        ApiReservaCliente.singleton();
        ApiRol.singleton();
        ApiRolUsuario.singleton();
        ApiTipoAlojamiento.singleton();
        ApiTipoIdentificacion.singleton();
        ApiUsuario.singleton();
    }

}
