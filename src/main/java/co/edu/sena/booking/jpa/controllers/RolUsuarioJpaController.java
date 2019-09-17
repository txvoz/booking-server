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
import co.edu.sena.booking.jpa.entities.Rol;
import co.edu.sena.booking.jpa.entities.RolUsuario;
import co.edu.sena.booking.jpa.entities.Usuario;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class RolUsuarioJpaController implements Serializable {

    public RolUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolUsuario rolUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol fkRol = rolUsuario.getFkRol();
            if (fkRol != null) {
                fkRol = em.getReference(fkRol.getClass(), fkRol.getRolId());
                rolUsuario.setFkRol(fkRol);
            }
            Usuario fkUsuario = rolUsuario.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario = em.getReference(fkUsuario.getClass(), fkUsuario.getUsuId());
                rolUsuario.setFkUsuario(fkUsuario);
            }
            em.persist(rolUsuario);
            if (fkRol != null) {
                fkRol.getRolUsuarioList().add(rolUsuario);
                fkRol = em.merge(fkRol);
            }
            if (fkUsuario != null) {
                fkUsuario.getRolUsuarioList().add(rolUsuario);
                fkUsuario = em.merge(fkUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RolUsuario rolUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolUsuario persistentRolUsuario = em.find(RolUsuario.class, rolUsuario.getRuId());
            Rol fkRolOld = persistentRolUsuario.getFkRol();
            Rol fkRolNew = rolUsuario.getFkRol();
            Usuario fkUsuarioOld = persistentRolUsuario.getFkUsuario();
            Usuario fkUsuarioNew = rolUsuario.getFkUsuario();
            if (fkRolNew != null) {
                fkRolNew = em.getReference(fkRolNew.getClass(), fkRolNew.getRolId());
                rolUsuario.setFkRol(fkRolNew);
            }
            if (fkUsuarioNew != null) {
                fkUsuarioNew = em.getReference(fkUsuarioNew.getClass(), fkUsuarioNew.getUsuId());
                rolUsuario.setFkUsuario(fkUsuarioNew);
            }
            rolUsuario = em.merge(rolUsuario);
            if (fkRolOld != null && !fkRolOld.equals(fkRolNew)) {
                fkRolOld.getRolUsuarioList().remove(rolUsuario);
                fkRolOld = em.merge(fkRolOld);
            }
            if (fkRolNew != null && !fkRolNew.equals(fkRolOld)) {
                fkRolNew.getRolUsuarioList().add(rolUsuario);
                fkRolNew = em.merge(fkRolNew);
            }
            if (fkUsuarioOld != null && !fkUsuarioOld.equals(fkUsuarioNew)) {
                fkUsuarioOld.getRolUsuarioList().remove(rolUsuario);
                fkUsuarioOld = em.merge(fkUsuarioOld);
            }
            if (fkUsuarioNew != null && !fkUsuarioNew.equals(fkUsuarioOld)) {
                fkUsuarioNew.getRolUsuarioList().add(rolUsuario);
                fkUsuarioNew = em.merge(fkUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolUsuario.getRuId();
                if (findRolUsuario(id) == null) {
                    throw new NonexistentEntityException("The rolUsuario with id " + id + " no longer exists.");
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
            RolUsuario rolUsuario;
            try {
                rolUsuario = em.getReference(RolUsuario.class, id);
                rolUsuario.getRuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolUsuario with id " + id + " no longer exists.", enfe);
            }
            Rol fkRol = rolUsuario.getFkRol();
            if (fkRol != null) {
                fkRol.getRolUsuarioList().remove(rolUsuario);
                fkRol = em.merge(fkRol);
            }
            Usuario fkUsuario = rolUsuario.getFkUsuario();
            if (fkUsuario != null) {
                fkUsuario.getRolUsuarioList().remove(rolUsuario);
                fkUsuario = em.merge(fkUsuario);
            }
            em.remove(rolUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RolUsuario> findRolUsuarioEntities() {
        return findRolUsuarioEntities(true, -1, -1);
    }

    public List<RolUsuario> findRolUsuarioEntities(int maxResults, int firstResult) {
        return findRolUsuarioEntities(false, maxResults, firstResult);
    }

    private List<RolUsuario> findRolUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolUsuario.class));
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

    public RolUsuario findRolUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolUsuario> rt = cq.from(RolUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
