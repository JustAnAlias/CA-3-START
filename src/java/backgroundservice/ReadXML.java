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
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rasmus
 */

public class ReadXML extends DefaultHandler {
    
    private List<CurrencyRate> todaysRates;
    private Date today;
    private CurrencyFacade cf;
    
    public ReadXML(){
        today = new Date();
        todaysRates = new ArrayList<CurrencyRate>();
    }
    
    public List<CurrencyRate> getTodaysRates(){
        return todaysRates;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("dailyrates")) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                today = format.parse(attributes.getValue("id"));
            } catch (ParseException ex) {
                today = new Date();
            }
        }
        
        if (localName.equals("currency")) {
            CurrencyRate rate = new CurrencyRate();
            rate.setCode(attributes.getValue("code"));
            rate.setDesc(attributes.getValue("desc"));
            try{
                rate.setRate(Float.parseFloat(attributes.getValue("rate")));
            }catch(java.lang.NumberFormatException ex){
                rate.setRate(-1);
            }
            rate.setDate(today);
            todaysRates.add(rate);
        }
        
        System.out.print("Element: " + localName + ": ");
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.print("[Atribute: NAME: " + attributes.getLocalName(i) + " VALUE: " + attributes.getValue(i) + "] ");
        }
        System.out.println("");
    }

    public static void main(String[] argv) throws MalformedURLException, IOException {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            ReadXML reader = new ReadXML();
            xr.setContentHandler(reader);
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
            for (CurrencyRate rate : reader.getTodaysRates()) {
                CurrencyFacade cf = new CurrencyFacade();
                cf.addCurrencyRate(rate);

                System.out.println(rate.toString());
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}    