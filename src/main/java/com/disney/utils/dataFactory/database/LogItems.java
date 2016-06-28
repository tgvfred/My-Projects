package com.disney.utils.dataFactory.database;

import java.util.ArrayList;
import java.util.List;

public class LogItems{
	private List<LogItem> logItems = new ArrayList<LogItem>();

	public void addItem(String serviceClass, String serviceOperation, boolean isErrorExpected){
		logItems.add(new LogItem(serviceClass, serviceOperation, isErrorExpected));
	}
	
	public List<LogItem> getItems(){
		return logItems;
	}
	

	public class LogItem{

		private String serviceClass = "";
		private String serviceOperation = "";
		private boolean isErrorExpected = false;
		
		public LogItem(String serviceClass, String serviceOperation, boolean isErrorExpected){
			this.serviceClass = serviceClass;
			this.serviceOperation = serviceOperation;
			this.isErrorExpected = isErrorExpected;
		}

		public String getServiceClass() {return serviceClass;}
		public String getServiceOperation() {return serviceOperation;}
		public boolean isErrorExpected() {return isErrorExpected;}
	}
	
}