/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.Calificacion;
import co.edu.sena.booking.jpa.entities.Usuario;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alojamiento fkAlojamiento = calificacion.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento = em.getReference(fkAlojamiento.getClass(), fkAlojamiento.getAloId());
                calificacion.setFkAlojamiento(fkAlojamiento);
            }
            Usuario fkUsuario = calificacion.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getUsuId());
                calificacion.setFkUsuario(fkUsuario);
            }
            em.persist(calificacion);
            if (fkAlojamiento != null) {
                fkAlojamiento.getCalificacionList().add(calificacion);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            if (fkUsuario != null) {
                fkUsuario.getCalificacionList().add(calificacion);
                fkUsuario = em.merge(fkUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificacion calificacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getCalId());
            Alojamiento fkAlojamientoOld = persistentCalificacion.getFkAlojamiento();
            Alojamiento fkAlojamientoNew = calificacion.getFkAlojamiento();
            Usuario fkUsuarioOld = persistentCalificacion.getFkUsuario();
            Usuario fkUsuarioNew = calificacion.getFkUsuario();
            if (fkAlojamientoNew != null) {
                fkAlojamientoNew = em.getReference(fkAlojamientoNew.getClass(), fkAlojamientoNew.getAloId());
                calificacion.setFkAlojamiento(fkAlojamientoNew);
            }
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getUsuId());
                calificacion.setFkUsuario(fkUsuarioNew);
            }
            calificacion = em.merge(calificacion);
            if (fkAlojamientoOld != null && !fkAlojamientoOld.equals(fkAlojamientoNew)) {
                fkAlojamientoOld.getCalificacionList().remove(calificacion);
                fkAlojamientoOld = em.merge(fkAlojamientoOld);
            }
            if (fkAlojamientoNew != null && !fkAlojamientoNew.equals(fkAlojamientoOld)) {
                fkAlojamientoNew.getCalificacionList().add(calificacion);
                fkAlojamientoNew = em.merge(fkAlojamientoNew);
            }
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getCalificacionList().remove(calificacion);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getCalificacionList().add(calificacion);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calificacion.getCalId();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getCalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            Alojamiento fkAlojamiento = calificacion.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento.getCalificacionList().remove(calificacion);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            Usuario fkUsuario = calificacion.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getCalificacionList().remove(calificacion);
                fkUsuario = em.merge(fkUsuario);
            }
            em.remove(calificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Calificacion findCalificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
