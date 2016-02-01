package fiala;

import java.io.InputStream;
import java.security.Principal;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.security.auth.login.LoginContext;

/**
 * A collection of various utility functions
 * @author Marek Šesták, marek.sestak@gmail.com
 * @version 1.0
 */
public class Utils {
    
    public static final String NEW_LINE = System.getProperty( "line.separator");
    public static final String USER_HOME = System.getProperty( "user.dir");

    public static final Logger LOGGER = Logger.getLogger("Fiala");
    static
    {
        try {
            FileHandler fh = new FileHandler("%t/Fiala.log", true);
            
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING,"Failed to create the main log file Fiala.log");
        }
    }
    
    public static final String PROPERTIES_FILE_NAME = "fiala.properties";
    public static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream is = Utils.class.getResourceAsStream(PROPERTIES_FILE_NAME)) {
             if( is == null )
                 throw new Exception("Unable to load property file " + PROPERTIES_FILE_NAME);
             PROPERTIES.load(is);        
        }
        catch(Exception e) {
            LOGGER.severe("Failed to open main property file " + PROPERTIES_FILE_NAME);
            LOGGER.log(Level.INFO, "Reason: {0}", e.getMessage());            
        }
    }
    
    /**
     * Reads the entire content of the InputStream given as the parameter
     * and returns it in a String.
     * @param is InputStream
     * @return If <code>is</code> is <code>null</code>, returns an empty String.
     */
    public static String InputStreamToString( InputStream is ) {
        if( is==null )
            return "";
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    /**
     * Attempts to get user's credentials from ActiveDirectory services.
     * @return If successful, user's credentials are returned as a String,
     * if the method fails (for instance on systems outside of domains)
     * an empty string is returned and the attempt is logged.
     */
    public static String ActiveUser() {

        System.setProperty("java.security.auth.login.config", Fiala.class.getResource("login.conf").toString() );

        try {
            LoginContext lc = new LoginContext("SignedOnUserLoginContext");
            lc.login();
            
            Set<Principal> principals = lc.getSubject().getPrincipals();
            if( principals.size()>1 ) {
                throw new RuntimeException("Single sign on authentication failed; more than one principal");
            }
            
            return principals.iterator().next().toString();
        }
        catch (Exception e) {
            Utils.LOGGER.log(Level.INFO, "Failed to obtain signed-on user name. Reason: {0}", e.getMessage());
        }
        return "";
    }
}    
