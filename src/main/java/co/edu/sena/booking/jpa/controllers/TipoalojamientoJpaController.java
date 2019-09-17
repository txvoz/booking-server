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
import co.edu.sena.booking.jpa.entities.Tipoalojamiento;
import co.edu.sena.booking.jpa.controllers.exceptions.IllegalOrphanException;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class TipoalojamientoJpaController implements Serializable {

    public TipoalojamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoalojamiento tipoalojamiento) {
        if (tipoalojamiento.getAlojamientoList() == null) {
            tipoalojamiento.setAlojamientoList(new ArrayList<Alojamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Alojamiento> attachedAlojamientoList = new ArrayList<Alojamiento>();
            for (Alojamiento alojamientoListAlojamientoToAttach : tipoalojamiento.getAlojamientoList()) {
                alojamientoListAlojamientoToAttach = em.getReference(alojamientoListAlojamientoToAttach.getClass(), alojamientoListAlojamientoToAttach.getAloId());
                attachedAlojamientoList.add(alojamientoListAlojamientoToAttach);
            }
            tipoalojamiento.setAlojamientoList(attachedAlojamientoList);
            em.persist(tipoalojamiento);
            for (Alojamiento alojamientoListAlojamiento : tipoalojamiento.getAlojamientoList()) {
                Tipoalojamiento oldFkTipoAlojamientoOfAlojamientoListAlojamiento = alojamientoListAlojamiento.getFkTipoAlojamiento();
                alojamientoListAlojamiento.setFkTipoAlojamiento(tipoalojamiento);
                alojamientoListAlojamiento = em.merge(alojamientoListAlojamiento);
                if (oldFkTipoAlojamientoOfAlojamientoListAlojamiento != null) {
                    oldFkTipoAlojamientoOfAlojamientoListAlojamiento.getAlojamientoList().remove(alojamientoListAlojamiento);
                    oldFkTipoAlojamientoOfAlojamientoListAlojamiento = em.merge(oldFkTipoAlojamientoOfAlojamientoListAlojamiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoalojamiento tipoalojamiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoalojamiento persistentTipoalojamiento = em.find(Tipoalojamiento.class, tipoalojamiento.getTalId());
            List<Alojamiento> alojamientoListOld = persistentTipoalojamiento.getAlojamientoList();
            List<Alojamiento> alojamientoListNew = tipoalojamiento.getAlojamientoList();
            List<String> illegalOrphanMessages = null;
            for (Alojamiento alojamientoListOldAlojamiento : alojamientoListOld) {
                if (!alojamientoListNew.contains(alojamientoListOldAlojamiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alojamiento " + alojamientoListOldAlojamiento + " since its fkTipoAlojamiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Alojamiento> attachedAlojamientoListNew = new ArrayList<Alojamiento>();
            for (Alojamiento alojamientoListNewAlojamientoToAttach : alojamientoListNew) {
                alojamientoListNewAlojamientoToAttach = em.getReference(alojamientoListNewAlojamientoToAttach.getClass(), alojamientoListNewAlojamientoToAttach.getAloId());
                attachedAlojamientoListNew.add(alojamientoListNewAlojamientoToAttach);
            }
            alojamientoListNew = attachedAlojamientoListNew;
            tipoalojamiento.setAlojamientoList(alojamientoListNew);
            tipoalojamiento = em.merge(tipoalojamiento);
            for (Alojamiento alojamientoListNewAlojamiento : alojamientoListNew) {
                if (!alojamientoListOld.contains(alojamientoListNewAlojamiento)) {
                    Tipoalojamiento oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento = alojamientoListNewAlojamiento.getFkTipoAlojamiento();
                    alojamientoListNewAlojamiento.setFkTipoAlojamiento(tipoalojamiento);
                    alojamientoListNewAlojamiento = em.merge(alojamientoListNewAlojamiento);
                    if (oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento != null && !oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento.equals(tipoalojamiento)) {
                        oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento.getAlojamientoList().remove(alojamientoListNewAlojamiento);
                        oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento = em.merge(oldFkTipoAlojamientoOfAlojamientoListNewAlojamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoalojamiento.getTalId();
                if (findTipoalojamiento(id) == null) {
                    throw new NonexistentEntityException("The tipoalojamiento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoalojamiento tipoalojamiento;
            try {
                tipoalojamiento = em.getReference(Tipoalojamiento.class, id);
                tipoalojamiento.getTalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoalojamiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Alojamiento> alojamientoListOrphanCheck = tipoalojamiento.getAlojamientoList();
            for (Alojamiento alojamientoListOrphanCheckAlojamiento : alojamientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoalojamiento (" + tipoalojamiento + ") cannot be destroyed since the Alojamiento " + alojamientoListOrphanCheckAlojamiento + " in its alojamientoList field has a non-nullable fkTipoAlojamiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoalojamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoalojamiento> findTipoalojamientoEntities() {
        return findTipoalojamientoEntities(true, -1, -1);
    }

    public List<Tipoalojamiento> findTipoalojamientoEntities(int maxResults, int firstResult) {
        return findTipoalojamientoEntities(false, maxResults, firstResult);
    }

    private List<Tipoalojamiento> findTipoalojamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoalojamiento.class));
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

    public Tipoalojamiento findTipoalojamiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoalojamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoalojamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoalojamiento> rt = cq.from(Tipoalojamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
