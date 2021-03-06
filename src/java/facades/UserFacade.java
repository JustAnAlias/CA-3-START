package facades;

import entity.Role;
import security.IUserFacade;
import entity.User;
import exceptions.UserAlreadyExistsException;
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

    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT u FROM User u");
            users = query.getResultList();
        } finally {
            em.close();
        }
        return users;
    }

    public User deleteUserByID(String id) {
        EntityManager em = getEntityManager();
        User user = null;
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.userName = :id", User.class);
            query.setParameter("id", id);
            user = (User) query.getSingleResult();
            user.RemoveRoles();
            em.getTransaction().begin();
            em.merge(user);
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return user;
    }

    public void addUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println("something went wrong persisting the user in the DB");
        }
        finally {
            em.close();
        }
    }
    
    public void editUser(User user) throws UserAlreadyExistsException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User oldUser = em.find(User.class, user.getUserName());
            oldUser.RemoveRoles();
            for (Role role : user.getRoles()) {
                oldUser.AddRole(role);
            }
            oldUser.setPassword(user.getPassword());
            em.merge(oldUser);
            em.getTransaction().commit();
        }
        catch(Exception e){
            throw new UserAlreadyExistsException(user.getUserName());
        }
        finally {
            em.close();
        }
    }

    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u").getResultList();
        } finally {
            em.close();
        }
    }

}
