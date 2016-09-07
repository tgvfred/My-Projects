package com.disney.utils.dataFactory.database.databaseImpl;

import com.disney.test.utils.Base64Coder;
import com.disney.utils.Environment;
import com.disney.utils.dataFactory.database.Database;

public class OracleDatabase extends Database {	
	private String dbGeneralUsername = Base64Coder.decodeString("QVBQREVWX1JP");
	private String dbGeneralPassword = Base64Coder.decodeString("QVBQREVWX1JPIzE=");
	private String dbGoMasterUsername = Base64Coder.decodeString("b2RzX3N5bmNfcm8=");
	private String dbGoMasterPassword = Base64Coder.decodeString("b2RzX3N5bmNfcm81");
	private String dbMcUsername = Base64Coder.decodeString("TUM=");
	private String dbMcPassword = Base64Coder.decodeString("bWNhZG1pbg==");
	private String dbSeUsername = Base64Coder.decodeString("YXZhaWxzZXJv");
	private String dbSePassword = Base64Coder.decodeString("YXZhaWxzZXJv");
	
	public OracleDatabase(String environment, String tnsName){
		environment = Environment.getEnvironmentName(environment);
		switch (environment.toLowerCase()) {
		case "snowwhite":
			environment = "SNOW_WHITE";
			break;
		case "evilqueen":
			environment = "EVIL_QUEEN";
			break;
		case "development":
			environment = "DEV3";
			break;
		case "bashful_cm":
			environment = "BASHFUL";
			break;
		case "sleepy_cm":
			environment = "SLEEPY";
			break;
		case "grumpy_cm":
			environment = "GRUMPY";
			break;
		case "doc_cm":
			environment = "DOC";
			break;
		default:
			break;
		}
		setDbDriver("oracle.jdbc.driver.OracleDriver");
		setDbConnectionString("jdbc:oracle:thin:@" + environment.toUpperCase() + "." + tnsName.toUpperCase());
		
		switch(tnsName.toLowerCase().replace("_", "")){
		case "dreams":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "dreamslog":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "sales":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "recommender":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "eailog":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "gomaster":
			setDbUserName(dbGoMasterUsername);
			setDbPassword(dbGoMasterPassword);
			break;
		case "dvcpoints":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		case "celebrations":
			setDbUserName(dbMcUsername);
			setDbPassword(dbMcPassword);
			break;

		case "availse":
			setDbUserName(dbSeUsername);
			setDbPassword(dbSePassword);
			break;
			
		case "directconnect":
			setDbUserName(dbGeneralUsername);
			setDbPassword(dbGeneralPassword);
			break;
		}
	}

	@Override
	protected void setDbDriver(String driver) {
		super.strDriver = driver;	
	}

	@Override
	protected void setDbConnectionString(String connection) {
		super.strConnectionString = connection;
	}
}
