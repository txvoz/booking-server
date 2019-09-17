/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.controllers;

import co.edu.sena.booking.jpa.entities.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.RolUsuario;
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
public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getRolUsuarioList() == null) {
            rol.setRolUsuarioList(new ArrayList<RolUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RolUsuario> attachedRolUsuarioList = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListRolUsuarioToAttach : rol.getRolUsuarioList()) {
                rolUsuarioListRolUsuarioToAttach = em.getReference(rolUsuarioListRolUsuarioToAttach.getClass(), rolUsuarioListRolUsuarioToAttach.getRuId());
                attachedRolUsuarioList.add(rolUsuarioListRolUsuarioToAttach);
            }
            rol.setRolUsuarioList(attachedRolUsuarioList);
            em.persist(rol);
            for (RolUsuario rolUsuarioListRolUsuario : rol.getRolUsuarioList()) {
                Rol oldFkRolOfRolUsuarioListRolUsuario = rolUsuarioListRolUsuario.getFkRol();
                rolUsuarioListRolUsuario.setFkRol(rol);
                rolUsuarioListRolUsuario = em.merge(rolUsuarioListRolUsuario);
                if (oldFkRolOfRolUsuarioListRolUsuario != null) {
                    oldFkRolOfRolUsuarioListRolUsuario.getRolUsuarioList().remove(rolUsuarioListRolUsuario);
                    oldFkRolOfRolUsuarioListRolUsuario = em.merge(oldFkRolOfRolUsuarioListRolUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getRolId());
            List<RolUsuario> rolUsuarioListOld = persistentRol.getRolUsuarioList();
            List<RolUsuario> rolUsuarioListNew = rol.getRolUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (RolUsuario rolUsuarioListOldRolUsuario : rolUsuarioListOld) {
                if (!rolUsuarioListNew.contains(rolUsuarioListOldRolUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolUsuario " + rolUsuarioListOldRolUsuario + " since its fkRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RolUsuario> attachedRolUsuarioListNew = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListNewRolUsuarioToAttach : rolUsuarioListNew) {
                rolUsuarioListNewRolUsuarioToAttach = em.getReference(rolUsuarioListNewRolUsuarioToAttach.getClass(), rolUsuarioListNewRolUsuarioToAttach.getRuId());
                attachedRolUsuarioListNew.add(rolUsuarioListNewRolUsuarioToAttach);
            }
            rolUsuarioListNew = attachedRolUsuarioListNew;
            rol.setRolUsuarioList(rolUsuarioListNew);
            rol = em.merge(rol);
            for (RolUsuario rolUsuarioListNewRolUsuario : rolUsuarioListNew) {
                if (!rolUsuarioListOld.contains(rolUsuarioListNewRolUsuario)) {
                    Rol oldFkRolOfRolUsuarioListNewRolUsuario = rolUsuarioListNewRolUsuario.getFkRol();
                    rolUsuarioListNewRolUsuario.setFkRol(rol);
                    rolUsuarioListNewRolUsuario = em.merge(rolUsuarioListNewRolUsuario);
                    if (oldFkRolOfRolUsuarioListNewRolUsuario != null && !oldFkRolOfRolUsuarioListNewRolUsuario.equals(rol)) {
                        oldFkRolOfRolUsuarioListNewRolUsuario.getRolUsuarioList().remove(rolUsuarioListNewRolUsuario);
                        oldFkRolOfRolUsuarioListNewRolUsuario = em.merge(oldFkRolOfRolUsuarioListNewRolUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getRolId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getRolId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RolUsuario> rolUsuarioListOrphanCheck = rol.getRolUsuarioList();
            for (RolUsuario rolUsuarioListOrphanCheckRolUsuario : rolUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the RolUsuario " + rolUsuarioListOrphanCheckRolUsuario + " in its rolUsuarioList field has a non-nullable fkRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
