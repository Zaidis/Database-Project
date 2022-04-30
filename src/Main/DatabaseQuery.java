package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseQuery {

	 	static final String databasePrefix ="cs366-2221_mcwhortewt14";
	    static final String netID ="mcwhortewt14"; // Please enter your netId
	    static final String hostName ="washington.uww.edu"; //140.146.23.39 or washington.uww.edu
	    static final String databaseURL ="jdbc:mariadb://"+hostName+"/"+databasePrefix;
	    static final String password="wm8007"; // please enter your own password
	
	    
	    private Connection connection = null;
        private Statement statement = null;
        private ResultSet resultSet = null;
        
	    public void Connection(){
	  
	      try {
	    	    Class.forName("org.mariadb.jdbc.Driver");
	    	  	System.out.println("databaseURL"+ databaseURL);
	            connection = DriverManager.getConnection(databaseURL, netID, password);
	            System.out.println("Successfully connected to the database");
	         }
	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } // end of Connection
	    
	    public ResultSet simpleQuery(String sqlQuery) {
	    
	    	try {
	    		statement = connection.createStatement();
	    		resultSet = statement.executeQuery(sqlQuery);
	    		
	    	}
	    	catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return resultSet;
	    } // end of simpleQuery method
	    
}
