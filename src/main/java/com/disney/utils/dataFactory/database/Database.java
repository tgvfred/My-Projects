package com.disney.utils.dataFactory.database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.io.FileUtils;

import com.disney.AutomationException;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public abstract class Database {
	final public static String AVAIL_SE = "availse";
	final public static String DIRECT_CONNECT = "directconnect";
	final public static String DME = "dme";
	final public static String DREAMS = "dreams";
	final public static String DREAMS_LOG = "dreams_log";
	final public static String DVC_POINTS = "dvc_points";
	final public static String GOMASTER = "gomaster";
	final public static String MAGICAL_CELEBRATIONS = "celebrations";
	final public static String RECOMMENDER = "recommender";
	final public static String SALES = "sales";
	
	
	protected String strDriver = null;	
	private String strDbHost = null;
	private String strDbPort= null;
	private String strDbService= null;
	private String strDbUser= null;
	private String strDbPassword= null;
	protected String strConnectionString= null; 
	
	protected abstract void setDbDriver(String driver);
	
	protected String getDbDriver(){
		return strDriver;
	}
	
	protected void setDbHost(String host){
		strDbHost = host;
	}
	
	protected String getDbHost(){
		return strDbHost;
	}
	
	protected void setDbPort(String port){
		strDbPort = port;
	}
	
	protected String getDbPort(){
		return strDbPort;
	}
	
	protected void setDbService(String serivce){
		strDbService = serivce;
	}
	
	protected String getDbService(){
		return strDbService;
	}
	
	public void setDbUserName(String user){
		strDbUser = user;
	}
	
	protected String getDbUserName(){
		return strDbUser;
	}
	
	public void setDbPassword(String pass){
		strDbPassword = pass;
	}
	
	protected String getDbPassword(){
		return strDbPassword;
	}
	
	protected abstract void setDbConnectionString(String connection);
	
	protected String getDbConnectionString(){
		return strConnectionString;
	}
		
	public Object[][] getResultSet(String query) {
	    loadDriver();
	    //System.out.println("here");
	    File temp = null;
	 //   Path tempDir = null;
	    String pathToTempDir = System.getProperty("java.io.tmpdir");
	    Connection connection = null;
	    String file = "tnsnames.ora";
	    if(!new File(pathToTempDir+file).exists()){
			try {
				URL inputUrl = getClass().getResource("/com/disney/utils/dataFactory/database/tnsnames.ora");
				File dest = new File("/com/disney/utils/dataFactory/database/tnsnames.ora");
			//	tempDir = Files.createTempDirectory("tns");
			    temp = File.createTempFile("tns" , ".ora");
				FileUtils.copyURLToFile(inputUrl, temp.getAbsoluteFile());
			//	String path = tempDir.toAbsolutePath()+"/";
				temp.renameTo(new File(pathToTempDir + file));
			} catch (IOException e) {
				 if(!new File(pathToTempDir+file).exists()){
					 throw new AutomationException("Failed to create tnsnames.ora", e);
				 }
			} 
		}
		

		System.setProperty("oracle.net.tns_admin", pathToTempDir);
		
		for(int times = 0 ; times <= 5 ; times++){
			try {
				connection = DriverManager.getConnection(getDbConnectionString(), getDbUserName(), getDbPassword());
			} catch (SQLException e) {
				if(times == 5) throw new AutomationException("Failed to connect to database after 5 attempts", e);
				Sleeper.sleep(Randomness.randomNumberBetween(2000, 5000));
			}
		}

		  TestReporter.logInfo(query);
			ResultSet rs = (runQuery(connection, query));
			  
			Object[][] extractedRs = extract(rs);
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return extractedRs;
	    
	}
	 
	
	private void loadDriver(){			
		try{
			  Class.forName(getDbDriver());
		} catch(ClassNotFoundException cnfe) {
		      System.err.println("Error loading driver: " + cnfe);
		}		 
	}

	private static ResultSet runQuery(Connection connection, String query) {
	    try {
	      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	      ResultSet resultSet = statement.executeQuery(query);
	
	      return(resultSet);
	      
	    } catch(SQLException sqle) {
	      System.err.println("Error executing query: " + sqle);
	      return(null);	     
	    } 
	}
	
    /** 
     * Returns an ArrayList of ArrayLists of Strings extracted from a 
     * ResultSet retrieved from the database. 
     * @param resultSet ResultSet to extract Strings from 
     * @return an ArrayList of ArrayLists of Strings 
     * @throws SQLException if an SQL exception occurs 
     */  
    public static Object[][] extract(ResultSet resultSet)   {  
        // get row and column count
        int rowCount = 0;
        try {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }
        catch(Exception ex) {
        	rowCount = 0;
        }
       try{
        int columnCount = resultSet.getMetaData().getColumnCount();  
      
        Object[][] table = new String[rowCount+1][columnCount];  
        ResultSetMetaData rsmd = resultSet.getMetaData();        
        
        for(int rowNum = 0; rowNum <= rowCount; rowNum++) {  
            for(int colNum = 0, rsColumn = 1; colNum < columnCount; colNum++, rsColumn++){
            	
            	if(rowNum == 0){
            		table[rowNum][colNum] = resultSet.getMetaData().getColumnName(rsColumn);   
            	}else if(resultSet.getString(colNum+1) == null){
            		//System.out.println("null");
            		table[rowNum][colNum] = "NULL";
            	}else{
            		try{
            			
	            		//
	            		switch (rsmd.getColumnType(rsColumn)){
	            		
	            		case Types.DATE:
	            			table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
	            			break;
	
	            		case Types.TIMESTAMP:
	            			table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
	            			break;
	            			
	            		case Types.TIME:
	            			table[rowNum][colNum] = resultSet.getTime(rsColumn);
	            			break;	            				            	
	            			
	            		default:
	            			table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
	            			break;
	            		}
            		}catch (Exception e){
            			table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
            		}
            		//System.out.println(resultSet.getString(c).intern());
            		//table[rowNum][colNum] = resultSet.getString(colNum+1).intern();              
            	}
           }
            resultSet.next();
        }  
        return table;  
       }catch(SQLException sql){throw new AutomationException("Failed to generate result set", sql);}
    }  
	
}
