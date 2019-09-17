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
import co.edu.sena.booking.jpa.entities.Foto;
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class FotoJpaController implements Serializable {

    public FotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foto foto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alojamiento fkAlojamiento = foto.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento = em.getReference(fkAlojamiento.getClass(), fkAlojamiento.getAloId());
                foto.setFkAlojamiento(fkAlojamiento);
            }
            Lugar fkLugar = foto.getFkLugar();
            if (fkLugar != null) {
                fkLugar = em.getReference(fkLugar.getClass(), fkLugar.getLugId());
                foto.setFkLugar(fkLugar);
            }
            em.persist(foto);
            if (fkAlojamiento != null) {
                fkAlojamiento.getFotoList().add(foto);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            if (fkLugar != null) {
                fkLugar.getFotoList().add(foto);
                fkLugar = em.merge(fkLugar);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foto foto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foto persistentFoto = em.find(Foto.class, foto.getFotId());
            Alojamiento fkAlojamientoOld = persistentFoto.getFkAlojamiento();
            Alojamiento fkAlojamientoNew = foto.getFkAlojamiento();
            Lugar fkLugarOld = persistentFoto.getFkLugar();
            Lugar fkLugarNew = foto.getFkLugar();
            if (fkAlojamientoNew != null) {
                fkAlojamientoNew = em.getReference(fkAlojamientoNew.getClass(), fkAlojamientoNew.getAloId());
                foto.setFkAlojamiento(fkAlojamientoNew);
            }
            if (fkLugarNew != null) {
                fkLugarNew = em.getReference(fkLugarNew.getClass(), fkLugarNew.getLugId());
                foto.setFkLugar(fkLugarNew);
            }
            foto = em.merge(foto);
            if (fkAlojamientoOld != null && !fkAlojamientoOld.equals(fkAlojamientoNew)) {
                fkAlojamientoOld.getFotoList().remove(foto);
                fkAlojamientoOld = em.merge(fkAlojamientoOld);
            }
            if (fkAlojamientoNew != null && !fkAlojamientoNew.equals(fkAlojamientoOld)) {
                fkAlojamientoNew.getFotoList().add(foto);
                fkAlojamientoNew = em.merge(fkAlojamientoNew);
            }
            if (fkLugarOld != null && !fkLugarOld.equals(fkLugarNew)) {
                fkLugarOld.getFotoList().remove(foto);
                fkLugarOld = em.merge(fkLugarOld);
            }
            if (fkLugarNew != null && !fkLugarNew.equals(fkLugarOld)) {
                fkLugarNew.getFotoList().add(foto);
                fkLugarNew = em.merge(fkLugarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foto.getFotId();
                if (findFoto(id) == null) {
                    throw new NonexistentEntityException("The foto with id " + id + " no longer exists.");
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
            Foto foto;
            try {
                foto = em.getReference(Foto.class, id);
                foto.getFotId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foto with id " + id + " no longer exists.", enfe);
            }
            Alojamiento fkAlojamiento = foto.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento.getFotoList().remove(foto);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            Lugar fkLugar = foto.getFkLugar();
            if (fkLugar != null) {
                fkLugar.getFotoList().remove(foto);
                fkLugar = em.merge(fkLugar);
            }
            em.remove(foto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foto> findFotoEntities() {
        return findFotoEntities(true, -1, -1);
    }

    public List<Foto> findFotoEntities(int maxResults, int firstResult) {
        return findFotoEntities(false, maxResults, firstResult);
    }

    private List<Foto> findFotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foto.class));
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

    public Foto findFoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foto> rt = cq.from(Foto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
