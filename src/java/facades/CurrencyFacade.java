/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.CurrencyRate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;

/**
 *
 * @author Rasmus
 */
public class CurrencyFacade {
    
    private EntityManagerFactory emf;
    private List<CurrencyRate> cache;

    public CurrencyFacade() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        cache = new ArrayList();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public CurrencyRate addCurrencyRate(CurrencyRate rate){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(rate);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return rate;
    }
    
    public List<CurrencyRate> getAllCurrencyRates(){
        EntityManager em = getEntityManager();
        List<CurrencyRate> rates = new ArrayList<>();
        try{
            Query query = em.createQuery("SELECT cr FROM CurrencyRate cr");
            rates = query.getResultList();
        }finally{
            em.close();
        }
        return rates;
    }
    
    public List<CurrencyRate> getCurrencyRatesByDate(Date date){
        EntityManager em = getEntityManager();
        List<CurrencyRate> rates = new ArrayList<>();
        try{
            Query query = em.createQuery("SELECT cr FROM CurrencyRate cr WHERE cr.date = :date");
            query.setParameter("date", date);
            rates = query.getResultList();
        }finally{
            em.close();
        }
        return rates;
    }
    
    public List<CurrencyRate> getCache(){
        return cache;
    }
    
    public void setNewCache(List<CurrencyRate> newRates){
        cache = newRates;
    }
    
    public double convertCurrency(double amount, String from, String to){
        double res = 0;
        CurrencyRate fromCurrency = null;
        CurrencyRate toCurrency = null;
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getCode()==from){
                fromCurrency = cache.get(i);
            }
            if (cache.get(i).getCode()==to){
                toCurrency = cache.get(i);
            }
        }
        double inReferenceCurrency = toReferenceCurrency(amount, fromCurrency.getRate());
        return inReferenceCurrency * toCurrency.getRate();
    }
    
    public double toReferenceCurrency(double amount, double rate){
        double result = amount * rate;
        return result;
    }
}