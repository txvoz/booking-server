/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.PaisJpaController;
import co.edu.sena.booking.jpa.entities.Pais;
import co.edu.sena.booking.utils.JsonTransformer;
import co.edu.sena.booking.utils.Utils;
import com.google.gson.Gson;
import java.util.Hashtable;
import java.util.List;
import spark.Request;
import spark.Response;

/**
 *
 * @author PR. SOFTWARE
 */
public class ApiPais extends BasicApi implements IApi {

    private static ApiPais instance = null;
    private String path = "/pais";
    private Gson gson = null;
    private PaisJpaController paisController = null;

    private ApiPais() {
        paisController = new PaisJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiPais singleton() {
        if (instance == null) {
            instance = new ApiPais();
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
        List<Pais> entities = paisController.findPaisEntities();
        if (entities.size() > 0) {
            retorno.put("status", 200);
            retorno.put("message", "Pais encontrados con exito!");
            retorno.put("data", entities);
        } else {
            rs.status(404);
            retorno.put("status", 404);
            retorno.put("message", "Pais no encontrados!");
        }
        return retorno;

    }

    @Override
    public Object getById(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            Pais entity = paisController.findPais(id);
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
