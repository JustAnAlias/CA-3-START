package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import entity.Role;
import entity.User;
import facades.UserFacade;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.PasswordStorage;

@Path("register")
public class Register {
    Gson gson;
    public Register(){
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
    public void createUser(String user) throws PasswordStorage.CannotPerformOperationException {
        UserFacade uf = new UserFacade();
        JsonObject jo = new JsonParser().parse(user).getAsJsonObject();
        
        User u = new User();
        
        u.setUserName(jo.get("username").getAsString());
        u.setPassword(PasswordStorage.createHash(jo.get("password").getAsString()));
        
        uf.addUser(u);
    }
}
