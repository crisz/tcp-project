package it.metallicdonkey.tcp.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBManager {
    private String username;
    private String psw;
    private String server;
    private String database;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
	
    private static final Logger LOG = Logger.getLogger(DBManager.class.getName());

    public void log(Level level, String msg) {
        LOG.log(level, msg);
    }
    
    public Connection getConnection() {
    	return connection;
    }
    
    public DBManager(String host, String uname, String password, String db) {
        loadMySQLDriver();
        this.server = host;
        this.username = uname;
        this.psw = password;
        this.database = db;
    }

    private void loadMySQLDriver() {
// This will load the MySQL driver, each DB has its own driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void connect() {
        String driverString = "jdbc:mysql://" + server + "/" + database + "?user=" + username + "&password=" + psw;
        try {    
        	this.connection = DriverManager.getConnection(driverString);
            return;
        }
        catch (SQLException ex) {
        	Alert alert = new Alert(AlertType.WARNING);
          alert.initOwner(null);
          alert.setTitle("Connection Information");
          alert.setHeaderText("Connessione Non Disponibile");
          alert.setContentText("Controlla la connessione e riprova.");
          alert.showAndWait();
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }        
    }

    public boolean disconnect() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);
            return this.resultSet;
        }
        catch (SQLException ex) {
        	Alert alert = new Alert(AlertType.WARNING);
          alert.initOwner(null);
          alert.setTitle("Connection Information");
          alert.setHeaderText("Connessione Non Disponibile");
          alert.setContentText("Controlla la connessione e riprova.");
          alert.showAndWait();
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int executeUpdate(String query) {
        int result = -1;
        try {
            this.statement = this.connection.createStatement();
            result = this.statement.executeUpdate(query);
            return result;
        } catch (SQLException ex) {
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Connection Information");
            alert.setHeaderText("Connessione Non Disponibile");
            alert.setContentText("Controlla la connessione e riprova.");
            alert.showAndWait();
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }


}
