/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.LugarJpaController;
import co.edu.sena.booking.jpa.entities.Lugar;
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
public class ApiLugar extends BasicApi implements IApi {

    private static ApiLugar instance = null;
    private String path = "/lugar";
    private Gson gson = null;
    private LugarJpaController lugarController = null;

    /**
     * Constructor privado de la clase
     */
    private ApiLugar() {
        lugarController = new LugarJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
//        gson = new Gson();
        init();
    }
    /**
     * Metodo statico para generar el patro singleton en el api.
     * @return de tipo ApiLugar la instancia de la clase.
     */
    public static ApiLugar singleton() {
        if (instance == null) {
            instance = new ApiLugar();
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
            Lugar newEntity = gson.fromJson(body, Lugar.class);
            lugarController.create(newEntity);
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
            Lugar newEntity = gson.fromJson(body, Lugar.class);
            Lugar oldEntity = lugarController.findLugar(id);
            if (oldEntity != null) {
                oldEntity.setLugNombre(newEntity.getLugNombre());
                oldEntity.setLugCorreo(newEntity.getLugCorreo());
                oldEntity.setLugDescripcion(newEntity.getLugDescripcion());
                oldEntity.setLugDireccion(newEntity.getLugDireccion());
                oldEntity.setLugLatitud(newEntity.getLugLatitud());
                oldEntity.setLugLongitud(newEntity.getLugLongitud());
                oldEntity.setLugTelefono(newEntity.getLugTelefono());
                oldEntity.setFkMunicipio(newEntity.getFkMunicipio());
                oldEntity.setFkTipoLugar(newEntity.getFkTipoLugar());

                lugarController.edit(oldEntity);
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
            lugarController.destroy(id);
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
        List<Lugar> entities = lugarController.findLugarEntities();
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
            Lugar entity = lugarController.findLugar(id);
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
