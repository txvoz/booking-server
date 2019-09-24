/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.DepartamentoJpaController;
import co.edu.sena.booking.jpa.entities.Departamento;
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
public class ApiDepartamento extends BasicApi implements IApi {

    private static ApiDepartamento instance = null;
    private String path = "/departamento";
    private Gson gson = null;
    private DepartamentoJpaController departamentoController = null;

    private ApiDepartamento() {
        departamentoController = new DepartamentoJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiDepartamento singleton() {
        if (instance == null) {
            instance = new ApiDepartamento();
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
        List<Departamento> entities = departamentoController.findDepartamentoEntities();
        if (entities.size() > 0) {
            retorno.put("status", 200);
            retorno.put("message", "departamento encontrados con exito!");
            retorno.put("data", entities);
        } else {
            rs.status(404);
            retorno.put("status", 404);
            retorno.put("message", "departamento no encontrados!");
        }
        return retorno;
    }

    @Override
    public Object getById(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            Departamento entity = departamentoController.findDepartamento(id);
            if (entity != null) {
                retorno.put("status", 200);
                retorno.put("message", "Departamento con el id@" + id + " encontrado!");
                retorno.put("data", entity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Departamento con el id@" + id + " no exite!");
            }
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;

    }

}
