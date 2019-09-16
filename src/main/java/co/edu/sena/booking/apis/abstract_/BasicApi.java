package co.edu.sena.booking.apis.abstract_;
import co.edu.sena.booking.utils.JsonTransformer;
import spark.Spark;

public abstract class BasicApi {
    
    public void init(){
        if(this instanceof IApi){
            JsonTransformer jt = JsonTransformer.singleton();
            //*************
            IApi ia = (IApi) this;
            //Traer todos
            Spark.get(ia.getPath(),(rq,rs)->ia.getAll(rq, rs),jt);
            //Traer detalle
            Spark.get(ia.getPath()+"/:id",(rq,rs)->ia.getById(rq, rs),jt);
            //Crear un recurso
            Spark.post(ia.getPath(), (rq,rs)->ia.create(rq, rs),jt);
            //Actualizar un recurso
            Spark.put(ia.getPath()+"/:id", (rq,rs)->ia.update(rq, rs),jt);
            //Eliminar un recurso
            Spark.delete(ia.getPath()+"/:id", (rq,rs)->ia.delete(rq, rs),jt);
        }else{
            System.out.println("No ha implementado IApi!");
        }
    }
    
}
