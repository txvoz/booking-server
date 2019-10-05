package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.RolJpaController;
import co.edu.sena.booking.jpa.controllers.RolUsuarioJpaController;
import co.edu.sena.booking.jpa.entities.Calificacion_;
import static co.edu.sena.booking.jpa.entities.Calificacion_.fkUsuario;
import co.edu.sena.booking.jpa.entities.Rol;
import co.edu.sena.booking.jpa.entities.RolUsuario;
import co.edu.sena.booking.utils.JsonTransformer;
import co.edu.sena.booking.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.PersistenceException;
import spark.Request;
import spark.Response;

public class ApiRolUsuario extends BasicApi implements IApi {

    private static ApiRolUsuario instance = null;
    private String path = "/rolUsuario";
    private Gson gson = null;

    private RolUsuarioJpaController rolusuarioController = null;

    private ApiRolUsuario() {
        gson = new Gson();
        rolusuarioController = new RolUsuarioJpaController(Utils.getEM());
        init();
    }
    
    public static ApiRolUsuario singleton(){
        if (instance==null){
            instance = new ApiRolUsuario();
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
            RolUsuario nEntity = gson.fromJson(body,RolUsuario.class);
            rolusuarioController.create(nEntity);
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
            int Id  = Integer.parseInt(rq.params("id"));
            String body = rq.body();
            RolUsuario nEntity = gson.fromJson(body, RolUsuario.class);
            RolUsuario oEntity = rolusuarioController.findRolUsuario(Id);
            if (oEntity != null) {
                oEntity.setRolEstado(nEntity.getRolEstado());
                rolusuarioController.edit(oEntity);
                rs.status(201);
                retorno.put("status", 201);
                retorno.put("message", "Registro con el id@" + Id + " actualizado!");
                retorno.put("data", oEntity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id@" + Id + " no exite!");
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
            int Id = Integer.parseInt(rq.params("id"));
            rolusuarioController.destroy(Id);
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
        List<RolUsuario> entities = rolusuarioController.findRolUsuarioEntities();
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
            int Id = Integer.parseInt(rq.params("id"));
            RolUsuario entity = rolusuarioController.findRolUsuario(Id);
            if (entity != null) {
                retorno.put("status", 200);
                retorno.put("message", "Registro con el id@" + Id + " encontrado!");
                retorno.put("data", entity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id@" + Id + " no exite!");
            }
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }

}
    


