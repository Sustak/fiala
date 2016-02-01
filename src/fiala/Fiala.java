/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fiala;

import static fiala.Utils.LOGGER;
import static fiala.Utils.PROPERTIES;
import java.util.logging.Level;

import static finale.DaycountConvention.*;
import finale.utils.Date;


/**
 *
 * @author Marek
 */
public class Fiala {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //
        LOGGER.log(Level.INFO, "Fiala  starting...");
        System.out.println("Active dir: \t\t\t" + System.getProperty("user.dir"));
        System.out.println("Resource login.conf URL: \t" + Fiala.class.getResource("login.conf").toString() );
        System.out.println("Active user: \t\t" + fiala.Utils.ActiveUser());
        System.out.println("DB driver name: \t\t" + PROPERTIES.getProperty("DB_DRIVER"));
        System.out.println("DB URL: \t\t\t" + PROPERTIES.getProperty("DB_URL"));
        
        System.out.println(finale.Utils.YearFrac(Date.getValueOf(1,1,2010),Date.getValueOf(1,1,2011),ACT_ACT));
    }
    
}
