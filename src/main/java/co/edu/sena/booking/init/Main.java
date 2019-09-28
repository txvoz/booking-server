package co.edu.sena.booking.init;

import co.edu.sena.booking.apis.ApiReservaCliente;
import co.edu.sena.booking.apis.ApiLugar;
import co.edu.sena.booking.apis.ApiReserva;
import co.edu.sena.booking.apis.ApiRol;
import co.edu.sena.booking.apis.ApiTipoIdentificacion;
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
        ApiRol.singleton();
        ApiReservaCliente.singleton();
        ApiLugar.singleton();
        ApiReserva.singleton();
        ApiTipoIdentificacion.singleton();
        
    }

}
