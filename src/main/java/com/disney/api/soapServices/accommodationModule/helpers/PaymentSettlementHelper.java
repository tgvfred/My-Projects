package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.WebService;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.CreateSettlementMethod;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.RetrieveFolioBalanceDue;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCardPayment;
import com.disney.api.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.Datatable;
import com.disney.utils.GenerateCard;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class PaymentSettlementHelper {
    private String environment;
    private String locationId;
    private HouseHold hh;
    private String primaryGuestId;
    private String primaryPartyId;
    private String paymentAmount;
    private WebService ws;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public HouseHold getHh() {
        return hh;
    }

    public void setHh(HouseHold hh) {
        this.hh = hh;
    }

    public String getPrimaryGuestId() {
        return primaryGuestId;
    }

    public void setPrimaryGuestId(String primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
    }

    public String getPrimaryPartyId() {
        return primaryPartyId;
    }

    public void setPrimaryPartyId(String primaryPartyId) {
        this.primaryPartyId = primaryPartyId;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public WebService getWs() {
        return ws;
    }

    public void setWs(WebService ws) {
        this.ws = ws;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getTcgId() {
        return tcgId;
    }

    public void setTcgId(String tcgId) {
        this.tcgId = tcgId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public PaymentSettlementHelper(String environment, WebService ws, HouseHold hh) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (ws == null) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setWs(ws);
            if (getWs() instanceof ReplaceAllForTravelPlanSegment) {
                setTpId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanId());
                setTpsId(((ReplaceAllForTravelPlanSegment) ws).getTravelPlanSegmentId());
                setTcgId(((ReplaceAllForTravelPlanSegment) ws).getTravelComponentGroupingId());
                setTcId(((ReplaceAllForTravelPlanSegment) ws).getTravelComponentId());
            }
        }
        if (hh == null) {
            throw new AutomationException("The household object cannot be null.");
        } else {
            setHh(hh);
        }
        retrieveReservation();
    }

    public void createSettlementMethod(String scenario) {
        Datatable datatable = new Datatable();
        datatable.setVirtualtablePath(Datatable.PAYMENTUI_MASTER_DATA_PATH);
        datatable.setVirtualtablePage("PaymentUI");
        datatable.setVirtualtableScenario(scenario);

        String paymentMethod = null;
        String status = null;
        String delay = null;
        String expressCheckout = null;

        paymentMethod = datatable.getDataParameter("PaymentMethod");
        status = datatable.getDataParameter("Status");
        delay = datatable.getDataParameter("Delay");
        expressCheckout = datatable.getDataParameter("ExpressCheckout");

        GenerateCard card = new GenerateCard();
        Map<String, String> cardInfo = null;
        // System.out.println();
        try {
            cardInfo = card.getCardInfo(status, delay, card.getCradTypeEnum(paymentMethod));
        } catch (Exception e) {
            TestReporter.assertNotNull(cardInfo, "An error occurred retrieving the card.  The search parameters are as follows:"
                    + "\nSTATUS: " + status
                    + "\nDELAY:  " + delay
                    + "\nMETHOD: " + paymentMethod
                    + "\nStacktrace: " + e.getMessage());
        }
        String cardPaymentMethod = datatable.getDataParameter("PaymentMethod");
        String cardNumber = cardInfo.get("AccountNumber").replace("", "");
        String cardExpirationMonth = cardInfo.get("ExpMonth");
        String cardExpirationYear = cardInfo.get("ExpYear");
        String cardHolderName = cardInfo.get("NameOnCard");
        String cardAddressLine1 = cardInfo.get("BillingStreet");
        String cardAddressLine2 = cardInfo.get("BillingStreet2");
        String cardState = cardInfo.get("BillingState");
        String cardPostalCode = cardInfo.get("BillingZip");

        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment().toLowerCase().replace("_cm", ""), "UI booking");
        retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getTpId());
        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        retrieveBalance.setLocationId(getLocationId());
        retrieveBalance.sendRequest();
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for TP ID [" + getTpId() + "]: " + retrieveBalance.getFaultString());

        CreateSettlementMethod settlement = new CreateSettlementMethod(getEnvironment().toLowerCase().replace("_cm", ""), "Main");
        settlement.setFolioId(retrieveBalance.getFolioId());
        if (expressCheckout.equalsIgnoreCase("true")) {
            settlement.setExpressCheckout("true");
        }
        settlement.setSettlementMethod(cardPaymentMethod);
        settlement.setCardNumber(cardNumber);
        settlement.setCardName(cardHolderName);
        settlement.setCardAddressLine1(cardAddressLine1);
        String method = cardPaymentMethod;
        if (cardPaymentMethod.equalsIgnoreCase("DINERS CLUB")) {
            method = "DINERS_CLUB";
        }
        if (cardPaymentMethod.equalsIgnoreCase("American Express")) {
            method = "AMEX";
        }
        settlement.setRetreivalReferenceNumber(cardNumber, cardExpirationMonth + "/" + cardExpirationYear, method.replace(" ", "").toUpperCase());
        if (cardAddressLine2.isEmpty() || cardAddressLine2 == null || cardAddressLine2.equalsIgnoreCase("null")) {
            settlement.removeAddressLine2();
        } else {
            settlement.setCardAddressLine2(cardAddressLine2);
        }
        settlement.setCardPostalCode(cardPostalCode);
        settlement.setCardState(cardState);
        settlement.setCardExpirationMonth(cardExpirationMonth);
        settlement.setCardExpirationYear(cardExpirationYear);
        settlement.sendRequest();
        TestReporter.assertEquals(settlement.getResponseStatusCode(), "200", "Verify that no error occurred creating a settlement method: " + settlement.getFaultString());
    }

    /**
     * * This method retrieves folio information for a reservation and the makes a
     * * payment that satisfies the deposit requirement.
     *
     */

    public void makeFullPayment() {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
        retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getTpId());

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        sqlTpId = getTpId();
        String sql = "select d.WRK_LOC_ID "
                + "from rsrc_inv.wrk_loc d "
                + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + sqlTpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.fac_id is not null )";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            setLocationId(rs.getValue("WRK_LOC_ID", i));

            retrieveBalance.setLocationId(getLocationId());
            retrieveBalance.sendRequest();
            if (retrieveBalance.getResponseStatusCode().equals("200")) {
                break;
            }
        }
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for the reservation: " + retrieveBalance.getFaultString());

        PostCardPayment postPayment = new PostCardPayment(getEnvironment(), "VisaCreditCard");
        setPaymentAmount(retrieveBalance.getPaymentRequired());
        postPayment.setAmount(getPaymentAmount());
        postPayment.setFolioId(retrieveBalance.getFolioId());
        postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, getTpId());
        postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, getTcId());
        postPayment.setLocationId(getLocationId());
        postPayment.setPartyId(getPrimaryPartyId());
        postPayment.setPrimaryLastname(getHh().primaryGuest().getFirstName());
        postPayment.setTravelPlanId(getTpId());
        postPayment.setTravelPlanSegmentId(getTpsId());
        postPayment.setRetreivalReferenceNumber();
        postPayment.sendRequest();
        TestReporter.assertEquals(postPayment.getResponseStatusCode(), "200", "Response was not 200");
        TestReporter.log("Payment ID: " + postPayment.getPaymentId());
    }

    /**
     * This method retrieves folio information for a reservation and the makes a
     * payment that satisfies the deposit requirement.
     */
    public void makeFirstNightDeposit() {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
        retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getTpId());

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        sqlTpId = getTpId();
        String sql = "select d.WRK_LOC_ID "
                + "from rsrc_inv.wrk_loc d "
                + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + sqlTpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.fac_id is not null )";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            setLocationId(rs.getValue("WRK_LOC_ID", i));

            retrieveBalance.setLocationId(getLocationId());
            retrieveBalance.sendRequest();
            if (retrieveBalance.getResponseStatusCode().equals("200")) {
                break;
            }
        }
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for the reservation: " + retrieveBalance.getFaultString());

        PostCardPayment postPayment = new PostCardPayment(getEnvironment(), "VisaCreditCard");
        setPaymentAmount(retrieveBalance.getDepositRequired());
        postPayment.setAmount(getPaymentAmount());
        postPayment.setFolioId(retrieveBalance.getFolioId());
        postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, getTpId());
        postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, getTcId());
        postPayment.setLocationId(getLocationId());
        postPayment.setPartyId(getPrimaryPartyId());
        postPayment.setPrimaryLastname(getHh().primaryGuest().getFirstName());
        postPayment.setTravelPlanId(getTpId());
        postPayment.setTravelPlanSegmentId(getTpsId());
        postPayment.setRetreivalReferenceNumber();
        postPayment.sendRequest();
        TestReporter.assertEquals(postPayment.getResponseStatusCode(), "200", "Response was not 200");
        TestReporter.log("Payment ID: " + postPayment.getPaymentId());
    }

    public void retrieveReservation() {
        Retrieve retrieve = new Retrieve(getEnvironment(), "Main");
        retrieve.setRequestNodeValueByXPath("//request/travelPlanId", getTpId());
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams.getLocationIdByTpId(getTpId())));

        do {
            retrieve.setRequestNodeValueByXPath("//request/locationId", rs.getValue("WRK_LOC_ID"));
            retrieve.sendRequest();
            if (retrieve.getResponseStatusCode().equals("200")) {
                setLocationId(rs.getValue("WRK_LOC_ID"));
                break;
            } else {
                rs.moveNext();
            }
        } while (rs.hasNext());

        TestReporter.assertTrue(retrieve.getResponseStatusCode().equals("200"), "Verify that an error did not occurred retrieving the prereq reservation: " + retrieve.getFaultString());
        setPrimaryPartyId(retrieve.getPartyId());
        setPrimaryGuestId(retrieve.getGuestId());
    }
}
