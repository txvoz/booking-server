/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.TipolugarJpaController;
import co.edu.sena.booking.jpa.entities.Tipolugar;
import co.edu.sena.booking.utils.JsonTransformer;
import co.edu.sena.booking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.PersistenceException;
import spark.Request;
import spark.Response;

/**
 *
 * @author Andrés Felipe Mera Tróchez
 */
public class ApiTipoLugar extends BasicApi implements IApi {

    private static ApiTipoLugar instance = null;
    private String path = "/tipolugar";
    private Gson gson = null;
    private TipolugarJpaController tipolugarController = null;

    private ApiTipoLugar() {
        tipolugarController = new TipolugarJpaController(Utils.getEM());
//        gson = JsonTransformer.singleton().getGson();
        gson = new Gson();
        init();
    }

    public static ApiTipoLugar singleton() {
        if (instance == null) {
            instance = new ApiTipoLugar();
        }
        return instance;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object create(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            String body = rq.body();
            Tipolugar newEntity = gson.fromJson(body, Tipolugar.class);
            tipolugarController.create(newEntity);
            retorno.put("status", 201);
            retorno.put("message", "Registro creado con exito!");
            retorno.put("data", newEntity);
        } catch (JsonSyntaxException | PersistenceException e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }

    @Override
    public Object update(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            String body = rq.body();
            Tipolugar newEntity = gson.fromJson(body, Tipolugar.class);
            Tipolugar oldEntity = tipolugarController.findTipolugar(id);
            if (oldEntity != null) {
                oldEntity.setTluId(newEntity.getTluId());
                oldEntity.setTluNombre(newEntity.getTluNombre());

                tipolugarController.edit(oldEntity);
                rs.status(201);
                retorno.put("status", 201);
                retorno.put("message", "Registro con el id@" + id + " actualizado!");
                retorno.put("data", oldEntity);
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

    @Override
    public Object delete(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            tipolugarController.destroy(id);
            retorno.put("status", 200);
            retorno.put("message", "Registro eliminado con exito!");
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }

    @Override
    public Object getAll(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        List<Tipolugar> entities = tipolugarController.findTipolugarEntities();
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
            Tipolugar entity = tipolugarController.findTipolugar(id);
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
