/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgroundservice;

import entity.CurrencyRate;
import facades.CurrencyFacade;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 *
 * @author Rasmus
 */
public class Task implements Runnable {
    
    private CurrencyFacade cf;
    private List<CurrencyRate> newRates;
    
    public Task(CurrencyFacade cf){
        newRates = new ArrayList();
        this.cf = cf;
    }

    @Override
    public void run() {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            ReadXML reader = new ReadXML();
            xr.setContentHandler(reader);
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
            for (CurrencyRate rate : reader.getTodaysRates()) {
//                System.out.println("________________________" + rate.getDesc());
                cf.addCurrencyRate(rate);
                newRates.add(rate);
            }
            cf.setNewCache(newRates);
        } catch (MalformedURLException ex) {
//            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
//            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}    
