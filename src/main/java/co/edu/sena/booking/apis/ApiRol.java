package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.RolJpaController;
import co.edu.sena.booking.jpa.entities.Rol;
import co.edu.sena.booking.utils.JsonTransformer;
import co.edu.sena.booking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import spark.Request;
import spark.Response;

public class ApiRol extends BasicApi implements IApi {

    private static ApiRol instance = null;
    private String path = "/rol";
    private Gson gson = null;
    private RolJpaController rolController = null;

    private ApiRol() {
        rolController = new RolJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiRol singleton() {
        if (instance == null) {
            instance = new ApiRol();
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
            Rol nEntity = gson.fromJson(body, Rol.class);
            rolController.create(nEntity);
            retorno.put("status", 201);
            retorno.put("message", "Registro creado con exito!");
            retorno.put("data", nEntity);
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
            Rol nEntity = gson.fromJson(body, Rol.class);
            Rol oEntity = rolController.findRol(id);
            if (oEntity != null) {
                oEntity.setRolNombre(nEntity.getRolNombre());
                rolController.edit(oEntity);
                rs.status(201);
                retorno.put("status", 201);
                retorno.put("message", "Registro con el id@" + id + " actualizado!");
                retorno.put("data", oEntity);
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
            rolController.destroy(id);
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
        List<Rol> entities = rolController.findRolEntities();
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
            Rol entity = rolController.findRol(id);
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
