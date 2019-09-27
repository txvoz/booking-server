package co.edu.sena.booking.jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
import co.edu.sena.booking.jpa.entities.Usuario;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class ReservaClienteJpaController implements Serializable {

    public ReservaClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaCliente reservaCliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva fkReserva = reservaCliente.getFkReserva();
            if (fkReserva != null) {
                fkReserva = em.getReference(fkReserva.getClass(), fkReserva.getResId());
                reservaCliente.setFkReserva(fkReserva);
            }
            Usuario fkCliente = reservaCliente.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getUsuId());
                reservaCliente.setFkCliente(fkCliente);
            }
            em.persist(reservaCliente);
            if (fkReserva != null) {
                fkReserva.getReservaClienteList().add(reservaCliente);
                fkReserva = em.merge(fkReserva);
            }
            if (fkCliente != null) {
                fkCliente.getReservaClienteList().add(reservaCliente);
                fkCliente = em.merge(fkCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaCliente reservaCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaCliente persistentReservaCliente = em.find(ReservaCliente.class, reservaCliente.getRuId());
            Reserva fkReservaOld = persistentReservaCliente.getFkReserva();
            Reserva fkReservaNew = reservaCliente.getFkReserva();
            Usuario fkClienteOld = persistentReservaCliente.getFkCliente();
            Usuario fkClienteNew = reservaCliente.getFkCliente();
            if (fkReservaNew != null) {
                fkReservaNew = em.getReference(fkReservaNew.getClass(), fkReservaNew.getResId());
                reservaCliente.setFkReserva(fkReservaNew);
            }
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getUsuId());
                reservaCliente.setFkCliente(fkClienteNew);
            }
            reservaCliente = em.merge(reservaCliente);
            if (fkReservaOld != null && !fkReservaOld.equals(fkReservaNew)) {
                fkReservaOld.getReservaClienteList().remove(reservaCliente);
                fkReservaOld = em.merge(fkReservaOld);
            }
            if (fkReservaNew != null && !fkReservaNew.equals(fkReservaOld)) {
                fkReservaNew.getReservaClienteList().add(reservaCliente);
                fkReservaNew = em.merge(fkReservaNew);
            }
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getReservaClienteList().remove(reservaCliente);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getReservaClienteList().add(reservaCliente);
                fkClienteNew = em.merge(fkClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservaCliente.getRuId();
                if (findReservaCliente(id) == null) {
                    throw new NonexistentEntityException("The reservaCliente with id " + id + " no longer exists.");
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
            ReservaCliente reservaCliente;
            try {
                reservaCliente = em.getReference(ReservaCliente.class, id);
                reservaCliente.getRuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservaCliente with id " + id + " no longer exists.", enfe);
            }
            Reserva fkReserva = reservaCliente.getFkReserva();
            if (fkReserva != null) {
                fkReserva.getReservaClienteList().remove(reservaCliente);
                fkReserva = em.merge(fkReserva);
            }
            Usuario fkCliente = reservaCliente.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getReservaClienteList().remove(reservaCliente);
                fkCliente = em.merge(fkCliente);
            }
            em.remove(reservaCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaCliente> findReservaClienteEntities() {
        return findReservaClienteEntities(true, -1, -1);
    }

    public List<ReservaCliente> findReservaClienteEntities(int maxResults, int firstResult) {
        return findReservaClienteEntities(false, maxResults, firstResult);
    }

    private List<ReservaCliente> findReservaClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReservaCliente.class));
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

    public ReservaCliente findReservaCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReservaCliente> rt = cq.from(ReservaCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
