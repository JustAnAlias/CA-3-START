/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import backgroundservice.Task;
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
        if (cache.size()<1){
            Task task = new Task(this);
            Thread t = new Thread(task);
            t.start();
        }
        return cache;
    }
    
    public void setNewCache(List<CurrencyRate> newRates){
        cache = newRates;
    }
    
    public double convertCurrency(double amount, String from, String to, Date date){
        List<CurrencyRate> rates = getCurrencyRatesByDate(date);
        double res = 0;
        System.out.println("trying to convert from " + from + " to " + to);
        CurrencyRate fromCurrency = getCurrencyFromCode(rates, from);
        System.out.println("fromCurrency is now: " + fromCurrency.getDesc());
        CurrencyRate toCurrency = getCurrencyFromCode(rates, to);
        System.out.println("toCurrency is now: " + toCurrency.getDesc());
        double inReferenceCurrency = toReferenceCurrency(amount, fromCurrency.getRate());
        return inReferenceCurrency / toCurrency.getRate();
    }
    
    public double toReferenceCurrency(double amount, double rate){
        double result = amount * rate;
        return result;
    }
    
    private CurrencyRate getCurrencyFromCode(List<CurrencyRate> rates, String code){
        System.out.println("the size of the rates is: " + rates.size());
        for (CurrencyRate c : rates) {
            if(c.getCode().equals(code)){
                return c;
            }
        }
        return null;
    }
    
}