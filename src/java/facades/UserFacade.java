package facades;

import security.IUserFacade;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

  EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);

  public UserFacade() {

  }

  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  @Override
  public IUser getUserByUserId(String id) {
    EntityManager em = emf.createEntityManager();
    try {
      return em.find(User.class, id);
    } finally {
      em.close();
    }
  }
  /*
   Return the Roles if users could be authenticated, otherwise null
   */

  @Override
  /*
   Return the Roles if users could be authenticated, otherwise null
   */
  public List<String> authenticateUser(String userName, String password) {
    EntityManager em = emf.createEntityManager();
    try {
      User user = em.find(User.class, userName);
      if (user == null) {
        return null;
      }

      boolean authenticated;
      try {
        authenticated = PasswordStorage.verifyPassword(password, user.getPassword());
        return authenticated ? user.getRolesAsStrings() : null;
      } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
        Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        return null;
      }
    } finally {
      em.close();
    }
  }

    public List<User> getAllUsers(){
        EntityManager em = getEntityManager();
        List<User> users = new ArrayList<>();
        try{
            Query query = em.createQuery("SELECT u FROM User u");
            users = query.getResultList();
        }finally{
            em.close();
        }
        return users;
    }

    public User deleteUserByID(String id){
        EntityManager em = getEntityManager();
        User user = null;
        try{
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
            query.setParameter("id", Integer.parseInt(id));
            user = (User) query.getSingleResult();
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return user;
    }

}
