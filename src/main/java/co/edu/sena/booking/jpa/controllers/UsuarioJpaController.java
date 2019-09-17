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
import co.edu.sena.booking.jpa.entities.Tipoidentificacion;
import co.edu.sena.booking.jpa.entities.Calificacion;
import java.util.ArrayList;
import java.util.List;
import co.edu.sena.booking.jpa.entities.RolUsuario;
import co.edu.sena.booking.jpa.entities.ReservaCliente;
import co.edu.sena.booking.jpa.entities.Reserva;
import co.edu.sena.booking.jpa.entities.Usuario;
import co.edu.sena.booking.jpa.controllers.exceptions.IllegalOrphanException;
import co.edu.sena.booking.jpa.controllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getCalificacionList() == null) {
            usuario.setCalificacionList(new ArrayList<Calificacion>());
        }
        if (usuario.getRolUsuarioList() == null) {
            usuario.setRolUsuarioList(new ArrayList<RolUsuario>());
        }
        if (usuario.getReservaClienteList() == null) {
            usuario.setReservaClienteList(new ArrayList<ReservaCliente>());
        }
        if (usuario.getReservaList() == null) {
            usuario.setReservaList(new ArrayList<Reserva>());
        }
        if (usuario.getReservaList1() == null) {
            usuario.setReservaList1(new ArrayList<Reserva>());
        }
        if (usuario.getReservaList2() == null) {
            usuario.setReservaList2(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoidentificacion fkTipoIdentificacion = usuario.getFkTipoIdentificacion();
            if (fkTipoIdentificacion != null) {
                fkTipoIdentificacion = em.getReference(fkTipoIdentificacion.getClass(), fkTipoIdentificacion.getTidId());
                usuario.setFkTipoIdentificacion(fkTipoIdentificacion);
            }
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : usuario.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalId());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            usuario.setCalificacionList(attachedCalificacionList);
            List<RolUsuario> attachedRolUsuarioList = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListRolUsuarioToAttach : usuario.getRolUsuarioList()) {
                rolUsuarioListRolUsuarioToAttach = em.getReference(rolUsuarioListRolUsuarioToAttach.getClass(), rolUsuarioListRolUsuarioToAttach.getRuId());
                attachedRolUsuarioList.add(rolUsuarioListRolUsuarioToAttach);
            }
            usuario.setRolUsuarioList(attachedRolUsuarioList);
            List<ReservaCliente> attachedReservaClienteList = new ArrayList<ReservaCliente>();
            for (ReservaCliente reservaClienteListReservaClienteToAttach : usuario.getReservaClienteList()) {
                reservaClienteListReservaClienteToAttach = em.getReference(reservaClienteListReservaClienteToAttach.getClass(), reservaClienteListReservaClienteToAttach.getRuId());
                attachedReservaClienteList.add(reservaClienteListReservaClienteToAttach);
            }
            usuario.setReservaClienteList(attachedReservaClienteList);
            List<Reserva> attachedReservaList = new ArrayList<Reserva>();
            for (Reserva reservaListReservaToAttach : usuario.getReservaList()) {
                reservaListReservaToAttach = em.getReference(reservaListReservaToAttach.getClass(), reservaListReservaToAttach.getResId());
                attachedReservaList.add(reservaListReservaToAttach);
            }
            usuario.setReservaList(attachedReservaList);
            List<Reserva> attachedReservaList1 = new ArrayList<Reserva>();
            for (Reserva reservaList1ReservaToAttach : usuario.getReservaList1()) {
                reservaList1ReservaToAttach = em.getReference(reservaList1ReservaToAttach.getClass(), reservaList1ReservaToAttach.getResId());
                attachedReservaList1.add(reservaList1ReservaToAttach);
            }
            usuario.setReservaList1(attachedReservaList1);
            List<Reserva> attachedReservaList2 = new ArrayList<Reserva>();
            for (Reserva reservaList2ReservaToAttach : usuario.getReservaList2()) {
                reservaList2ReservaToAttach = em.getReference(reservaList2ReservaToAttach.getClass(), reservaList2ReservaToAttach.getResId());
                attachedReservaList2.add(reservaList2ReservaToAttach);
            }
            usuario.setReservaList2(attachedReservaList2);
            em.persist(usuario);
            if (fkTipoIdentificacion != null) {
                fkTipoIdentificacion.getUsuarioList().add(usuario);
                fkTipoIdentificacion = em.merge(fkTipoIdentificacion);
            }
            for (Calificacion calificacionListCalificacion : usuario.getCalificacionList()) {
                Usuario oldFkUsuarioOfCalificacionListCalificacion = calificacionListCalificacion.getFkUsuario();
                calificacionListCalificacion.setFkUsuario(usuario);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldFkUsuarioOfCalificacionListCalificacion != null) {
                    oldFkUsuarioOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldFkUsuarioOfCalificacionListCalificacion = em.merge(oldFkUsuarioOfCalificacionListCalificacion);
                }
            }
            for (RolUsuario rolUsuarioListRolUsuario : usuario.getRolUsuarioList()) {
                Usuario oldFkUsuarioOfRolUsuarioListRolUsuario = rolUsuarioListRolUsuario.getFkUsuario();
                rolUsuarioListRolUsuario.setFkUsuario(usuario);
                rolUsuarioListRolUsuario = em.merge(rolUsuarioListRolUsuario);
                if (oldFkUsuarioOfRolUsuarioListRolUsuario != null) {
                    oldFkUsuarioOfRolUsuarioListRolUsuario.getRolUsuarioList().remove(rolUsuarioListRolUsuario);
                    oldFkUsuarioOfRolUsuarioListRolUsuario = em.merge(oldFkUsuarioOfRolUsuarioListRolUsuario);
                }
            }
            for (ReservaCliente reservaClienteListReservaCliente : usuario.getReservaClienteList()) {
                Usuario oldFkClienteOfReservaClienteListReservaCliente = reservaClienteListReservaCliente.getFkCliente();
                reservaClienteListReservaCliente.setFkCliente(usuario);
                reservaClienteListReservaCliente = em.merge(reservaClienteListReservaCliente);
                if (oldFkClienteOfReservaClienteListReservaCliente != null) {
                    oldFkClienteOfReservaClienteListReservaCliente.getReservaClienteList().remove(reservaClienteListReservaCliente);
                    oldFkClienteOfReservaClienteListReservaCliente = em.merge(oldFkClienteOfReservaClienteListReservaCliente);
                }
            }
            for (Reserva reservaListReserva : usuario.getReservaList()) {
                Usuario oldFkClienteOfReservaListReserva = reservaListReserva.getFkCliente();
                reservaListReserva.setFkCliente(usuario);
                reservaListReserva = em.merge(reservaListReserva);
                if (oldFkClienteOfReservaListReserva != null) {
                    oldFkClienteOfReservaListReserva.getReservaList().remove(reservaListReserva);
                    oldFkClienteOfReservaListReserva = em.merge(oldFkClienteOfReservaListReserva);
                }
            }
            for (Reserva reservaList1Reserva : usuario.getReservaList1()) {
                Usuario oldFkUsuarioCheckingOfReservaList1Reserva = reservaList1Reserva.getFkUsuarioChecking();
                reservaList1Reserva.setFkUsuarioChecking(usuario);
                reservaList1Reserva = em.merge(reservaList1Reserva);
                if (oldFkUsuarioCheckingOfReservaList1Reserva != null) {
                    oldFkUsuarioCheckingOfReservaList1Reserva.getReservaList1().remove(reservaList1Reserva);
                    oldFkUsuarioCheckingOfReservaList1Reserva = em.merge(oldFkUsuarioCheckingOfReservaList1Reserva);
                }
            }
            for (Reserva reservaList2Reserva : usuario.getReservaList2()) {
                Usuario oldFkUsuarioCheckoutOfReservaList2Reserva = reservaList2Reserva.getFkUsuarioCheckout();
                reservaList2Reserva.setFkUsuarioCheckout(usuario);
                reservaList2Reserva = em.merge(reservaList2Reserva);
                if (oldFkUsuarioCheckoutOfReservaList2Reserva != null) {
                    oldFkUsuarioCheckoutOfReservaList2Reserva.getReservaList2().remove(reservaList2Reserva);
                    oldFkUsuarioCheckoutOfReservaList2Reserva = em.merge(oldFkUsuarioCheckoutOfReservaList2Reserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuId());
            Tipoidentificacion fkTipoIdentificacionOld = persistentUsuario.getFkTipoIdentificacion();
            Tipoidentificacion fkTipoIdentificacionNew = usuario.getFkTipoIdentificacion();
            List<Calificacion> calificacionListOld = persistentUsuario.getCalificacionList();
            List<Calificacion> calificacionListNew = usuario.getCalificacionList();
            List<RolUsuario> rolUsuarioListOld = persistentUsuario.getRolUsuarioList();
            List<RolUsuario> rolUsuarioListNew = usuario.getRolUsuarioList();
            List<ReservaCliente> reservaClienteListOld = persistentUsuario.getReservaClienteList();
            List<ReservaCliente> reservaClienteListNew = usuario.getReservaClienteList();
            List<Reserva> reservaListOld = persistentUsuario.getReservaList();
            List<Reserva> reservaListNew = usuario.getReservaList();
            List<Reserva> reservaList1Old = persistentUsuario.getReservaList1();
            List<Reserva> reservaList1New = usuario.getReservaList1();
            List<Reserva> reservaList2Old = persistentUsuario.getReservaList2();
            List<Reserva> reservaList2New = usuario.getReservaList2();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its fkUsuario field is not nullable.");
                }
            }
            for (RolUsuario rolUsuarioListOldRolUsuario : rolUsuarioListOld) {
                if (!rolUsuarioListNew.contains(rolUsuarioListOldRolUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolUsuario " + rolUsuarioListOldRolUsuario + " since its fkUsuario field is not nullable.");
                }
            }
            for (ReservaCliente reservaClienteListOldReservaCliente : reservaClienteListOld) {
                if (!reservaClienteListNew.contains(reservaClienteListOldReservaCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReservaCliente " + reservaClienteListOldReservaCliente + " since its fkCliente field is not nullable.");
                }
            }
            for (Reserva reservaListOldReserva : reservaListOld) {
                if (!reservaListNew.contains(reservaListOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaListOldReserva + " since its fkCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkTipoIdentificacionNew != null) {
                fkTipoIdentificacionNew = em.getReference(fkTipoIdentificacionNew.getClass(), fkTipoIdentificacionNew.getTidId());
                usuario.setFkTipoIdentificacion(fkTipoIdentificacionNew);
            }
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalId());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            usuario.setCalificacionList(calificacionListNew);
            List<RolUsuario> attachedRolUsuarioListNew = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListNewRolUsuarioToAttach : rolUsuarioListNew) {
                rolUsuarioListNewRolUsuarioToAttach = em.getReference(rolUsuarioListNewRolUsuarioToAttach.getClass(), rolUsuarioListNewRolUsuarioToAttach.getRuId());
                attachedRolUsuarioListNew.add(rolUsuarioListNewRolUsuarioToAttach);
            }
            rolUsuarioListNew = attachedRolUsuarioListNew;
            usuario.setRolUsuarioList(rolUsuarioListNew);
            List<ReservaCliente> attachedReservaClienteListNew = new ArrayList<ReservaCliente>();
            for (ReservaCliente reservaClienteListNewReservaClienteToAttach : reservaClienteListNew) {
                reservaClienteListNewReservaClienteToAttach = em.getReference(reservaClienteListNewReservaClienteToAttach.getClass(), reservaClienteListNewReservaClienteToAttach.getRuId());
                attachedReservaClienteListNew.add(reservaClienteListNewReservaClienteToAttach);
            }
            reservaClienteListNew = attachedReservaClienteListNew;
            usuario.setReservaClienteList(reservaClienteListNew);
            List<Reserva> attachedReservaListNew = new ArrayList<Reserva>();
            for (Reserva reservaListNewReservaToAttach : reservaListNew) {
                reservaListNewReservaToAttach = em.getReference(reservaListNewReservaToAttach.getClass(), reservaListNewReservaToAttach.getResId());
                attachedReservaListNew.add(reservaListNewReservaToAttach);
            }
            reservaListNew = attachedReservaListNew;
            usuario.setReservaList(reservaListNew);
            List<Reserva> attachedReservaList1New = new ArrayList<Reserva>();
            for (Reserva reservaList1NewReservaToAttach : reservaList1New) {
                reservaList1NewReservaToAttach = em.getReference(reservaList1NewReservaToAttach.getClass(), reservaList1NewReservaToAttach.getResId());
                attachedReservaList1New.add(reservaList1NewReservaToAttach);
            }
            reservaList1New = attachedReservaList1New;
            usuario.setReservaList1(reservaList1New);
            List<Reserva> attachedReservaList2New = new ArrayList<Reserva>();
            for (Reserva reservaList2NewReservaToAttach : reservaList2New) {
                reservaList2NewReservaToAttach = em.getReference(reservaList2NewReservaToAttach.getClass(), reservaList2NewReservaToAttach.getResId());
                attachedReservaList2New.add(reservaList2NewReservaToAttach);
            }
            reservaList2New = attachedReservaList2New;
            usuario.setReservaList2(reservaList2New);
            usuario = em.merge(usuario);
            if (fkTipoIdentificacionOld != null && !fkTipoIdentificacionOld.equals(fkTipoIdentificacionNew)) {
                fkTipoIdentificacionOld.getUsuarioList().remove(usuario);
                fkTipoIdentificacionOld = em.merge(fkTipoIdentificacionOld);
            }
            if (fkTipoIdentificacionNew != null && !fkTipoIdentificacionNew.equals(fkTipoIdentificacionOld)) {
                fkTipoIdentificacionNew.getUsuarioList().add(usuario);
                fkTipoIdentificacionNew = em.merge(fkTipoIdentificacionNew);
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Usuario oldFkUsuarioOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getFkUsuario();
                    calificacionListNewCalificacion.setFkUsuario(usuario);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldFkUsuarioOfCalificacionListNewCalificacion != null && !oldFkUsuarioOfCalificacionListNewCalificacion.equals(usuario)) {
                        oldFkUsuarioOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldFkUsuarioOfCalificacionListNewCalificacion = em.merge(oldFkUsuarioOfCalificacionListNewCalificacion);
                    }
                }
            }
            for (RolUsuario rolUsuarioListNewRolUsuario : rolUsuarioListNew) {
                if (!rolUsuarioListOld.contains(rolUsuarioListNewRolUsuario)) {
                    Usuario oldFkUsuarioOfRolUsuarioListNewRolUsuario = rolUsuarioListNewRolUsuario.getFkUsuario();
                    rolUsuarioListNewRolUsuario.setFkUsuario(usuario);
                    rolUsuarioListNewRolUsuario = em.merge(rolUsuarioListNewRolUsuario);
                    if (oldFkUsuarioOfRolUsuarioListNewRolUsuario != null && !oldFkUsuarioOfRolUsuarioListNewRolUsuario.equals(usuario)) {
                        oldFkUsuarioOfRolUsuarioListNewRolUsuario.getRolUsuarioList().remove(rolUsuarioListNewRolUsuario);
                        oldFkUsuarioOfRolUsuarioListNewRolUsuario = em.merge(oldFkUsuarioOfRolUsuarioListNewRolUsuario);
                    }
                }
            }
            for (ReservaCliente reservaClienteListNewReservaCliente : reservaClienteListNew) {
                if (!reservaClienteListOld.contains(reservaClienteListNewReservaCliente)) {
                    Usuario oldFkClienteOfReservaClienteListNewReservaCliente = reservaClienteListNewReservaCliente.getFkCliente();
                    reservaClienteListNewReservaCliente.setFkCliente(usuario);
                    reservaClienteListNewReservaCliente = em.merge(reservaClienteListNewReservaCliente);
                    if (oldFkClienteOfReservaClienteListNewReservaCliente != null && !oldFkClienteOfReservaClienteListNewReservaCliente.equals(usuario)) {
                        oldFkClienteOfReservaClienteListNewReservaCliente.getReservaClienteList().remove(reservaClienteListNewReservaCliente);
                        oldFkClienteOfReservaClienteListNewReservaCliente = em.merge(oldFkClienteOfReservaClienteListNewReservaCliente);
                    }
                }
            }
            for (Reserva reservaListNewReserva : reservaListNew) {
                if (!reservaListOld.contains(reservaListNewReserva)) {
                    Usuario oldFkClienteOfReservaListNewReserva = reservaListNewReserva.getFkCliente();
                    reservaListNewReserva.setFkCliente(usuario);
                    reservaListNewReserva = em.merge(reservaListNewReserva);
                    if (oldFkClienteOfReservaListNewReserva != null && !oldFkClienteOfReservaListNewReserva.equals(usuario)) {
                        oldFkClienteOfReservaListNewReserva.getReservaList().remove(reservaListNewReserva);
                        oldFkClienteOfReservaListNewReserva = em.merge(oldFkClienteOfReservaListNewReserva);
                    }
                }
            }
            for (Reserva reservaList1OldReserva : reservaList1Old) {
                if (!reservaList1New.contains(reservaList1OldReserva)) {
                    reservaList1OldReserva.setFkUsuarioChecking(null);
                    reservaList1OldReserva = em.merge(reservaList1OldReserva);
                }
            }
            for (Reserva reservaList1NewReserva : reservaList1New) {
                if (!reservaList1Old.contains(reservaList1NewReserva)) {
                    Usuario oldFkUsuarioCheckingOfReservaList1NewReserva = reservaList1NewReserva.getFkUsuarioChecking();
                    reservaList1NewReserva.setFkUsuarioChecking(usuario);
                    reservaList1NewReserva = em.merge(reservaList1NewReserva);
                    if (oldFkUsuarioCheckingOfReservaList1NewReserva != null && !oldFkUsuarioCheckingOfReservaList1NewReserva.equals(usuario)) {
                        oldFkUsuarioCheckingOfReservaList1NewReserva.getReservaList1().remove(reservaList1NewReserva);
                        oldFkUsuarioCheckingOfReservaList1NewReserva = em.merge(oldFkUsuarioCheckingOfReservaList1NewReserva);
                    }
                }
            }
            for (Reserva reservaList2OldReserva : reservaList2Old) {
                if (!reservaList2New.contains(reservaList2OldReserva)) {
                    reservaList2OldReserva.setFkUsuarioCheckout(null);
                    reservaList2OldReserva = em.merge(reservaList2OldReserva);
                }
            }
            for (Reserva reservaList2NewReserva : reservaList2New) {
                if (!reservaList2Old.contains(reservaList2NewReserva)) {
                    Usuario oldFkUsuarioCheckoutOfReservaList2NewReserva = reservaList2NewReserva.getFkUsuarioCheckout();
                    reservaList2NewReserva.setFkUsuarioCheckout(usuario);
                    reservaList2NewReserva = em.merge(reservaList2NewReserva);
                    if (oldFkUsuarioCheckoutOfReservaList2NewReserva != null && !oldFkUsuarioCheckoutOfReservaList2NewReserva.equals(usuario)) {
                        oldFkUsuarioCheckoutOfReservaList2NewReserva.getReservaList2().remove(reservaList2NewReserva);
                        oldFkUsuarioCheckoutOfReservaList2NewReserva = em.merge(oldFkUsuarioCheckoutOfReservaList2NewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionListOrphanCheck = usuario.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable fkUsuario field.");
            }
            List<RolUsuario> rolUsuarioListOrphanCheck = usuario.getRolUsuarioList();
            for (RolUsuario rolUsuarioListOrphanCheckRolUsuario : rolUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the RolUsuario " + rolUsuarioListOrphanCheckRolUsuario + " in its rolUsuarioList field has a non-nullable fkUsuario field.");
            }
            List<ReservaCliente> reservaClienteListOrphanCheck = usuario.getReservaClienteList();
            for (ReservaCliente reservaClienteListOrphanCheckReservaCliente : reservaClienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ReservaCliente " + reservaClienteListOrphanCheckReservaCliente + " in its reservaClienteList field has a non-nullable fkCliente field.");
            }
            List<Reserva> reservaListOrphanCheck = usuario.getReservaList();
            for (Reserva reservaListOrphanCheckReserva : reservaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Reserva " + reservaListOrphanCheckReserva + " in its reservaList field has a non-nullable fkCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipoidentificacion fkTipoIdentificacion = usuario.getFkTipoIdentificacion();
            if (fkTipoIdentificacion != null) {
                fkTipoIdentificacion.getUsuarioList().remove(usuario);
                fkTipoIdentificacion = em.merge(fkTipoIdentificacion);
            }
            List<Reserva> reservaList1 = usuario.getReservaList1();
            for (Reserva reservaList1Reserva : reservaList1) {
                reservaList1Reserva.setFkUsuarioChecking(null);
                reservaList1Reserva = em.merge(reservaList1Reserva);
            }
            List<Reserva> reservaList2 = usuario.getReservaList2();
            for (Reserva reservaList2Reserva : reservaList2) {
                reservaList2Reserva.setFkUsuarioCheckout(null);
                reservaList2Reserva = em.merge(reservaList2Reserva);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
