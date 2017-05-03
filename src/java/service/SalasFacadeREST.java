/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Salas;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Usuario
 */
@Stateless
@Path("/salas")
public class SalasFacadeREST extends AbstractFacade<Salas> {
    @PersistenceContext(unitName = "PeliculasSalaWSPU")
    private EntityManager em;

    public SalasFacadeREST() {
        super(Salas.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Salas entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Salas entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Salas find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Salas> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Salas> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/salas/{pelicula}")
    @Produces({"application/xml","application/json"})
    public String getSala(@PathParam("pelicula")String nombre){
        try{
            List <Salas> sala =  em.createQuery("SELECT s FROM Salas s WHERE s.pelicula = :pelicula")
                    .setParameter("pelicula",nombre).getResultList();
            String result="";
            for (Salas sala1 : sala) {
                result+=sala1.getSala()+ " ";
            }
            return result;
            
        }catch (NoResultException ex){
            throw new WebApplicationException(new Throwable("Falla"));
        }
    }
    
    @GET
    @Path("/allSalas")
    @Produces({"application/xml","application/json"})
    public String getAllSala(){
        try{
            List <Salas> sala =  em.createQuery("SELECT s FROM Salas s").getResultList();
            String result="";
            for (Salas sala1 : sala) {
                result+=sala1.getSala()+ ". ";
            }
            return result;
            
        }catch (NoResultException ex){
            throw new WebApplicationException(new Throwable("Falla"));
        }
    }

    @GET
    @Path("/pelicula/{sala}")
    @Produces({"application/xml","application/json"})
    public String getPelicula(@PathParam("sala")String nombre){
        try{
            Salas pelicula = (Salas) em.createQuery("SELECT s FROM Salas s WHERE s.sala = :sala")
                    .setParameter("sala",nombre).getSingleResult();
            return pelicula.getPelicula();
            
        }catch (NoResultException ex){
            throw new WebApplicationException(new Throwable("Falla"));
        }
    }
    
    @GET
    @Path("/allPeliculas")
    @Produces({"application/xml","application/json"})
    public String getAllPeliculas(){
        try{
            List <Salas> sala =  em.createQuery("SELECT s FROM Salas s").getResultList();
            String result="";
            for (Salas sala1 : sala) {
                if(!result.contains(sala1.getPelicula())){
                    result+=sala1.getPelicula()+ ". ";
                }
            }
            return result;
            
        }catch (NoResultException ex){
            throw new WebApplicationException(new Throwable("Falla"));
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
