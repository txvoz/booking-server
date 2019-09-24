/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.apis;

import co.edu.sena.booking.apis.abstract_.BasicApi;
import co.edu.sena.booking.apis.abstract_.IApi;
import co.edu.sena.booking.jpa.controllers.ReservaJpaController;
import co.edu.sena.booking.jpa.entities.Reserva;
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
 * @author MIYER
 */
public class ApiReserva extends BasicApi implements IApi {

    private static ApiReserva instance = null;
    private String path = "/reserva";
    private Gson gson = null;
    private ReservaJpaController reservaController = null;

    private ApiReserva() {
        reservaController = new ReservaJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    public static ApiReserva singleton() {
        if (instance == null) {
            instance = new ApiReserva();
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
            Reserva nEntity = gson.fromJson(body, Reserva.class);
            reservaController.create(nEntity);
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
            Reserva nEntity = gson.fromJson(body, Reserva.class);
            Reserva oEntity = reservaController.findReserva(id);
            if (oEntity != null) {
                oEntity.setResFechaRegistro(nEntity.getResFechaRegistro());
                oEntity.setResFechaLlegada(nEntity.getResFechaLlegada());
                oEntity.setResFechaSalida(nEntity.getResFechaSalida());
                oEntity.setResFechaChecking(nEntity.getResFechaChecking());
                oEntity.setResFechaCheckout(nEntity.getResFechaCheckout());
                oEntity.setResEstado(nEntity.getResEstado());
                oEntity.setResPago(nEntity.getResPago());
                oEntity.setFkCliente(nEntity.getFkCliente());
                oEntity.setFkAlojamiento(nEntity.getFkAlojamiento());
                oEntity.setFkUsuarioChecking(nEntity.getFkUsuarioChecking());
                oEntity.setFkUsuarioCheckout(nEntity.getFkUsuarioCheckout());
                reservaController.edit(oEntity);
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
            reservaController.destroy(id);
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
        List<Reserva> entities = reservaController.findReservaEntities();
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
            Reserva entity = reservaController.findReserva(id);
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
