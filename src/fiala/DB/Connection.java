package fiala.DB;

import fiala.Utils;
import finale.db.SimpleTable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author Marek
 */
public class Connection {

    private Connection() {}
    
    private java.sql.Connection conn;
    public Connection getConnection() {
        if(conn==null) {
            connect();
        }
        return this;
    }
    
    public void connect() {
        String driver = Utils.PROPERTIES.getProperty("DB_DRIVER");
        String url = Utils.PROPERTIES.getProperty("DB_URL");
        String username = Utils.PROPERTIES.getProperty("DB_USERID");
        String password = Utils.PROPERTIES.getProperty("DB_PASSWORD");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            Utils.LOGGER.log(Level.SEVERE,"Establishing connection to {0} using driver {1} failed. Appropriate driver hasn't been found.", new Object[]{url, driver});
        } catch (SQLException ex) {
            Utils.LOGGER.log(Level.SEVERE,"Establishing connection to {0} using driver {1} failed. Wrong user-id and/or password.", new Object[]{url, driver});
        } catch (Exception ex) {
            Utils.LOGGER.log(Level.SEVERE,"Establishing connection to {0} using driver {1} failed. Reason: {2}", new Object[]{url, driver, ex.getMessage()});
        }
        throw new RuntimeException("Failed to connect to a database.");
    }
    
    public SimpleTable getQueryResult( String sqlCommand ) {
        SimpleTable tab = new SimpleTable();
        
        
    }
}
