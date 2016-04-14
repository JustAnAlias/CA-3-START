/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Role;
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
    public String getMembers() {    
        JsonArray res = new JsonArray();
        List<entity.User> users = uf.getUsers();
        for (entity.User user : users) {
            JsonObject js1 = new JsonObject();
            js1.addProperty("userName", user.getUserName());
            
            JsonArray roles = new JsonArray();
            List<Role> r1 = user.getRoles();
            for (Role r : r1) {
                JsonObject js2 = new JsonObject();
                js2.addProperty("role", r.getRoleName());
                roles.add(js2);
            }
            js1.add("roles", roles);
            res.add(js1);
        }
        return gson.toJson(res);

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
