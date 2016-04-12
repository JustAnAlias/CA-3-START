package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import entity.Role;
import facades.UserFacade;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
public class Register {
    UserFacade uf;
    Gson gson;
    public Register(){
        gson = new GsonBuilder().setPrettyPrinting().create();
        uf = new UserFacade();
    }
    
    
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(String jsonString) throws JOSEException {
    try {
        entity.User user = gson.fromJson(jsonString, entity.User.class);
        user.AddRole(new Role("User"));
        uf.addUser(user);
      
      
      

//      if ((roles = authenticate(username, password)) != null) {
//        String token = createToken(username, roles);
//        responseJson.addProperty("username", username);
//        responseJson.addProperty("token", token);
//        return Response.ok(new Gson().toJson(responseJson)).build();
//      }
    }
    catch (Exception e){
      if(e instanceof JOSEException){
        throw e;
      }
    }
    throw new NotAuthorizedException("Username already exist. Please chose another name", Response.Status.UNAUTHORIZED);
  } 
}