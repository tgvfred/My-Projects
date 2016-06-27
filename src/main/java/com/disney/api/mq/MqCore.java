package com.disney.api.mq;
import java.io.FileNotFoundException;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.WordUtils;
import org.w3c.dom.Document;

import com.disney.AutomationException;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.VirtualTable;
import com.disney.utils.dataFactory.database.Recordset;
import com.ibm.mq.jms.MQQueueConnectionFactory;

@SuppressWarnings("unused")
public class MqCore extends BaseSoapService{
	private String server = "";
	private String tdmURL = "QAAUTO_";
	private String environment = "";
	private String location = "";
	private boolean isARP = false;
	private String operation = "";
	
	public MqCore(String environment, String location, boolean isARP){
		if(isARP) this.server = "EAI_ARP_" + environment.toUpperCase()  + "_" + location.toUpperCase();
		else this.server = "EAI_" + environment.toUpperCase()  + "_" + location.toUpperCase();
		this.environment = environment;
		this.location = location;
		this.isARP = isARP;
	}
	
	@Override
	protected String buildRequestFromWSDL(String operationName){
		String request = "";
		operation = operationName;
		String url = "https://github.disney.com/raw/phlej001/TestDataOnDemand/master/TestDataOnDemand/soap-xml-storage/{environment}/MQ/{operation}.xml";
		url = url.replace("{environment}", WordUtils.capitalize(environment)); 
		url = url.replace("{operation}", operation);
		
		try {
			request = sendGetRequest(url);
		} catch(FileNotFoundException fe){
			throw new AutomationException("XML file not found in this location: " + fe.getMessage());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}

	@Override
    public void sendRequest() {
        try {
            /*MQ Configuration*/
        	Connection conn = new Connection(server);
            MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
            mqQueueConnectionFactory.setHostName(conn.getHostName());
            mqQueueConnectionFactory.setChannel(conn.getChannel());//communications link
            mqQueueConnectionFactory.setPort(conn.getPort());
            mqQueueConnectionFactory.setQueueManager(conn.getQueueManager());//service provider 
            mqQueueConnectionFactory.setTransportType(conn.getTransportType());

            /*Create Connection */
            QueueConnection queueConnection = mqQueueConnectionFactory.createQueueConnection();
            queueConnection.start();

            /*Create session */
            QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            /*Create response queue */
            Queue queue = queueSession.createQueue(conn.getResponseName());

            /*Create text message */
            TextMessage textMessage = queueSession.createTextMessage(getRequest());
            textMessage.setJMSReplyTo(queue);
            textMessage.setJMSType("mcd://xmlns");//message type
            textMessage.setJMSExpiration(2*1000);//message expiration
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT); //message delivery mode either persistent or non-persistemnt

            /*Create sender queue */
            QueueSender queueSender = queueSession.createSender(queueSession.createQueue(conn.getRequestName()));
            queueSender.setTimeToLive(2*1000);
            queueSender.send(textMessage);

            /*After sending a message we get message id */           
            String jmsCorrelationID = " JMSCorrelationID = '" + textMessage.getJMSMessageID() + "'";

            /*Within the session we have to create queue reciver */
            QueueReceiver queueReceiver = queueSession.createReceiver(queue,jmsCorrelationID);

            /*Receive the message from*/
            Message message = queueReceiver.receive(60*1000);
            String responseMsg = ((TextMessage) message).getText();
            queueSender.close();
            queueReceiver.close();
            queueSession.close();
            queueConnection.close();

    		// Covert Soap Response to XML and set it as Response in memory
    		Document doc = XMLTools.makeXMLDocument(responseMsg);
    		doc.normalize();
    		setResponseDocument(doc);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	protected Object[][] getTestScenario(String operation, String scenario){
		if (operation.isEmpty()) throw new RuntimeException("The operation is blank");
		if (scenario.isEmpty()) throw new RuntimeException("The scenario is blank");
		String url = tdmURL + "API_TEST_SCENARIO_DATA_MQ_" + operation.toUpperCase()+"_" + (scenario.toUpperCase().replace(" ", "_"));
		//System.out.println(url);
		Recordset rs = VirtualTable.compileXML(url, new VirtualTable().getAllTestRows(url));
		return removeRowsWithRowNumber(rs.getArray(), 0);
	}   
}