/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rasmus
 */
@Path("admin")
//@RolesAllowed("Admin")
public class AdminApi {
    
    private UserFacade uf;
    private Gson gson;
    
    public AdminApi(){
        uf = new UserFacade();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    public Response getTodaysUsers(){
        List<entity.User> users = uf.getAllUsers();
        JsonArray usersJson = new JsonArray();
        for (entity.User u : users) {
            usersJson.add(new JsonParser().parse(gson.toJson(u)));
        }
        return Response.status(Response.Status.OK).entity(usersJson.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Produces("text/plain") 
    @Path("users/{id}")
    public Response deleteUsers(@PathParam("id") String id){
        uf.deleteUserByID(id);
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("users/add")
    public Response addUser(String jsonString){
        entity.User newUser = gson.fromJson(jsonString, entity.User.class);
        uf.addUser(newUser);
        return Response.status(Response.Status.OK).entity("User " + newUser.getUserName() + " added").build();
    }
    
}    
