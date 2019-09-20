/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.CalificacionJpaController;
import co.edu.sena.booking.jpa.entities.Calificacion;
import co.edu.sena.booking.utils.JsonTransformer;
import co.edu.sena.booking.utils.Utils;
import com.google.gson.Gson;
import java.util.Hashtable;
import java.util.List;
import spark.Request;
import spark.Response;

/**
 *
 * @author Administrador
 */
public class ApiCalificacion extends BasicApi implements IApi {
    
    private static ApiCalificacion instance = null;
    private String path = "/calificacion";
    private Gson gson = null;
    private CalificacionJpaController calificacionController = null;
    
    private ApiCalificacion() {
        calificacionController = new CalificacionJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiCalificacion singleton() {
        if (instance == null) {
            instance = new ApiCalificacion();
        }
        return instance;
    }

    @Override
    public String getPath() {
       return path;
    }

    @Override
    public Object create(Request rq, Response rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object update(Request rq, Response rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object delete(Request rq, Response rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAll(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        List<Calificacion> entities = calificacionController.findCalificacionEntities();
        if (entities.size() > 0) {
            retorno.put("status", 200);
            retorno.put("message", "Datos encontrados con exito!");
            retorno.put("data", entities);
        } else {
            rs.status(404);
            retorno.put("status", 404);
            retorno.put("message", "Datos no encontrados!");
        }
        return retorno;
    }

    @Override
    public Object getById(Request rq, Response rs) {
         Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            Calificacion entity = calificacionController.findCalificacion(id);
            if (entity != null) {
                retorno.put("status", 200);
                retorno.put("message", "Registro con el id@" + id + " encontrado!");
                retorno.put("data", entity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id@" + id + " no exite!");
            }
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }
    
}
