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
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.entities.Usuario;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
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
public class ReservaJpaController implements Serializable {

    public ReservaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reserva reserva) {
        if (reserva.getReservaClienteList() == null) {
            reserva.setReservaClienteList(new ArrayList<ReservaCliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alojamiento fkAlojamiento = reserva.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento = em.getReference(fkAlojamiento.getClass(), fkAlojamiento.getAloId());
                reserva.setFkAlojamiento(fkAlojamiento);
            }
            Usuario fkCliente = reserva.getFkCliente();
            if (fkCliente != null) {
                fkCliente = em.getReference(fkCliente.getClass(), fkCliente.getUsuId());
                reserva.setFkCliente(fkCliente);
            }
            Usuario fkUsuarioChecking = reserva.getFkUsuarioChecking();
            if (fkUsuarioChecking != null) {
                fkUsuarioChecking = em.getReference(fkUsuarioChecking.getClass(), fkUsuarioChecking.getUsuId());
                reserva.setFkUsuarioChecking(fkUsuarioChecking);
            }
            Usuario fkUsuarioCheckout = reserva.getFkUsuarioCheckout();
            if (fkUsuarioCheckout != null) {
                fkUsuarioCheckout = em.getReference(fkUsuarioCheckout.getClass(), fkUsuarioCheckout.getUsuId());
                reserva.setFkUsuarioCheckout(fkUsuarioCheckout);
            }
            List<ReservaCliente> attachedReservaClienteList = new ArrayList<ReservaCliente>();
            for (ReservaCliente reservaClienteListReservaClienteToAttach : reserva.getReservaClienteList()) {
                reservaClienteListReservaClienteToAttach = em.getReference(reservaClienteListReservaClienteToAttach.getClass(), reservaClienteListReservaClienteToAttach.getRuId());
                attachedReservaClienteList.add(reservaClienteListReservaClienteToAttach);
            }
            reserva.setReservaClienteList(attachedReservaClienteList);
            em.persist(reserva);
            if (fkAlojamiento != null) {
                fkAlojamiento.getReservaList().add(reserva);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            if (fkCliente != null) {
                fkCliente.getReservaList().add(reserva);
                fkCliente = em.merge(fkCliente);
            }
            if (fkUsuarioChecking != null) {
                fkUsuarioChecking.getReservaList().add(reserva);
                fkUsuarioChecking = em.merge(fkUsuarioChecking);
            }
            if (fkUsuarioCheckout != null) {
                fkUsuarioCheckout.getReservaList().add(reserva);
                fkUsuarioCheckout = em.merge(fkUsuarioCheckout);
            }
            for (ReservaCliente reservaClienteListReservaCliente : reserva.getReservaClienteList()) {
                Reserva oldFkReservaOfReservaClienteListReservaCliente = reservaClienteListReservaCliente.getFkReserva();
                reservaClienteListReservaCliente.setFkReserva(reserva);
                reservaClienteListReservaCliente = em.merge(reservaClienteListReservaCliente);
                if (oldFkReservaOfReservaClienteListReservaCliente != null) {
                    oldFkReservaOfReservaClienteListReservaCliente.getReservaClienteList().remove(reservaClienteListReservaCliente);
                    oldFkReservaOfReservaClienteListReservaCliente = em.merge(oldFkReservaOfReservaClienteListReservaCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getResId());
            Alojamiento fkAlojamientoOld = persistentReserva.getFkAlojamiento();
            Alojamiento fkAlojamientoNew = reserva.getFkAlojamiento();
            Usuario fkClienteOld = persistentReserva.getFkCliente();
            Usuario fkClienteNew = reserva.getFkCliente();
            Usuario fkUsuarioCheckingOld = persistentReserva.getFkUsuarioChecking();
            Usuario fkUsuarioCheckingNew = reserva.getFkUsuarioChecking();
            Usuario fkUsuarioCheckoutOld = persistentReserva.getFkUsuarioCheckout();
            Usuario fkUsuarioCheckoutNew = reserva.getFkUsuarioCheckout();
            List<ReservaCliente> reservaClienteListOld = persistentReserva.getReservaClienteList();
            List<ReservaCliente> reservaClienteListNew = reserva.getReservaClienteList();
            List<String> illegalOrphanMessages = null;
            for (ReservaCliente reservaClienteListOldReservaCliente : reservaClienteListOld) {
                if (!reservaClienteListNew.contains(reservaClienteListOldReservaCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReservaCliente " + reservaClienteListOldReservaCliente + " since its fkReserva field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkAlojamientoNew != null) {
                fkAlojamientoNew = em.getReference(fkAlojamientoNew.getClass(), fkAlojamientoNew.getAloId());
                reserva.setFkAlojamiento(fkAlojamientoNew);
            }
            if (fkClienteNew != null) {
                fkClienteNew = em.getReference(fkClienteNew.getClass(), fkClienteNew.getUsuId());
                reserva.setFkCliente(fkClienteNew);
            }
            if (fkUsuarioCheckingNew != null) {
                fkUsuarioCheckingNew = em.getReference(fkUsuarioCheckingNew.getClass(), fkUsuarioCheckingNew.getUsuId());
                reserva.setFkUsuarioChecking(fkUsuarioCheckingNew);
            }
            if (fkUsuarioCheckoutNew != null) {
                fkUsuarioCheckoutNew = em.getReference(fkUsuarioCheckoutNew.getClass(), fkUsuarioCheckoutNew.getUsuId());
                reserva.setFkUsuarioCheckout(fkUsuarioCheckoutNew);
            }
            List<ReservaCliente> attachedReservaClienteListNew = new ArrayList<ReservaCliente>();
            for (ReservaCliente reservaClienteListNewReservaClienteToAttach : reservaClienteListNew) {
                reservaClienteListNewReservaClienteToAttach = em.getReference(reservaClienteListNewReservaClienteToAttach.getClass(), reservaClienteListNewReservaClienteToAttach.getRuId());
                attachedReservaClienteListNew.add(reservaClienteListNewReservaClienteToAttach);
            }
            reservaClienteListNew = attachedReservaClienteListNew;
            reserva.setReservaClienteList(reservaClienteListNew);
            reserva = em.merge(reserva);
            if (fkAlojamientoOld != null && !fkAlojamientoOld.equals(fkAlojamientoNew)) {
                fkAlojamientoOld.getReservaList().remove(reserva);
                fkAlojamientoOld = em.merge(fkAlojamientoOld);
            }
            if (fkAlojamientoNew != null && !fkAlojamientoNew.equals(fkAlojamientoOld)) {
                fkAlojamientoNew.getReservaList().add(reserva);
                fkAlojamientoNew = em.merge(fkAlojamientoNew);
            }
            if (fkClienteOld != null && !fkClienteOld.equals(fkClienteNew)) {
                fkClienteOld.getReservaList().remove(reserva);
                fkClienteOld = em.merge(fkClienteOld);
            }
            if (fkClienteNew != null && !fkClienteNew.equals(fkClienteOld)) {
                fkClienteNew.getReservaList().add(reserva);
                fkClienteNew = em.merge(fkClienteNew);
            }
            if (fkUsuarioCheckingOld != null && !fkUsuarioCheckingOld.equals(fkUsuarioCheckingNew)) {
                fkUsuarioCheckingOld.getReservaList().remove(reserva);
                fkUsuarioCheckingOld = em.merge(fkUsuarioCheckingOld);
            }
            if (fkUsuarioCheckingNew != null && !fkUsuarioCheckingNew.equals(fkUsuarioCheckingOld)) {
                fkUsuarioCheckingNew.getReservaList().add(reserva);
                fkUsuarioCheckingNew = em.merge(fkUsuarioCheckingNew);
            }
            if (fkUsuarioCheckoutOld != null && !fkUsuarioCheckoutOld.equals(fkUsuarioCheckoutNew)) {
                fkUsuarioCheckoutOld.getReservaList().remove(reserva);
                fkUsuarioCheckoutOld = em.merge(fkUsuarioCheckoutOld);
            }
            if (fkUsuarioCheckoutNew != null && !fkUsuarioCheckoutNew.equals(fkUsuarioCheckoutOld)) {
                fkUsuarioCheckoutNew.getReservaList().add(reserva);
                fkUsuarioCheckoutNew = em.merge(fkUsuarioCheckoutNew);
            }
            for (ReservaCliente reservaClienteListNewReservaCliente : reservaClienteListNew) {
                if (!reservaClienteListOld.contains(reservaClienteListNewReservaCliente)) {
                    Reserva oldFkReservaOfReservaClienteListNewReservaCliente = reservaClienteListNewReservaCliente.getFkReserva();
                    reservaClienteListNewReservaCliente.setFkReserva(reserva);
                    reservaClienteListNewReservaCliente = em.merge(reservaClienteListNewReservaCliente);
                    if (oldFkReservaOfReservaClienteListNewReservaCliente != null && !oldFkReservaOfReservaClienteListNewReservaCliente.equals(reserva)) {
                        oldFkReservaOfReservaClienteListNewReservaCliente.getReservaClienteList().remove(reservaClienteListNewReservaCliente);
                        oldFkReservaOfReservaClienteListNewReservaCliente = em.merge(oldFkReservaOfReservaClienteListNewReservaCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reserva.getResId();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
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
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getResId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ReservaCliente> reservaClienteListOrphanCheck = reserva.getReservaClienteList();
            for (ReservaCliente reservaClienteListOrphanCheckReservaCliente : reservaClienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reserva (" + reserva + ") cannot be destroyed since the ReservaCliente " + reservaClienteListOrphanCheckReservaCliente + " in its reservaClienteList field has a non-nullable fkReserva field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Alojamiento fkAlojamiento = reserva.getFkAlojamiento();
            if (fkAlojamiento != null) {
                fkAlojamiento.getReservaList().remove(reserva);
                fkAlojamiento = em.merge(fkAlojamiento);
            }
            Usuario fkCliente = reserva.getFkCliente();
            if (fkCliente != null) {
                fkCliente.getReservaList().remove(reserva);
                fkCliente = em.merge(fkCliente);
            }
            Usuario fkUsuarioChecking = reserva.getFkUsuarioChecking();
            if (fkUsuarioChecking != null) {
                fkUsuarioChecking.getReservaList().remove(reserva);
                fkUsuarioChecking = em.merge(fkUsuarioChecking);
            }
            Usuario fkUsuarioCheckout = reserva.getFkUsuarioCheckout();
            if (fkUsuarioCheckout != null) {
                fkUsuarioCheckout.getReservaList().remove(reserva);
                fkUsuarioCheckout = em.merge(fkUsuarioCheckout);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
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

    public Reserva findReserva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
