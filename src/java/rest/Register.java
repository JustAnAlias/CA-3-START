package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
public class Register {
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(String jsonString) throws JOSEException {
    try {
      JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
      String username = json.get("username").getAsString();
      String password = json.get("password").getAsString();
      JsonObject responseJson = new JsonObject();
      List<String> roles;
      
      

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