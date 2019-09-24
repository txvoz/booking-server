package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.TipoidentificacionJpaController;
import co.edu.sena.booking.jpa.entities.Tipoidentificacion;
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



/**
 *
 * @author robert
 */
public class ApiTipoIdentificacion extends BasicApi implements IApi{

    private static ApiTipoIdentificacion instance = null;
    private String path = "/tipid";
    private Gson gson = null;
    private TipoidentificacionJpaController tipoidentificacionController = null;
    
    
    private ApiTipoIdentificacion() {
        tipoidentificacionController = new TipoidentificacionJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiTipoIdentificacion singleton() {
        if (instance == null) {
            instance = new ApiTipoIdentificacion();
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
            Tipoidentificacion nEntity = gson.fromJson(body, Tipoidentificacion.class);
            tipoidentificacionController.create(nEntity);
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
            int tidId = Integer.parseInt(rq.params("id"));
            String body = rq.body();
            Tipoidentificacion nEntity = gson.fromJson(body, Tipoidentificacion.class);
            Tipoidentificacion oEntity = tipoidentificacionController.findTipoidentificacion(tidId);
              
            if (oEntity != null) {
                oEntity.setTipSigla(nEntity.getTipSigla());
                oEntity.setTipNombre(nEntity.getTipNombre());
                tipoidentificacionController.edit(oEntity);
                rs.status(201);
                retorno.put("status", 201);
                retorno.put("message", "Registro con el id@" + tidId + " actualizado!");
                retorno.put("data", oEntity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id@" + tidId + " no exite!");
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
            int tidId = Integer.parseInt(rq.params("id"));
            tipoidentificacionController.destroy(tidId);
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
        List<Tipoidentificacion> entities = tipoidentificacionController.findTipoidentificacionEntities(); 
               
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
            int tidId = Integer.parseInt(rq.params("id"));
            Tipoidentificacion entity = tipoidentificacionController.findTipoidentificacion(tidId);
                    
            if (entity != null) {
                retorno.put("status", 200);
                retorno.put("message", "Registro con el id@" + tidId + " encontrado!");
                retorno.put("data", entity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id@" + tidId + " no exite!");
            }
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }
    
}

    
