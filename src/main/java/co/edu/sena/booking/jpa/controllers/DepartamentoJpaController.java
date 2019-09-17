/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.booking.jpa.controllers;

import co.edu.sena.booking.jpa.entities.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.edu.sena.booking.jpa.entities.Pais;
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
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getMunicipioList() == null) {
            departamento.setMunicipioList(new ArrayList<Municipio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais fkPais = departamento.getFkPais();
            if (fkPais != null) {
                fkPais = em.getReference(fkPais.getClass(), fkPais.getPaiId());
                departamento.setFkPais(fkPais);
            }
            List<Municipio> attachedMunicipioList = new ArrayList<Municipio>();
            for (Municipio municipioListMunicipioToAttach : departamento.getMunicipioList()) {
                municipioListMunicipioToAttach = em.getReference(municipioListMunicipioToAttach.getClass(), municipioListMunicipioToAttach.getMunId());
                attachedMunicipioList.add(municipioListMunicipioToAttach);
            }
            departamento.setMunicipioList(attachedMunicipioList);
            em.persist(departamento);
            if (fkPais != null) {
                fkPais.getDepartamentoList().add(departamento);
                fkPais = em.merge(fkPais);
            }
            for (Municipio municipioListMunicipio : departamento.getMunicipioList()) {
                Departamento oldFkDepartamentoOfMunicipioListMunicipio = municipioListMunicipio.getFkDepartamento();
                municipioListMunicipio.setFkDepartamento(departamento);
                municipioListMunicipio = em.merge(municipioListMunicipio);
                if (oldFkDepartamentoOfMunicipioListMunicipio != null) {
                    oldFkDepartamentoOfMunicipioListMunicipio.getMunicipioList().remove(municipioListMunicipio);
                    oldFkDepartamentoOfMunicipioListMunicipio = em.merge(oldFkDepartamentoOfMunicipioListMunicipio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getDepId());
            Pais fkPaisOld = persistentDepartamento.getFkPais();
            Pais fkPaisNew = departamento.getFkPais();
            List<Municipio> municipioListOld = persistentDepartamento.getMunicipioList();
            List<Municipio> municipioListNew = departamento.getMunicipioList();
            List<String> illegalOrphanMessages = null;
            for (Municipio municipioListOldMunicipio : municipioListOld) {
                if (!municipioListNew.contains(municipioListOldMunicipio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Municipio " + municipioListOldMunicipio + " since its fkDepartamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkPaisNew != null) {
                fkPaisNew = em.getReference(fkPaisNew.getClass(), fkPaisNew.getPaiId());
                departamento.setFkPais(fkPaisNew);
            }
            List<Municipio> attachedMunicipioListNew = new ArrayList<Municipio>();
            for (Municipio municipioListNewMunicipioToAttach : municipioListNew) {
                municipioListNewMunicipioToAttach = em.getReference(municipioListNewMunicipioToAttach.getClass(), municipioListNewMunicipioToAttach.getMunId());
                attachedMunicipioListNew.add(municipioListNewMunicipioToAttach);
            }
            municipioListNew = attachedMunicipioListNew;
            departamento.setMunicipioList(municipioListNew);
            departamento = em.merge(departamento);
            if (fkPaisOld != null && !fkPaisOld.equals(fkPaisNew)) {
                fkPaisOld.getDepartamentoList().remove(departamento);
                fkPaisOld = em.merge(fkPaisOld);
            }
            if (fkPaisNew != null && !fkPaisNew.equals(fkPaisOld)) {
                fkPaisNew.getDepartamentoList().add(departamento);
                fkPaisNew = em.merge(fkPaisNew);
            }
            for (Municipio municipioListNewMunicipio : municipioListNew) {
                if (!municipioListOld.contains(municipioListNewMunicipio)) {
                    Departamento oldFkDepartamentoOfMunicipioListNewMunicipio = municipioListNewMunicipio.getFkDepartamento();
                    municipioListNewMunicipio.setFkDepartamento(departamento);
                    municipioListNewMunicipio = em.merge(municipioListNewMunicipio);
                    if (oldFkDepartamentoOfMunicipioListNewMunicipio != null && !oldFkDepartamentoOfMunicipioListNewMunicipio.equals(departamento)) {
                        oldFkDepartamentoOfMunicipioListNewMunicipio.getMunicipioList().remove(municipioListNewMunicipio);
                        oldFkDepartamentoOfMunicipioListNewMunicipio = em.merge(oldFkDepartamentoOfMunicipioListNewMunicipio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getDepId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getDepId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Municipio> municipioListOrphanCheck = departamento.getMunicipioList();
            for (Municipio municipioListOrphanCheckMunicipio : municipioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Municipio " + municipioListOrphanCheckMunicipio + " in its municipioList field has a non-nullable fkDepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais fkPais = departamento.getFkPais();
            if (fkPais != null) {
                fkPais.getDepartamentoList().remove(departamento);
                fkPais = em.merge(fkPais);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
