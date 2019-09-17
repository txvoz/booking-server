package co.edu.sena.booking.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Utils {
    private static final String JPU = "JPU_BOOKING";
    private static EntityManagerFactory em = null;
    
    public static EntityManagerFactory getEM(){
        if(em==null){
            em = Persistence.createEntityManagerFactory(JPU);
        }
        return em;
    }
}
