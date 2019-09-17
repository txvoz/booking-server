/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.controllers;

import co.edu.sena.booking.jpa.entities.Tipoidentificacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.Usuario;
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
public class TipoidentificacionJpaController implements Serializable {

    public TipoidentificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoidentificacion tipoidentificacion) {
        if (tipoidentificacion.getUsuarioList() == null) {
            tipoidentificacion.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipoidentificacion.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipoidentificacion.setUsuarioList(attachedUsuarioList);
            em.persist(tipoidentificacion);
            for (Usuario usuarioListUsuario : tipoidentificacion.getUsuarioList()) {
                Tipoidentificacion oldFkTipoIdentificacionOfUsuarioListUsuario = usuarioListUsuario.getFkTipoIdentificacion();
                usuarioListUsuario.setFkTipoIdentificacion(tipoidentificacion);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldFkTipoIdentificacionOfUsuarioListUsuario != null) {
                    oldFkTipoIdentificacionOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldFkTipoIdentificacionOfUsuarioListUsuario = em.merge(oldFkTipoIdentificacionOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoidentificacion tipoidentificacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoidentificacion persistentTipoidentificacion = em.find(Tipoidentificacion.class, tipoidentificacion.getTidId());
            List<Usuario> usuarioListOld = persistentTipoidentificacion.getUsuarioList();
            List<Usuario> usuarioListNew = tipoidentificacion.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its fkTipoIdentificacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tipoidentificacion.setUsuarioList(usuarioListNew);
            tipoidentificacion = em.merge(tipoidentificacion);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Tipoidentificacion oldFkTipoIdentificacionOfUsuarioListNewUsuario = usuarioListNewUsuario.getFkTipoIdentificacion();
                    usuarioListNewUsuario.setFkTipoIdentificacion(tipoidentificacion);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldFkTipoIdentificacionOfUsuarioListNewUsuario != null && !oldFkTipoIdentificacionOfUsuarioListNewUsuario.equals(tipoidentificacion)) {
                        oldFkTipoIdentificacionOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldFkTipoIdentificacionOfUsuarioListNewUsuario = em.merge(oldFkTipoIdentificacionOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoidentificacion.getTidId();
                if (findTipoidentificacion(id) == null) {
                    throw new NonexistentEntityException("The tipoidentificacion with id " + id + " no longer exists.");
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
            Tipoidentificacion tipoidentificacion;
            try {
                tipoidentificacion = em.getReference(Tipoidentificacion.class, id);
                tipoidentificacion.getTidId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoidentificacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = tipoidentificacion.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoidentificacion (" + tipoidentificacion + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable fkTipoIdentificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoidentificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoidentificacion> findTipoidentificacionEntities() {
        return findTipoidentificacionEntities(true, -1, -1);
    }

    public List<Tipoidentificacion> findTipoidentificacionEntities(int maxResults, int firstResult) {
        return findTipoidentificacionEntities(false, maxResults, firstResult);
    }

    private List<Tipoidentificacion> findTipoidentificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoidentificacion.class));
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

    public Tipoidentificacion findTipoidentificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoidentificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoidentificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoidentificacion> rt = cq.from(Tipoidentificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
