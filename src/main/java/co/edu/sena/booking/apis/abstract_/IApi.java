package co.edu.sena.booking.apis.abstract_;

import spark.Request;
import spark.Response;

public interface IApi {
    //Ruta base del api
    public String getPath();
    //Metodo para crear un recurso
    public Object create(Request rq, Response rs);
    //Metodo para actualizar 
    public Object update(Request rq, Response rs);
    //Metodo para eliminar 
    public Object delete(Request rq, Response rs);
    //Metodo para consultar todos los registros 
    public Object getAll(Request rq, Response rs);
    //Metodo para consultar por detalle
    public Object getById(Request rq, Response rs);
}