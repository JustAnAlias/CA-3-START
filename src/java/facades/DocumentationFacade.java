package facades;

import entity.DocumentEntry;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

public class DocumentationFacade {

    EntityManagerFactory emf;
    EntityManager em;
    public DocumentationFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   
    public List<DocumentEntry> getDocumentation() {
        em = emf.createEntityManager();
        List<DocumentEntry> result = null;
        try {
            Query q = em.createQuery("select d from DocumentEntry d", DocumentEntry.class);
            result = q.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
    

    public DocumentEntry deleteEntry(int id) {
        em = getEntityManager();
        DocumentEntry entry = null;
        try {
            Query query = em.createQuery("SELECT d FROM DocumentEntry d WHERE d.id = :id", DocumentEntry.class);
            query.setParameter("id", id);
            entry = (DocumentEntry) query.getSingleResult();
            em.getTransaction().begin();
            em.remove(entry);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return entry;
    }

    public void addEntry(DocumentEntry entry) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entry);
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println("something went wrong persisting the document entry in the DB");
        }
        finally {
            em.close();
        }
    }
    
    public void editEntry(DocumentEntry entry) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.find(DocumentEntry.class, entry.getId());
            em.merge(entry);
            em.getTransaction().commit();
        }
        catch(Exception e){
            System.out.println("something went wrong merging the document entry in the DB");
        }
        finally {
            em.close();
        }
    }

}
