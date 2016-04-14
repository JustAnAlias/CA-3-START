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
import entity.CurrencyRate;
import facades.CurrencyFacade;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rasmus
 */
@Path("currency")
//@RolesAllowed("User")
public class Currency {
    
    private CurrencyFacade cf;
    private Gson gson;
    
    public Currency(){
        cf = new CurrencyFacade();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dailyrates")
    public Response getTodaysRates(){
        Calendar yesterday = Calendar.getInstance(TimeZone.getTimeZone("Europe/Copenhagen"));
        yesterday.add(Calendar.DATE, -1);
        List<CurrencyRate> rates = cf.getCurrencyRatesByDate(yesterday.getTime());
        JsonArray ratesJson = new JsonArray();
        for (CurrencyRate rate : rates) {
            ratesJson.add(new JsonParser().parse(gson.toJson(rate)));
        }
        return Response.status(Response.Status.OK).entity(ratesJson.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("calculator/{amount}/{from}/{to}")
    public Response getExchangedValue(@PathParam("amount") double amount, @PathParam("from") String from, @PathParam("to") String to){
        Calendar yesterday = Calendar.getInstance(TimeZone.getTimeZone("Europe/Copenhagen"));
        yesterday.add(Calendar.DATE, -1);
        
        String result = "" + cf.convertCurrency(amount, from, to, yesterday.getTime());
        return Response.status(Response.Status.OK).entity(result).type(MediaType.TEXT_PLAIN).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cache")
    public Response getCache(){
        String ratesJson = gson.toJson(cf.getCache());
        return Response.status(Response.Status.OK).entity(ratesJson).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rates")
    public Response getRates(){
        String ratesJson = gson.toJson(cf.getAllCurrencyRates());
        return Response.status(Response.Status.OK).entity(ratesJson).type(MediaType.APPLICATION_JSON).build();
    }
}