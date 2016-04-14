/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.DocumentEntry;
import facades.DocumentationFacade;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rasmus
 */
@Path("documentation")
//@RolesAllowed("Admin")
public class DocumentAPI {
    
    private DocumentationFacade df;
    private Gson gson;
    
    public DocumentAPI(){
        df = new DocumentationFacade();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getDocuments(){
        List<DocumentEntry> users = df.getDocumentation();
        return Response.status(Response.Status.OK).entity(gson.toJson(users)).type(MediaType.APPLICATION_JSON).build();
    }
    //@RolesAllowed("Developer")
    @DELETE
    @Produces("text/plain") 
    @Path("{id}")
    public Response deleteDocument(@PathParam("id") int id){
        df.deleteEntry(id);
        return Response.status(Response.Status.OK).build();
    }
    
    //@RolesAllowed("Developer")
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editDocument(String jsonString){
        DocumentEntry entry = gson.fromJson(jsonString, DocumentEntry.class);
        df.editEntry(entry);
        return Response.status(Response.Status.OK).entity(gson.toJson(entry)).type(MediaType.APPLICATION_JSON).build();
    }
    
    //@RolesAllowed("Developer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDocument(String jsonString){
        DocumentEntry entry = gson.fromJson(jsonString, DocumentEntry.class);
        df.addEntry(entry);
        return Response.status(Response.Status.OK).entity(gson.toJson(entry)).type(MediaType.APPLICATION_JSON).build();
    }
    
}    
