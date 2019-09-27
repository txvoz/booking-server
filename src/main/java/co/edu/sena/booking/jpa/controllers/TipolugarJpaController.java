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
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.entities.Tipolugar;
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
public class TipolugarJpaController implements Serializable {

    public TipolugarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipolugar tipolugar) {
        if (tipolugar.getLugarList() == null) {
            tipolugar.setLugarList(new ArrayList<Lugar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Lugar> attachedLugarList = new ArrayList<Lugar>();
            for (Lugar lugarListLugarToAttach : tipolugar.getLugarList()) {
                lugarListLugarToAttach = em.getReference(lugarListLugarToAttach.getClass(), lugarListLugarToAttach.getLugId());
                attachedLugarList.add(lugarListLugarToAttach);
            }
            tipolugar.setLugarList(attachedLugarList);
            em.persist(tipolugar);
            for (Lugar lugarListLugar : tipolugar.getLugarList()) {
                Tipolugar oldFkTipoLugarOfLugarListLugar = lugarListLugar.getFkTipoLugar();
                lugarListLugar.setFkTipoLugar(tipolugar);
                lugarListLugar = em.merge(lugarListLugar);
                if (oldFkTipoLugarOfLugarListLugar != null) {
                    oldFkTipoLugarOfLugarListLugar.getLugarList().remove(lugarListLugar);
                    oldFkTipoLugarOfLugarListLugar = em.merge(oldFkTipoLugarOfLugarListLugar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipolugar tipolugar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipolugar persistentTipolugar = em.find(Tipolugar.class, tipolugar.getTluId());
            List<Lugar> lugarListOld = persistentTipolugar.getLugarList();
            List<Lugar> lugarListNew = tipolugar.getLugarList();
            List<String> illegalOrphanMessages = null;
            for (Lugar lugarListOldLugar : lugarListOld) {
                if (!lugarListNew.contains(lugarListOldLugar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lugar " + lugarListOldLugar + " since its fkTipoLugar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Lugar> attachedLugarListNew = new ArrayList<Lugar>();
            for (Lugar lugarListNewLugarToAttach : lugarListNew) {
                lugarListNewLugarToAttach = em.getReference(lugarListNewLugarToAttach.getClass(), lugarListNewLugarToAttach.getLugId());
                attachedLugarListNew.add(lugarListNewLugarToAttach);
            }
            lugarListNew = attachedLugarListNew;
            tipolugar.setLugarList(lugarListNew);
            tipolugar = em.merge(tipolugar);
            for (Lugar lugarListNewLugar : lugarListNew) {
                if (!lugarListOld.contains(lugarListNewLugar)) {
                    Tipolugar oldFkTipoLugarOfLugarListNewLugar = lugarListNewLugar.getFkTipoLugar();
                    lugarListNewLugar.setFkTipoLugar(tipolugar);
                    lugarListNewLugar = em.merge(lugarListNewLugar);
                    if (oldFkTipoLugarOfLugarListNewLugar != null && !oldFkTipoLugarOfLugarListNewLugar.equals(tipolugar)) {
                        oldFkTipoLugarOfLugarListNewLugar.getLugarList().remove(lugarListNewLugar);
                        oldFkTipoLugarOfLugarListNewLugar = em.merge(oldFkTipoLugarOfLugarListNewLugar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipolugar.getTluId();
                if (findTipolugar(id) == null) {
                    throw new NonexistentEntityException("The tipolugar with id " + id + " no longer exists.");
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
            Tipolugar tipolugar;
            try {
                tipolugar = em.getReference(Tipolugar.class, id);
                tipolugar.getTluId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipolugar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lugar> lugarListOrphanCheck = tipolugar.getLugarList();
            for (Lugar lugarListOrphanCheckLugar : lugarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipolugar (" + tipolugar + ") cannot be destroyed since the Lugar " + lugarListOrphanCheckLugar + " in its lugarList field has a non-nullable fkTipoLugar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipolugar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipolugar> findTipolugarEntities() {
        return findTipolugarEntities(true, -1, -1);
    }

    public List<Tipolugar> findTipolugarEntities(int maxResults, int firstResult) {
        return findTipolugarEntities(false, maxResults, firstResult);
    }

    private List<Tipolugar> findTipolugarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipolugar.class));
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

    public Tipolugar findTipolugar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            
            return em.find(Tipolugar.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipolugarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipolugar> rt = cq.from(Tipolugar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
