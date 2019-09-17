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
import co.edu.sena.booking.jpa.entities.Departamento;
import co.edu.sena.booking.jpa.entities.Lugar;
import co.edu.sena.booking.jpa.entities.Municipio;
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
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) {
        if (municipio.getLugarList() == null) {
            municipio.setLugarList(new ArrayList<Lugar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento fkDepartamento = municipio.getFkDepartamento();
            if (fkDepartamento != null) {
                fkDepartamento = em.getReference(fkDepartamento.getClass(), fkDepartamento.getDepId());
                municipio.setFkDepartamento(fkDepartamento);
            }
            List<Lugar> attachedLugarList = new ArrayList<Lugar>();
            for (Lugar lugarListLugarToAttach : municipio.getLugarList()) {
                lugarListLugarToAttach = em.getReference(lugarListLugarToAttach.getClass(), lugarListLugarToAttach.getLugId());
                attachedLugarList.add(lugarListLugarToAttach);
            }
            municipio.setLugarList(attachedLugarList);
            em.persist(municipio);
            if (fkDepartamento != null) {
                fkDepartamento.getMunicipioList().add(municipio);
                fkDepartamento = em.merge(fkDepartamento);
            }
            for (Lugar lugarListLugar : municipio.getLugarList()) {
                Municipio oldFkMunicipioOfLugarListLugar = lugarListLugar.getFkMunicipio();
                lugarListLugar.setFkMunicipio(municipio);
                lugarListLugar = em.merge(lugarListLugar);
                if (oldFkMunicipioOfLugarListLugar != null) {
                    oldFkMunicipioOfLugarListLugar.getLugarList().remove(lugarListLugar);
                    oldFkMunicipioOfLugarListLugar = em.merge(oldFkMunicipioOfLugarListLugar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getMunId());
            Departamento fkDepartamentoOld = persistentMunicipio.getFkDepartamento();
            Departamento fkDepartamentoNew = municipio.getFkDepartamento();
            List<Lugar> lugarListOld = persistentMunicipio.getLugarList();
            List<Lugar> lugarListNew = municipio.getLugarList();
            List<String> illegalOrphanMessages = null;
            for (Lugar lugarListOldLugar : lugarListOld) {
                if (!lugarListNew.contains(lugarListOldLugar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lugar " + lugarListOldLugar + " since its fkMunicipio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkDepartamentoNew != null) {
                fkDepartamentoNew = em.getReference(fkDepartamentoNew.getClass(), fkDepartamentoNew.getDepId());
                municipio.setFkDepartamento(fkDepartamentoNew);
            }
            List<Lugar> attachedLugarListNew = new ArrayList<Lugar>();
            for (Lugar lugarListNewLugarToAttach : lugarListNew) {
                lugarListNewLugarToAttach = em.getReference(lugarListNewLugarToAttach.getClass(), lugarListNewLugarToAttach.getLugId());
                attachedLugarListNew.add(lugarListNewLugarToAttach);
            }
            lugarListNew = attachedLugarListNew;
            municipio.setLugarList(lugarListNew);
            municipio = em.merge(municipio);
            if (fkDepartamentoOld != null && !fkDepartamentoOld.equals(fkDepartamentoNew)) {
                fkDepartamentoOld.getMunicipioList().remove(municipio);
                fkDepartamentoOld = em.merge(fkDepartamentoOld);
            }
            if (fkDepartamentoNew != null && !fkDepartamentoNew.equals(fkDepartamentoOld)) {
                fkDepartamentoNew.getMunicipioList().add(municipio);
                fkDepartamentoNew = em.merge(fkDepartamentoNew);
            }
            for (Lugar lugarListNewLugar : lugarListNew) {
                if (!lugarListOld.contains(lugarListNewLugar)) {
                    Municipio oldFkMunicipioOfLugarListNewLugar = lugarListNewLugar.getFkMunicipio();
                    lugarListNewLugar.setFkMunicipio(municipio);
                    lugarListNewLugar = em.merge(lugarListNewLugar);
                    if (oldFkMunicipioOfLugarListNewLugar != null && !oldFkMunicipioOfLugarListNewLugar.equals(municipio)) {
                        oldFkMunicipioOfLugarListNewLugar.getLugarList().remove(lugarListNewLugar);
                        oldFkMunicipioOfLugarListNewLugar = em.merge(oldFkMunicipioOfLugarListNewLugar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getMunId();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getMunId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lugar> lugarListOrphanCheck = municipio.getLugarList();
            for (Lugar lugarListOrphanCheckLugar : lugarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the Lugar " + lugarListOrphanCheckLugar + " in its lugarList field has a non-nullable fkMunicipio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento fkDepartamento = municipio.getFkDepartamento();
            if (fkDepartamento != null) {
                fkDepartamento.getMunicipioList().remove(municipio);
                fkDepartamento = em.merge(fkDepartamento);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
