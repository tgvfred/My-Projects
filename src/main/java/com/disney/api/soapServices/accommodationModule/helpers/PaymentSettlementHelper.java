package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
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
    private Book book;
    private String environment;
    private String locationId;
    private HouseHold hh;
    private String primaryGuestId;
    private String primaryPartyId;
    private String paymentAmount;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

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

    public PaymentSettlementHelper(String environment, Book book, HouseHold hh) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (book == null) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setBook(book);
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
        String cardNumber = cardInfo.get("AccountNumber").replace("-", "");
        String cardExpirationMonth = cardInfo.get("ExpMonth");
        String cardExpirationYear = cardInfo.get("ExpYear");
        String cardHolderName = cardInfo.get("NameOnCard");
        String cardAddressLine1 = cardInfo.get("BillingStreet");
        String cardAddressLine2 = cardInfo.get("BillingStreet2");
        String cardState = cardInfo.get("BillingState");
        String cardPostalCode = cardInfo.get("BillingZip");

        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment().toLowerCase().replace("_cm", ""), "UI booking");
        retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getBook().getTravelPlanId());
        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        retrieveBalance.setLocationId(getLocationId());
        retrieveBalance.sendRequest();
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for TP ID [" + getBook().getTravelPlanId() + "]: " + retrieveBalance.getFaultString());

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
     * This method retrieves folio information for a reservation and the makes a
     * payment that satisfies the deposit requirement.
     */
    public void makeFirstNightDeposit() {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
        retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getBook().getTravelPlanId());

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        sqlTpId = getBook().getTravelPlanId();
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

        PostCardPayment postPayment = new PostCardPayment(getEnvironment(), "Visa-CreditCard");
        setPaymentAmount(retrieveBalance.getDepositRequired());
        postPayment.setAmount(getPaymentAmount());
        postPayment.setFolioId(retrieveBalance.getFolioId());
        postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, getBook().getTravelPlanId());
        postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, getBook().getTravelComponentId());
        postPayment.setLocationId(getLocationId());
        postPayment.setPartyId(getPrimaryPartyId());
        postPayment.setPrimaryLastname(getBook().getPrimaryGuestLastName());
        postPayment.setTravelPlanId(getBook().getTravelPlanId());
        postPayment.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        postPayment.setRetreivalReferenceNumber();
        postPayment.sendRequest();
        TestReporter.assertEquals(postPayment.getResponseStatusCode(), "200", "Response was not 200");
        TestReporter.log("Payment ID: " + postPayment.getPaymentId());
    }

    public void retrieveReservation() {
        Retrieve retrieve = new Retrieve(getEnvironment(), "Main");
        retrieve.setRequestNodeValueByXPath("//request/travelPlanId", getBook().getTravelPlanId());
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams.getLocationIdByTpId(getBook().getTravelPlanId())));

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
