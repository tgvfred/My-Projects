package com.disney.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.disney.AutomationException;
import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.date.SimpleDate;

public class TestReporter {
	private static boolean printToConsole = false;
	
	/**
	 * No additional info printed to console
	 */
	public static final int NONE = 0;
	
	/**
	 * Will print some useful information to console such as URL's, parameters, and RQ/RS
	 */
	public static final int INFO = 1;
	
	/**
	 *  Will print some low-level granular steps to console 
	 */
	public static final int DEBUG = 2;
	private static int debugLevel = 0;
	
	/**
	 * 
	 * @param level - Options below <br/>
	 *  TestReporter.NONE : (Default) - No additional info printed to console <br/> 
	 *  TestReporter.INFO : Will print some useful information to console such as URL's, parameters, and RQ/RS<br/>
	 *  TestReporter.DEBUG : Will print some low level information to console <br/>
	 */
	public static void setDebugLevel(int level){
		debugLevel = level;
	}
	
	public static int getDebugLevel(){
		return debugLevel;
	}
	
	private static String getTimestamp() {
		return SimpleDate.getTimestamp().toString() + " :: ";
	}

	private static String trimHtml(String log) {
		return log.replaceAll("<[^>]*>", "");
	}

	public static void setPrintToConsole(boolean printToConsole) {
		TestReporter.printToConsole = printToConsole;
	}

	public static boolean getPrintToConsole() {
		return printToConsole;
	}
	private static String getClassPath(){
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		int x = 0;
		String filename = "";
		String path = "";
		for(StackTraceElement element : elements){
			filename = element.getClassName().toString();
			if(x == 0 || x == 1 || x == 2) {
				x++;
				continue;
			}else if(!filename.contains("sun.reflect")  &&
					!filename.contains("com.disney.utils.TestReporter") &&
					!filename.contains("java.lang.reflect") &&
					!filename.contains("java.lang.Thread") &&
					!filename.contains("com.disney.composite.core.interfaces.impl") &&
					!filename.contains("interfaces.impl.internal.ElementHandler") &&
					!filename.contains("com.disney.test.composite") &&
					!filename.contains("com.sun.proxy") &&
					!filename.contains("org.testng") &&
					!filename.contains("java.util.concurrent.ThreadPoolExecutor") &&
					!filename.contains("com.disney.composite.core.Screenshot"))
			{
				path = element.getClassName()+"#"+element.getMethodName();
				break;
			}
			
		}
		return path;
	}

	public static void logStep(String step) {
		Reporter.log("<br/><b><font size = 4>-------------------------------------------------------------------------------</font></b><br/>");
		Reporter.log("<br/><b><font size = 4>Step: " + step + "</font></b>");
		Reporter.log("<br/><b><font size = 4>-------------------------------------------------------------------------------</font></b><br/>");
		if(getPrintToConsole()) System.out.println(trimHtml(step));
		
	}
	
	public static void logVideo(String sessionId) {
		Reporter.log("<br/><b><font size = 4>-------------------------------------------------------------------------------</font></b><br/>");
		Reporter.log("<br/><b><font size = 4>Video for session: " +sessionId + "</font></b><br/>");
		Reporter.log("<a href='http://dnhprpptstw701.disid.disney.com:3000/download_video/" + sessionId +".mp4' font size = 4 target='_blank'>Video Link</a>");
		Reporter.log("<br/><b><font size = 4>-------------------------------------------------------------------------------</font></b><br/>");		
	}

/*	public static void logScenario() {
		Reporter.log("<br/><b><font size = 4>Data Scenario: " + Datatable.getCurrentScenario() + "</font></b><br/>");
	}*/

	public static void logScenario(String scenario) {
		Reporter.log("<br/><b><font size = 4>Data Scenario: " + scenario + "</font></b><br/>");
		if(getPrintToConsole()) System.out.println(trimHtml(scenario));
		logInfo("-------------------------------------------------------------------------------");
		logInfo("Data Scenario: " + scenario);
		logInfo("-------------------------------------------------------------------------------");
	}

	public static void log(String message) {
		Reporter.log(getTimestamp() + getClassPath() + " > "+ message + "<br />");
		if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + " > "+ trimHtml(message.trim()));
	}

	/**
	 * Use to output low-level granular steps
	 * @param message
	 */
	public static void logDebug(String message) {
		if(debugLevel >= DEBUG){
			//Reporter.log(getTimestamp().replace(" ::", "") + ":: DEBUG ::" + getClassPath() + " > "+ message + "<br />");
			//if(getPrintToConsole()) System.out.println(getTimestamp()replace(" ::", "") + "::DEBUG:: " + trimHtml(message.trim()));
			System.out.println(getTimestamp().replace(" ::", "") + ":: DEBUG :: " + getClassPath() + " > "+  (message.trim()));
		}
	}

	/**
	 * Use to output useful information such as URL's, parameters, and RQ/RS
	 * @param message
	 */
	public static void logInfo(String message) {
		if(debugLevel >= INFO){
			//logNoXmlTrim(getTimestamp() + ":: INFO :: "+ getClassPath() + " > " + message + "<br />");
			//if(getPrintToConsole()) System.out.println(getTimestamp().replace(" ::", "") + "::INFO:: " + trimHtml(message.trim()));
			System.out.println(getTimestamp().replace(" ::", "") + ":: INFO :: "  + getClassPath() + " > "+ message.trim());
		}
	}

	public static void logNoHtmlTrim(String message) {
		Reporter.log(getTimestamp() + " :: " + getClassPath() + " > "+message + "<br />");
		if(getPrintToConsole()) System.out.println(getTimestamp() +getClassPath() + " > "+ message.trim());
	}
	public static void logNoXmlTrim(String message) {
		Reporter.setEscapeHtml(true);
		Reporter.log("");
		Reporter.log(message);
		Reporter.setEscapeHtml(false);
		Reporter.log("<br /");
		if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + " > "+message.trim());
	}

	public static void logFailure(String message) {
		Reporter.log(getTimestamp() + getClassPath() + " > "+" <font size = 2 color=\"red\"><b><u> FAILURE: " + message + "</font></u></b><br />");
		if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + " > "+trimHtml(message.trim()));
	}

	 public static void logScreenshot(WebDriver driver, String fileLocation, String slash) {
			File file = new File("");

			try {
				file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(file, new File(fileLocation));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String jenkinsPath = System.getenv("JOB_URL");
			String jenkinsName = System.getenv("JOB_NAME");
			if(jenkinsPath != null && !jenkinsPath.isEmpty()){
				jenkinsName = jenkinsName.replace("/", "\\");
				String webFileLocation = fileLocation.replace("C:\\Jenkins\\workspace\\", jenkinsPath);
				if(jenkinsPath.indexOf("job") != jenkinsPath.lastIndexOf("job")) webFileLocation = webFileLocation.replace(jenkinsName, "ws");
				else webFileLocation = webFileLocation.replace(jenkinsName + "/" + jenkinsName, jenkinsName + "/ws");
				TestReporter.log("Web File Location : " +webFileLocation);
				Reporter.log("<a  target='_blank' href='" + webFileLocation + "'><img src='"+ webFileLocation + "' height='200' width='300'/></a>");
			}else{
				TestReporter.log("File Location : " +fileLocation);
				Reporter.log("<a  target='_blank' href='" + fileLocation + "'> <img src='file:///" + fileLocation + "' height='200' width='300'/> </a>");
			}
	 }
	public static void interfaceLog(String message) {
		Reporter.log(getTimestamp() + message + "<br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + trimHtml(message.trim()));
	}

	public static void interfaceLog(String message, boolean failed) {
		Reporter.log(getTimestamp() + "<font size = 2 color=\"red\">" + message + "</font><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + trimHtml(message.trim()));
		if(failed)
			Assert.fail(message);
	}

	public static void assertTrue(boolean condition, String description) {
		try {
			Assert.assertTrue(condition, description);
		} catch (AssertionError failure) {
			logFailure("Assert True - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert True - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert True - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert True - " + trimHtml(description));
	}

	public static void assertFalse(boolean condition, String description) {
		try {
			Assert.assertFalse(condition, description);
		} catch (AssertionError failure) {
			logFailure("Assert False - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert False - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert False - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert False - " + trimHtml(description));
	}

	public static void assertEquals(Object value1, Object value2, String description) {
		try {
			Assert.assertEquals(value1, value2, description);
		} catch (AssertionError failure) {
			logFailure("Assert Equals - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert Equals - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Equals - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert Equals - " + trimHtml(description));
	}

	public static void assertNotEquals(Object value1, Object value2, String description) {
		try {
			Assert.assertNotEquals(value1, value2, description);
		} catch (AssertionError failure) {
			logFailure("Assert Not Equals - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert Not Equals - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Not Equals - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert Not Equals - " + trimHtml(description));
	}

	public static void assertGreaterThanZero(int value) {
		try {
			Assert.assertTrue(value > 0);
		} catch (AssertionError failure) {
			logFailure("Assert Greater Than Zero - " + value);
			if (getPrintToConsole())
				System.out.println(
						getTimestamp() + "Assert Greater Than Zero - Assert " + value + " is greater than zero");
			Assert.fail("Assert " + value + " is greater than zero");
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Greater Than Zero - Assert " + value
				+ " is greater than zero</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert Greater Than Zero - Assert " + value + " is greater than zero");
	}

	public static void assertGreaterThanZero(float value) {
		assertGreaterThanZero((int) value);
	}

	public static void assertGreaterThanZero(double value) {
		assertGreaterThanZero((int) value);
	}

	public static void assertNull(Object condition, String description) {
		try {
			Assert.assertNull(condition, description);
		} catch (AssertionError failure) {
			logFailure("Assert Null - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert Null - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Null - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert Null - " + trimHtml(description));
	}

	public static void assertNotNull(Object condition, String description) {
		try {
			Assert.assertNotNull(condition, description);
		} catch (AssertionError failure) {
			logFailure("Assert Not Null - " + description);
			if (getPrintToConsole())
				System.out.println(getTimestamp() + "Assert Not Null - " + trimHtml(description));
			Assert.fail(description);
		}
		Reporter.log(getTimestamp() + "<font size = 2 color=\"green\"><b><u>Assert Not Null - " + description
				+ "</font></u></b><br />");
		if (getPrintToConsole())
			System.out.println(getTimestamp() + "Assert Not Null - " + trimHtml(description));
	}
	
	public static void logAPI(boolean fail, String message, BaseSoapService bs){
		String failFormat = "";
		if(fail){
			failFormat = "<font size = 2 color=\"red\">";
			logFailure(message);
		}
			logNoHtmlTrim("<font size = 2><b>Endpoint: " + bs.getServiceURL() + "</b></font><br/>"+failFormat+ "<b>SOAP REQUEST [ " + bs.getService() + "#" + bs.getOperation() + " ] </b></font>");
			Reporter.setEscapeHtml(true);
			logNoXmlTrim(bs.getRequest().replaceAll("</*>", "</*>"));
			Reporter.setEscapeHtml(false);
			Reporter.log("<br/>");
			logNoHtmlTrim(failFormat + "<b>SOAP RESPONSE [ " + bs.getService() + "#" + bs.getOperation() + " ] </b></font>" );
			Reporter.setEscapeHtml(true);
			logNoXmlTrim(bs.getResponse());
			Reporter.setEscapeHtml(false);
			Reporter.log("<br/>");
			
		if(fail){
			throw new SoapException(message);
		}
	}
}
