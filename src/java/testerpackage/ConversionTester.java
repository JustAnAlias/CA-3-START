/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testerpackage;

import facades.CurrencyFacade;

/**
 *
 * @author Michael
 */
public class ConversionTester {
    public static void main(String[] args) {
        CurrencyFacade cf = new CurrencyFacade();
        cf.getCache();
//        System.out.println(cf.convertCurrency(100, "SEK", "AUD"));
        
    }
}
