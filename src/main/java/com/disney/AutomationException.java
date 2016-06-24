package com.disney;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.internal.BuildInfo;

public class AutomationException extends RuntimeException{

    public static final String SESSION_ID = "Session ID";
    public static final String DRIVER_INFO = "Driver info";

    private Map<String, String> extraInfo = new HashMap<>();
	private static final long serialVersionUID = -8710980695994382082L;

	public AutomationException(){
		super("Automation Error");
	}
	
	public AutomationException(String message){
		super("Automation Error: " + message);
	}
	

	
	public AutomationException(String message, Throwable cause){
		super("Automation Error: " + message, cause);
	}
	 @Override
	  public String getMessage() {
	    return createMessage(super.getMessage());
	  }

	  private String createMessage(String originalMessageString) {
	    String supportMessage = getSupportUrl() == null ?
	        "" : "For documentation on this error, please visit: " + getSupportUrl() + "\n";	    

	    return (originalMessageString == null ? "" : originalMessageString + "\n")
	        + supportMessage
	        + getBuildInformation();
	  }

	  public String getSystemInformation() {
	    String host = "N/A";
	    String ip   = "N/A";

	    try{
	      host = InetAddress.getLocalHost().getHostName();
	      ip   = InetAddress.getLocalHost().getHostAddress();
	    } catch (UnknownHostException throw_away) {}

	    return String.format("Local System info: host: '%s', ip: '%s', os.name: '%s', os.arch: '%s', os.version: '%s', java.version: '%s'",
	      host,
	      ip,
	      System.getProperty("os.name"),
	      System.getProperty("os.arch"),
	      System.getProperty("os.version"),
	      System.getProperty("java.version"));
	  }

	  public String getSupportUrl() {
	    return null;
	  }

	  public BuildInfo getBuildInformation() {
	    return new BuildInfo();
	  }

	  public static String getDriverName(StackTraceElement[] stackTraceElements) {
	    String driverName = "unknown";
	    for (StackTraceElement e : stackTraceElements) {
	      if (e.getClassName().endsWith("Driver")) {
	        String[] bits = e.getClassName().split("\\.");
	        driverName = bits[bits.length - 1];
	      }
	    }

	    return driverName;
	  }


	  public void addInfo(String key, String value) {
	    extraInfo.put(key, value);
	  }

	  public String getAdditionalInformation() {
	    if (! extraInfo.containsKey(DRIVER_INFO)) {
	      extraInfo.put(DRIVER_INFO, "driver.version: " + getDriverName(getStackTrace()));
	    }

	    String result = "";
	    for (Map.Entry<String, String> entry : extraInfo.entrySet()) {
	      if (entry.getValue() != null && entry.getValue().startsWith(entry.getKey())) {
	        result += "\n" + entry.getValue();
	      } else {
	        result += "\n" + entry.getKey() + ": " + entry.getValue();
	      }
	    }
	    return result;
	  }
}
