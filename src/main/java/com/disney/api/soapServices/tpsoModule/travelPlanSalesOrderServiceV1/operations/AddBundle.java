package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class AddBundle extends TravelPlanSalesOrderService{
	private String environment; 
	private String database = "SALES";
	final String salesOrderItemType = "BUNDLE";
	final String columnName = "SALES_ORDER_ITEM_TYPE";
	private String query = "select a.sls_ord SALES_ORDER, b.sls_ord_item_id SALES_ORDER_ITEM_ID, b.sls_ord_item_typ_nm "+columnName+" "
			+ "from sales_tp.sls_ord a join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord where a.tp_id={TP_ID} ";
	private String salesOrderItemQuery = "and b.sls_ord_item_typ_nm  = '"+salesOrderItemType+"'";
	private String[] bundleIds;
	private String[] salesOrderIDs;
	private int column;
	private Recordset resultSet = null;

	public AddBundle(String environment, String scenario) {
		super(environment);
		this.environment = environment;

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("addBundle")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		removeComments() ;
		removeWhiteSpace();
	}
	
	
	//*******************************************
	//*******************************************
	//*******************************************
	//		Request Methods
	//*******************************************
	//*******************************************
	//*******************************************
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/travelPlanId", value);}
	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/salesOrderId", value);}
	/*
	 * /Envelope/Body/addBundle/packageBundleRequests
	 */
	public void setPackageBundleRequestsReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@referenceId", value);}
	public void setPackageBundleRequestsCode(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@code", value);}
	public void setPackageBundleRequestsBookDate(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@bookDate", value);}
	public void setPackageBundleRequestsStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@startDate", value);}
	public void setPackageBundleRequestsEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@endDate", value);}
	public void setPackageBundleRequestsContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/@contactName", value);}
	/*
	 * /Envelope/Body/addBundle/packageBundleRequests/salesOrderItemGuests
	 */
	public void setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/salesOrderItemGuests/@guestReferenceId", value);}
	public void setPackageBundleRequestsSalesOrderItemGuestsAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/packageBundleRequests/salesOrderItemGuests/@ageType", value);}
	/*
	 * /Envelope/Body/addBundle/guests
	 */
	public void setGuestsId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/guests/@id", value);}
	public void setGuestsGuestReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/guests/@guestReferenceId", value);}
	/*
	 * /Envelope/Body/addBundle/guests/guestName
	 */
	public void setGuestsGuestNameFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/guests/guestName/@firstName", value);}
	public void setGuestsGuestNameLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/guests/guestName/@lastName", value);}
	/*
	 * /Envelope/Body/addBundle/businessContext
	 */
	public void setBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/businessContext/@destination", value);}
	public void setBusinessContextSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/businessContext/@salesChannel", value);}
	public void setBusinessContextCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/businessContext/@communicationsChannel", value);}
	public void setBusinessContextPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/addBundle/businessContext/@pointOfOrigin", value);}

	//*******************************************
	//*******************************************
	//*******************************************
	//		Response Methods
	//*******************************************
	//*******************************************
	//*******************************************
	public String getSalesOrderId(){return getResponseNodeValueByXPath("/Envelope/Body/addBundleResponse/addBundleResponse/@salesOrderId");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/addBundleResponse/addBundleResponse/@travelPlanId");}
	
	
	
	
	
	public void retrieveBundleSalesOrderItemIds(String TP_ID){
		query = query.replace("{TP_ID}", TP_ID);
		query = query + salesOrderItemQuery;
		OracleDatabase odb = new OracleDatabase(environment, database);
		resultSet = new Recordset(odb.getResultSet(query));
		resultSet.print();
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "The sales order item query for TP_ID ["+TP_ID+"] did not return any records in ["+environment+"].");
		TestReporter.log("Record Count: " + String.valueOf(resultSet.getRowCount()));
		bundleIds = new String[resultSet.getRowCount()];
		//Capture the accommodation sales item ids
		column = resultSet.getColumnIndex(columnName);
		for(int record = 1; record <= resultSet.getRowCount(); record++){ 
			bundleIds[record - 1] = resultSet.getValue(column - 1, record);
			salesOrderIDs[record - 1] = resultSet.getValue(column - 3, record);
		}
	}
	
	public void retrieveSalesOrderId(String TP_ID){
		query = query.replace("{TP_ID}", TP_ID);
		OracleDatabase odb = new OracleDatabase(environment, database);
		resultSet = new Recordset(odb.getResultSet(query));
		resultSet.print();
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "The sales order query for TP_ID ["+TP_ID+"] did not return any records in ["+environment+"].");
		TestReporter.log("Record Count: " + String.valueOf(resultSet.getRowCount()));
		salesOrderIDs = new String[resultSet.getRowCount()];
		//Capture the accommodation sales item ids
		for(int record = 1; record <= resultSet.getRowCount(); record++){
			salesOrderIDs[record - 1] = resultSet.getValue(1, record);
		}
	}
	
	public String[] getBundleSalesOrderItemIds(){return bundleIds;}
	public String[] getBundleSalesOrderIds(){return salesOrderIDs;}
}
