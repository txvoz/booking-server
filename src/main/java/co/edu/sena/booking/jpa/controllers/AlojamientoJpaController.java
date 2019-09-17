/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.controllers;

import co.edu.sena.booking.jpa.entities.Alojamiento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.entities.Tipoalojamiento;
import co.edu.sena.booking.jpa.entities.Calificacion;
import java.util.ArrayList;
import java.util.List;
import co.edu.sena.booking.jpa.entities.Foto;
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.controllers.exceptions.IllegalOrphanException;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class AlojamientoJpaController implements Serializable {

    public AlojamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alojamiento alojamiento) {
        if (alojamiento.getCalificacionList() == null) {
            alojamiento.setCalificacionList(new ArrayList<Calificacion>());
        }
        if (alojamiento.getFotoList() == null) {
            alojamiento.setFotoList(new ArrayList<Foto>());
        }
        if (alojamiento.getReservaList() == null) {
            alojamiento.setReservaList(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugar fkLugar = alojamiento.getFkLugar();
            if (fkLugar != null) {
                fkLugar = em.getReference(fkLugar.getClass(), fkLugar.getLugId());
                alojamiento.setFkLugar(fkLugar);
            }
            Tipoalojamiento fkTipoAlojamiento = alojamiento.getFkTipoAlojamiento();
            if (fkTipoAlojamiento != null) {
                fkTipoAlojamiento = em.getReference(fkTipoAlojamiento.getClass(), fkTipoAlojamiento.getTalId());
                alojamiento.setFkTipoAlojamiento(fkTipoAlojamiento);
            }
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : alojamiento.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalId());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            alojamiento.setCalificacionList(attachedCalificacionList);
            List<Foto> attachedFotoList = new ArrayList<Foto>();
            for (Foto fotoListFotoToAttach : alojamiento.getFotoList()) {
                fotoListFotoToAttach = em.getReference(fotoListFotoToAttach.getClass(), fotoListFotoToAttach.getFotId());
                attachedFotoList.add(fotoListFotoToAttach);
            }
            alojamiento.setFotoList(attachedFotoList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : alojamiento.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getResId());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            alojamiento.setReservaList(attachedReservaList);
            em.persist(alojamiento);
            if (fkLugar != null) {
                fkLugar.getAlojamientoList().add(alojamiento);
                fkLugar = em.merge(fkLugar);
            }
            if (fkTipoAlojamiento != null) {
                fkTipoAlojamiento.getAlojamientoList().add(alojamiento);
                fkTipoAlojamiento = em.merge(fkTipoAlojamiento);
            }
            for (Calificacion calificacionListCalificacion : alojamiento.getCalificacionList()) {
                Alojamiento oldFkAlojamientoOfCalificacionListCalificacion = calificacionListCalificacion.getFkAlojamiento();
                calificacionListCalificacion.setFkAlojamiento(alojamiento);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldFkAlojamientoOfCalificacionListCalificacion != null) {
                    oldFkAlojamientoOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldFkAlojamientoOfCalificacionListCalificacion = em.merge(oldFkAlojamientoOfCalificacionListCalificacion);
                }
            }
            for (Foto fotoListFoto : alojamiento.getFotoList()) {
                Alojamiento oldFkAlojamientoOfFotoListFoto = fotoListFoto.getFkAlojamiento();
                fotoListFoto.setFkAlojamiento(alojamiento);
                fotoListFoto = em.merge(fotoListFoto);
                if (oldFkAlojamientoOfFotoListFoto != null) {
                    oldFkAlojamientoOfFotoListFoto.getFotoList().remove(fotoListFoto);
                    oldFkAlojamientoOfFotoListFoto = em.merge(oldFkAlojamientoOfFotoListFoto);
                }
            }
            for (Reserva reservaListReserva : alojamiento.getReservaList()) {
                Alojamiento oldFkAlojamientoOfReservaListReserva = reservaListReserva.getFkAlojamiento();
                reservaListReserva.setFkAlojamiento(alojamiento);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldFkAlojamientoOfReservaListReserva != null) {
                    oldFkAlojamientoOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldFkAlojamientoOfReservaListReserva = em.merge(oldFkAlojamientoOfReservaListReserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alojamiento alojamiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alojamiento persistentAlojamiento = em.find(Alojamiento.class, alojamiento.getAloId());
            Lugar fkLugarOld = persistentAlojamiento.getFkLugar();
            Lugar fkLugarNew = alojamiento.getFkLugar();
            Tipoalojamiento fkTipoAlojamientoOld = persistentAlojamiento.getFkTipoAlojamiento();
            Tipoalojamiento fkTipoAlojamientoNew = alojamiento.getFkTipoAlojamiento();
            List<Calificacion> calificacionListOld = persistentAlojamiento.getCalificacionList();
            List<Calificacion> calificacionListNew = alojamiento.getCalificacionList();
            List<Foto> fotoListOld = persistentAlojamiento.getFotoList();
            List<Foto> fotoListNew = alojamiento.getFotoList();
            List<Reserva> reservaListOld = persistentAlojamiento.getReservaList();
            List<Reserva> reservaListNew = alojamiento.getReservaList();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its fkAlojamiento field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its fkAlojamiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkLugarNew != null) {
                fkLugarNew = em.getReference(fkLugarNew.getClass(), fkLugarNew.getLugId());
                alojamiento.setFkLugar(fkLugarNew);
            }
            if (fkTipoAlojamientoNew != null) {
                fkTipoAlojamientoNew = em.getReference(fkTipoAlojamientoNew.getClass(), fkTipoAlojamientoNew.getTalId());
                alojamiento.setFkTipoAlojamiento(fkTipoAlojamientoNew);
            }
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalId());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            alojamiento.setCalificacionList(calificacionListNew);
            List<Foto> attachedFotoListNew = new ArrayList<Foto>();
            for (Foto fotoListNewFotoToAttach : fotoListNew) {
                fotoListNewFotoToAttach = em.getReference(fotoListNewFotoToAttach.getClass(), fotoListNewFotoToAttach.getFotId());
                attachedFotoListNew.add(fotoListNewFotoToAttach);
            }
            fotoListNew = attachedFotoListNew;
            alojamiento.setFotoList(fotoListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getResId());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            alojamiento.setReservaList(reservaListNew);
            alojamiento = em.merge(alojamiento);
            if (fkLugarOld != null && !fkLugarOld.equals(fkLugarNew)) {
                fkLugarOld.getAlojamientoList().remove(alojamiento);
                fkLugarOld = em.merge(fkLugarOld);
            }
            if (fkLugarNew != null && !fkLugarNew.equals(fkLugarOld)) {
                fkLugarNew.getAlojamientoList().add(alojamiento);
                fkLugarNew = em.merge(fkLugarNew);
            }
            if (fkTipoAlojamientoOld != null && !fkTipoAlojamientoOld.equals(fkTipoAlojamientoNew)) {
                fkTipoAlojamientoOld.getAlojamientoList().remove(alojamiento);
                fkTipoAlojamientoOld = em.merge(fkTipoAlojamientoOld);
            }
            if (fkTipoAlojamientoNew != null && !fkTipoAlojamientoNew.equals(fkTipoAlojamientoOld)) {
                fkTipoAlojamientoNew.getAlojamientoList().add(alojamiento);
                fkTipoAlojamientoNew = em.merge(fkTipoAlojamientoNew);
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Alojamiento oldFkAlojamientoOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getFkAlojamiento();
                    calificacionListNewCalificacion.setFkAlojamiento(alojamiento);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldFkAlojamientoOfCalificacionListNewCalificacion != null && !oldFkAlojamientoOfCalificacionListNewCalificacion.equals(alojamiento)) {
                        oldFkAlojamientoOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldFkAlojamientoOfCalificacionListNewCalificacion = em.merge(oldFkAlojamientoOfCalificacionListNewCalificacion);
                    }
                }
            }
            for (Foto fotoListOldFoto : fotoListOld) {
                if (!fotoListNew.contains(fotoListOldFoto)) {
                    fotoListOldFoto.setFkAlojamiento(null);
                    fotoListOldFoto = em.merge(fotoListOldFoto);
                }
            }
            for (Foto fotoListNewFoto : fotoListNew) {
                if (!fotoListOld.contains(fotoListNewFoto)) {
                    Alojamiento oldFkAlojamientoOfFotoListNewFoto = fotoListNewFoto.getFkAlojamiento();
                    fotoListNewFoto.setFkAlojamiento(alojamiento);
                    fotoListNewFoto = em.merge(fotoListNewFoto);
                    if (oldFkAlojamientoOfFotoListNewFoto != null && !oldFkAlojamientoOfFotoListNewFoto.equals(alojamiento)) {
                        oldFkAlojamientoOfFotoListNewFoto.getFotoList().remove(fotoListNewFoto);
                        oldFkAlojamientoOfFotoListNewFoto = em.merge(oldFkAlojamientoOfFotoListNewFoto);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Alojamiento oldFkAlojamientoOfReservaListNewReserva = reservaListNewReserva.getFkAlojamiento();
                    reservaListNewReserva.setFkAlojamiento(alojamiento);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldFkAlojamientoOfReservaListNewReserva != null && !oldFkAlojamientoOfReservaListNewReserva.equals(alojamiento)) {
                        oldFkAlojamientoOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldFkAlojamientoOfReservaListNewReserva = em.merge(oldFkAlojamientoOfReservaListNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alojamiento.getAloId();
                if (findAlojamiento(id) == null) {
                    throw new NonexistentEntityException("The alojamiento with id " + id + " no longer exists.");
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
            Alojamiento alojamiento;
            try {
                alojamiento = em.getReference(Alojamiento.class, id);
                alojamiento.getAloId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alojamiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionListOrphanCheck = alojamiento.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alojamiento (" + alojamiento + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable fkAlojamiento field.");
            }
            List<Reserva> reservaListOrphanCheck = alojamiento.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alojamiento (" + alojamiento + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable fkAlojamiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Lugar fkLugar = alojamiento.getFkLugar();
            if (fkLugar != null) {
                fkLugar.getAlojamientoList().remove(alojamiento);
                fkLugar = em.merge(fkLugar);
            }
            Tipoalojamiento fkTipoAlojamiento = alojamiento.getFkTipoAlojamiento();
            if (fkTipoAlojamiento != null) {
                fkTipoAlojamiento.getAlojamientoList().remove(alojamiento);
                fkTipoAlojamiento = em.merge(fkTipoAlojamiento);
            }
            List<Foto> fotoList = alojamiento.getFotoList();
            for (Foto fotoListFoto : fotoList) {
                fotoListFoto.setFkAlojamiento(null);
                fotoListFoto = em.merge(fotoListFoto);
            }
            em.remove(alojamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alojamiento> findAlojamientoEntities() {
        return findAlojamientoEntities(true, -1, -1);
    }

    public List<Alojamiento> findAlojamientoEntities(int maxResults, int firstResult) {
        return findAlojamientoEntities(false, maxResults, firstResult);
    }

    private List<Alojamiento> findAlojamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alojamiento.class));
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

    public Alojamiento findAlojamiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alojamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlojamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alojamiento> rt = cq.from(Alojamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
