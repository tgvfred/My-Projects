package com.disney.api.mq;

public class Connection {
		private String server;
		private String hostName = "";
		private String channel = "";
		private int port = 0;
		private String queueManager = "";
		private int transportType = 0;
		private String request = "";
		private String response = "";
		
		private String serverEaiArpBashful = "fldcvfsla0246";
		private String serverEaiArpBashfulDlr = "fldcvfsla6420";
		private String serverEaiBashful = "fldcvfsla0246";
		private String serverEaiBashfulDlr = "fldcvfsla0057";
		private String serverEaiGrumpy= "fldcvfsla0624";
		private String serverEaiGrumpyDlr= "fldcvfsla0617";
		private String serverEaiSleepy = "fldcvfsla0054";
		private String serverEaiSleepyDlr = "fldcvfsla0016";
	
		public Connection(String server){
			this.server = server;
			initialize();
		}
		
		public String getHostName() {return hostName;}
		public String getChannel() {return channel;}
		public int getPort() {return port;}
		public String getQueueManager() {return queueManager;}
		public int getTransportType() {	return transportType;}
		public String getRequestName() {return request;}
		public String getResponseName() {return response;}
		
		
		private void initialize(){
			switch (server) {
			case "EAI_ARP_BASHFUL_WDW":
				this.hostName = serverEaiArpBashful +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiArpBashful.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiArpBashful.substring(9);
				this.request = "SBC_REQUEST";
				break;
				
			case "EAI_ARP_BASHFUL_DLR":
				this.hostName = serverEaiArpBashfulDlr +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiArpBashfulDlr.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiArpBashfulDlr.substring(9);
				this.request = "SBC_REQUEST";
				break;
				
			case "EAI_BASHFUL_WDW":
				this.hostName = serverEaiBashful +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiBashful.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiBashful.substring(9);
				this.request = "SBC_REQUEST";
				break;

			case "EAI_BASHFUL_DLR":
				this.hostName = serverEaiBashfulDlr +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiBashfulDlr.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiBashfulDlr.substring(9);
				this.request = "SBC_REQUEST";
				break;
				
			case "EAI_GRUMPY_WDW":
				this.hostName = serverEaiGrumpy+".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiGrumpy.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiGrumpy.substring(9);
				this.request = "SBC_REQUEST";
				break;

			case "EAI_GRUMPY_DLR":
				this.hostName = serverEaiGrumpyDlr +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiGrumpyDlr.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiGrumpyDlr.substring(9);
				this.request = "SBC_REQUEST";
				break;
			case "EAI_SLEEPY_WDW":
				this.hostName = serverEaiSleepy+".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiSleepy.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiSleepy.substring(9);
				this.request = "SBC_REQUEST";
				break;

			case "EAI_SLEEPY_DLR_WDW":
				this.hostName = serverEaiSleepyDlr +".wdw.disney.com";
				this.channel = "EAI_SVRCONN";
				this.port = 1414;
				this.queueManager = serverEaiSleepyDlr.toUpperCase();
				this.transportType = 1;
				this.response = "SBC_RESPONSE" + serverEaiSleepyDlr.substring(9);
				this.request = "SBC_REQUEST";
				break;
			default:
				break;
			}
			
		}
}
