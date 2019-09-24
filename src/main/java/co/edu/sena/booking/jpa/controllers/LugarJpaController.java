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
import co.edu.sena.booking.jpa.entities.Municipio;
import co.edu.sena.booking.jpa.entities.Tipolugar;
import co.edu.sena.booking.jpa.entities.Foto;
import java.util.ArrayList;
import java.util.List;
import co.edu.sena.booking.jpa.entities.Alojamiento;
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.controllers.exceptions.IllegalOrphanException;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import co.edu.sena.booking.utils.Utils;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class LugarJpaController implements Serializable {

    public LugarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lugar lugar) {
        if (lugar.getFotoList() == null) {
            lugar.setFotoList(new ArrayList<Foto>());
        }
        if (lugar.getAlojamientoList() == null) {
            lugar.setAlojamientoList(new ArrayList<Alojamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio fkMunicipio = lugar.getFkMunicipio();
            if (fkMunicipio != null) {
                fkMunicipio = em.getReference(fkMunicipio.getClass(), fkMunicipio.getMunId());
                lugar.setFkMunicipio(fkMunicipio);
            }
            Tipolugar fkTipoLugar = lugar.getFkTipoLugar();
            if (fkTipoLugar != null) {
                TipolugarJpaController tLugarCtrl = new TipolugarJpaController(Utils.getEM());
                fkTipoLugar = tLugarCtrl.findTipolugar(fkTipoLugar.getTluId());
//                fkTipoLugar = em.getReference(fkTipoLugar.getClass(), fkTipoLugar.getTluId());
                lugar.setFkTipoLugar(fkTipoLugar);
            }
            List<Foto> attachedFotoList = new ArrayList<Foto>();
            for (Foto fotoListFotoToAttach : lugar.getFotoList()) {
                fotoListFotoToAttach = em.getReference(fotoListFotoToAttach.getClass(), fotoListFotoToAttach.getFotId());
                attachedFotoList.add(fotoListFotoToAttach);
            }
            lugar.setFotoList(attachedFotoList);
            List<Alojamiento> attachedAlojamientoList = new ArrayList<Alojamiento>();
            for (Alojamiento alojamientoListAlojamientoToAttach : lugar.getAlojamientoList()) {
                alojamientoListAlojamientoToAttach = em.getReference(alojamientoListAlojamientoToAttach.getClass(), alojamientoListAlojamientoToAttach.getAloId());
                attachedAlojamientoList.add(alojamientoListAlojamientoToAttach);
            }
            lugar.setAlojamientoList(attachedAlojamientoList);
            em.persist(lugar);
            if (fkMunicipio != null) {
                fkMunicipio.getLugarList().add(lugar);
                fkMunicipio = em.merge(fkMunicipio);
            }
            if (fkTipoLugar != null) {
                fkTipoLugar.getLugarList().add(lugar);
                fkTipoLugar = em.merge(fkTipoLugar);
            }
            for (Foto fotoListFoto : lugar.getFotoList()) {
                Lugar oldFkLugarOfFotoListFoto = fotoListFoto.getFkLugar();
                fotoListFoto.setFkLugar(lugar);
                fotoListFoto = em.merge(fotoListFoto);
                if (oldFkLugarOfFotoListFoto != null) {
                    oldFkLugarOfFotoListFoto.getFotoList().remove(fotoListFoto);
                    oldFkLugarOfFotoListFoto = em.merge(oldFkLugarOfFotoListFoto);
                }
            }
            for (Alojamiento alojamientoListAlojamiento : lugar.getAlojamientoList()) {
                Lugar oldFkLugarOfAlojamientoListAlojamiento = alojamientoListAlojamiento.getFkLugar();
                alojamientoListAlojamiento.setFkLugar(lugar);
                alojamientoListAlojamiento = em.merge(alojamientoListAlojamiento);
                if (oldFkLugarOfAlojamientoListAlojamiento != null) {
                    oldFkLugarOfAlojamientoListAlojamiento.getAlojamientoList().remove(alojamientoListAlojamiento);
                    oldFkLugarOfAlojamientoListAlojamiento = em.merge(oldFkLugarOfAlojamientoListAlojamiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lugar lugar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugar persistentLugar = em.find(Lugar.class, lugar.getLugId());
            Municipio fkMunicipioOld = persistentLugar.getFkMunicipio();
            Municipio fkMunicipioNew = lugar.getFkMunicipio();
            Tipolugar fkTipoLugarOld = persistentLugar.getFkTipoLugar();
            Tipolugar fkTipoLugarNew = lugar.getFkTipoLugar();
            List<Foto> fotoListOld = persistentLugar.getFotoList();
            List<Foto> fotoListNew = lugar.getFotoList();
            List<Alojamiento> alojamientoListOld = persistentLugar.getAlojamientoList();
            List<Alojamiento> alojamientoListNew = lugar.getAlojamientoList();
            List<String> illegalOrphanMessages = null;
            for (Alojamiento alojamientoListOldAlojamiento : alojamientoListOld) {
                if (!alojamientoListNew.contains(alojamientoListOldAlojamiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alojamiento " + alojamientoListOldAlojamiento + " since its fkLugar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkMunicipioNew != null) {
                fkMunicipioNew = em.getReference(fkMunicipioNew.getClass(), fkMunicipioNew.getMunId());
                lugar.setFkMunicipio(fkMunicipioNew);
            }
            if (fkTipoLugarNew != null) {
                fkTipoLugarNew = em.getReference(fkTipoLugarNew.getClass(), fkTipoLugarNew.getTluId());
                lugar.setFkTipoLugar(fkTipoLugarNew);
            }
            List<Foto> attachedFotoListNew = new ArrayList<Foto>();
            for (Foto fotoListNewFotoToAttach : fotoListNew) {
                fotoListNewFotoToAttach = em.getReference(fotoListNewFotoToAttach.getClass(), fotoListNewFotoToAttach.getFotId());
                attachedFotoListNew.add(fotoListNewFotoToAttach);
            }
            fotoListNew = attachedFotoListNew;
            lugar.setFotoList(fotoListNew);
            List<Alojamiento> attachedAlojamientoListNew = new ArrayList<Alojamiento>();
            for (Alojamiento alojamientoListNewAlojamientoToAttach : alojamientoListNew) {
                alojamientoListNewAlojamientoToAttach = em.getReference(alojamientoListNewAlojamientoToAttach.getClass(), alojamientoListNewAlojamientoToAttach.getAloId());
                attachedAlojamientoListNew.add(alojamientoListNewAlojamientoToAttach);
            }
            alojamientoListNew = attachedAlojamientoListNew;
            lugar.setAlojamientoList(alojamientoListNew);
            lugar = em.merge(lugar);
            if (fkMunicipioOld != null && !fkMunicipioOld.equals(fkMunicipioNew)) {
                fkMunicipioOld.getLugarList().remove(lugar);
                fkMunicipioOld = em.merge(fkMunicipioOld);
            }
            if (fkMunicipioNew != null && !fkMunicipioNew.equals(fkMunicipioOld)) {
                fkMunicipioNew.getLugarList().add(lugar);
                fkMunicipioNew = em.merge(fkMunicipioNew);
            }
            if (fkTipoLugarOld != null && !fkTipoLugarOld.equals(fkTipoLugarNew)) {
                fkTipoLugarOld.getLugarList().remove(lugar);
                fkTipoLugarOld = em.merge(fkTipoLugarOld);
            }
            if (fkTipoLugarNew != null && !fkTipoLugarNew.equals(fkTipoLugarOld)) {
                fkTipoLugarNew.getLugarList().add(lugar);
                fkTipoLugarNew = em.merge(fkTipoLugarNew);
            }
            for (Foto fotoListOldFoto : fotoListOld) {
                if (!fotoListNew.contains(fotoListOldFoto)) {
                    fotoListOldFoto.setFkLugar(null);
                    fotoListOldFoto = em.merge(fotoListOldFoto);
                }
            }
            for (Foto fotoListNewFoto : fotoListNew) {
                if (!fotoListOld.contains(fotoListNewFoto)) {
                    Lugar oldFkLugarOfFotoListNewFoto = fotoListNewFoto.getFkLugar();
                    fotoListNewFoto.setFkLugar(lugar);
                    fotoListNewFoto = em.merge(fotoListNewFoto);
                    if (oldFkLugarOfFotoListNewFoto != null && !oldFkLugarOfFotoListNewFoto.equals(lugar)) {
                        oldFkLugarOfFotoListNewFoto.getFotoList().remove(fotoListNewFoto);
                        oldFkLugarOfFotoListNewFoto = em.merge(oldFkLugarOfFotoListNewFoto);
                    }
                }
            }
            for (Alojamiento alojamientoListNewAlojamiento : alojamientoListNew) {
                if (!alojamientoListOld.contains(alojamientoListNewAlojamiento)) {
                    Lugar oldFkLugarOfAlojamientoListNewAlojamiento = alojamientoListNewAlojamiento.getFkLugar();
                    alojamientoListNewAlojamiento.setFkLugar(lugar);
                    alojamientoListNewAlojamiento = em.merge(alojamientoListNewAlojamiento);
                    if (oldFkLugarOfAlojamientoListNewAlojamiento != null && !oldFkLugarOfAlojamientoListNewAlojamiento.equals(lugar)) {
                        oldFkLugarOfAlojamientoListNewAlojamiento.getAlojamientoList().remove(alojamientoListNewAlojamiento);
                        oldFkLugarOfAlojamientoListNewAlojamiento = em.merge(oldFkLugarOfAlojamientoListNewAlojamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lugar.getLugId();
                if (findLugar(id) == null) {
                    throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.");
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
            Lugar lugar;
            try {
                lugar = em.getReference(Lugar.class, id);
                lugar.getLugId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Alojamiento> alojamientoListOrphanCheck = lugar.getAlojamientoList();
            for (Alojamiento alojamientoListOrphanCheckAlojamiento : alojamientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lugar (" + lugar + ") cannot be destroyed since the Alojamiento " + alojamientoListOrphanCheckAlojamiento + " in its alojamientoList field has a non-nullable fkLugar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Municipio fkMunicipio = lugar.getFkMunicipio();
            if (fkMunicipio != null) {
                fkMunicipio.getLugarList().remove(lugar);
                fkMunicipio = em.merge(fkMunicipio);
            }
            Tipolugar fkTipoLugar = lugar.getFkTipoLugar();
            if (fkTipoLugar != null) {
                fkTipoLugar.getLugarList().remove(lugar);
                fkTipoLugar = em.merge(fkTipoLugar);
            }
            List<Foto> fotoList = lugar.getFotoList();
            for (Foto fotoListFoto : fotoList) {
                fotoListFoto.setFkLugar(null);
                fotoListFoto = em.merge(fotoListFoto);
            }
            em.remove(lugar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lugar> findLugarEntities() {
        return findLugarEntities(true, -1, -1);
    }

    public List<Lugar> findLugarEntities(int maxResults, int firstResult) {
        return findLugarEntities(false, maxResults, firstResult);
    }

    private List<Lugar> findLugarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lugar.class));
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

    public Lugar findLugar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lugar.class, id);
        } finally {
            em.close();
        }
    }

    public int getLugarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lugar> rt = cq.from(Lugar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
