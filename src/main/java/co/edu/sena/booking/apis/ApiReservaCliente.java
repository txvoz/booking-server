package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.ReservaClienteJpaController;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
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
import static spark.Spark.init;

public class ApiReservaCliente extends BasicApi implements IApi{
    
    private static ApiReservaCliente instance = null;
    private String path = "/reservaCliente";
    private Gson gson = null;    
    private ReservaClienteJpaController reservaClienteController = null;
    
    private ApiReservaCliente() {
        reservaClienteController = new ReservaClienteJpaController(Utils.getEM());
        gson = new Gson();//JsonTransformer.singleton().getGson(); //datos del body se envian como json, no requiere parseo / si requiere parseo del servidor al body
        init();
    }

    public static ApiReservaCliente singleton() {
        if (instance == null) {
            instance = new ApiReservaCliente();
        }
        return instance;
    }

    public String getPath() {
        return path;
    }

    public Object create(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            String body = rq.body();
            ReservaCliente nEntity = gson.fromJson(body, ReservaCliente.class);
            reservaClienteController.create(nEntity);
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
    
    public Object update(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            String body = rq.body();
            ReservaCliente nEntity = gson.fromJson(body, ReservaCliente.class);
            ReservaCliente oEntity = reservaClienteController.findReservaCliente(id);
            if (oEntity != null) {
                oEntity.setFkCliente(nEntity.getFkCliente());
                oEntity.setFkReserva(nEntity.getFkReserva());
                reservaClienteController.edit(oEntity);
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
    
    public Object delete(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            reservaClienteController.destroy(id);
            retorno.put("status", 200);
            retorno.put("message", "Registro eliminado con exito!");
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }  
    
    public Object getAll(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        List<ReservaCliente> entities = reservaClienteController.findReservaClienteEntities();
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
    
    public Object getById(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            ReservaCliente entity = reservaClienteController.findReservaCliente(id);
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

