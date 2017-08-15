package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.disney.AutomationException;
import com.disney.api.DVCSalesBaseTest;
import com.disney.api.restServices.BaseRestTest;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort.operations.FindRoomForReservation;
import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.CheckingIn;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.availabilityWSPort.operations.FreezeInventory;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.RetrieveFolioBalanceDue;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCardPayment;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindMiscPackages;
import com.disney.api.soapServices.roomInventoryModule.accommodationAssignmentServicePort.operations.AssignRoomForReservation;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.ResortInfo;
import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.DVCSalesDreams;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class AccommodationBaseTest extends BaseRestTest {
    public final static String GATHERING_ID = "gatheringId";
    public final static String GATHERING_NAME = "gatheringName";
    public final static String GATHERING_TYPE = "gatheringType";
    public final static String COMMENT_TYPE = "commentType";
    public final static String COMMENT_TEXT = "commentText";
    public final static String COMMENT_TO = "to";
    public final static String COMMENT_FROM = "from";
    public final static String MEMBERSHIP_EXP_DATE = "expirationDate";
    public final static String MEMBERSHIP_TYPE = "memberShipType";
    public final static String MEMBERSHIP_ID = "membershipId";
    public final static String MEMBERSHIP_POLICY_ID = "policyId";
    public final static String MEMBERSHIP_PROD_CHANNEL_ID = "productChannelId";
    public final static String MEMBERSHIP_GUEST_MEMBERSHIP_ID = "guestMembershipId";

    protected static String environment;
    protected ThreadLocal<Integer> daysOut = new ThreadLocal<Integer>();
    protected ThreadLocal<Integer> nights = new ThreadLocal<Integer>();
    protected ThreadLocal<String> arrivalDate = new ThreadLocal<String>();
    protected ThreadLocal<String> departureDate = new ThreadLocal<String>();
    private ThreadLocal<String> locationId = new ThreadLocal<String>();
    private ThreadLocal<String> resortCode = new ThreadLocal<String>();
    private ThreadLocal<String> facilityId = new ThreadLocal<String>();
    private ThreadLocal<String> campusId = new ThreadLocal<String>();
    private ThreadLocal<String> sourceAccoutingCenter = new ThreadLocal<String>();
    private ThreadLocal<String> roomTypeCode = new ThreadLocal<String>();
    private ThreadLocal<String> agencyId = new ThreadLocal<String>();
    private ThreadLocal<String> guestId = new ThreadLocal<String>();
    private ThreadLocal<String> partyId = new ThreadLocal<String>();
    private ThreadLocal<String> packageCode = new ThreadLocal<String>();
    private ThreadLocal<String> guestAddressLocatorId = new ThreadLocal<String>();
    protected ThreadLocal<Boolean> skipExternalRef = new ThreadLocal<Boolean>();
    private ThreadLocal<String> externalRefNumber = new ThreadLocal<String>();
    protected static String externalRefSource = "DPMSProperty";
    private static String[][] roomTypeAndFacInfo = new String[40][6];
    private Map<String, String> noPackageCodes = new HashMap<String, String>();
    protected ThreadLocal<Boolean> fixedDates = new ThreadLocal<Boolean>();
    private ThreadLocal<HouseHold> hh = new ThreadLocal<HouseHold>();
    private ThreadLocal<ReplaceAllForTravelPlanSegment> book = new ThreadLocal<>();
    private ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>();
    protected ThreadLocal<String> tpId = new ThreadLocal<String>();
    protected ThreadLocal<String> tpsId = new ThreadLocal<String>();
    protected ThreadLocal<String> tcgId = new ThreadLocal<String>();
    protected ThreadLocal<String> tcId = new ThreadLocal<String>();
    protected String isComo = "";
    private ThreadLocal<Boolean> skipCancel = new ThreadLocal<Boolean>();
    private ThreadLocal<String> ageType = new ThreadLocal<String>();
    private ThreadLocal<String> age = new ThreadLocal<String>();
    private ThreadLocal<String> packageBillCode = new ThreadLocal<>();
    private ThreadLocal<String> packageDescription = new ThreadLocal<>();
    private ThreadLocal<String> packageType = new ThreadLocal<>();
    private ThreadLocal<Boolean> isWdtcBooking = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isLibgoBooking = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isADA = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isBundle = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isDining = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isRSR = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> isShared = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addGuest = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addChildGuest = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addNewGuest = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> skipDeposit = new ThreadLocal<Boolean>();
    private ThreadLocal<String> firstDiningTcg = new ThreadLocal<String>();
    private AddBundle add;
    private RetrieveDetailsByTravelPlanId details;
    private ThreadLocal<String> bundleTcg = new ThreadLocal<>();
    private ScheduledEventReservation diningRes;
    private ThreadLocal<Boolean> sendRequest = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> setTickets = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addTickets = new ThreadLocal<Boolean>();
    private ThreadLocal<String> ticketDescription = new ThreadLocal<>();
    private ThreadLocal<Boolean> bypassFreeze = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addConfirmatinoDetails = new ThreadLocal<Boolean>();
    private ThreadLocal<Boolean> addGathering = new ThreadLocal<Boolean>();
    private ThreadLocal<Map<String, String>> gatheringData = new ThreadLocal<>();
    private ThreadLocal<Boolean> addComments = new ThreadLocal<>();
    private ThreadLocal<Map<String, String>> comments = new ThreadLocal<>();
    private ThreadLocal<Boolean> addInternalComments = new ThreadLocal<>();
    private ThreadLocal<Boolean> addRoomResDetailsComments = new ThreadLocal<>();
    private ThreadLocal<Map<Integer, Guest>> additionalGuests = new ThreadLocal<Map<Integer, Guest>>();
    private ThreadLocal<TicketsHelper> ticketsHelper = new ThreadLocal<>();
    private ThreadLocal<Boolean> nonZeroVip = new ThreadLocal<>();
    private ThreadLocal<String> vipLevel = new ThreadLocal<>();
    private ThreadLocal<Boolean> addPrimaryGuestMembership = new ThreadLocal<>();
    private ThreadLocal<Map<String, String>> membershipData = new ThreadLocal<>();
    private ThreadLocal<Boolean> addPrimaryGuestODS = new ThreadLocal<>();
    private ThreadLocal<Boolean> addTravelAgency = new ThreadLocal<>();

    protected void addToNoPackageCodes(String key, String value) {
        noPackageCodes.put(key, value);
    }

    public static void setEnvironment(String env) {
        environment = env;
    }

    protected void setFacilityId(String facilityId) {
        this.facilityId.set(facilityId);
    }

    protected void setLocationId(String locationId) {
        this.locationId.set(locationId);
    }

    protected void setResortCode(String resortCode) {
        this.resortCode.set(resortCode);
    }

    protected void setSourceAccountingCenter(String sourceAccoutingCenter) {
        this.sourceAccoutingCenter.set(sourceAccoutingCenter);
    }

    protected void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode.set(roomTypeCode);
    }

    protected void setAgencyId(String agencyId) {
        this.agencyId.set(agencyId);
    }

    protected void setNights(int nights) {
        this.nights.set(nights);
    }

    protected void setDaysOut(int daysOut) {
        this.daysOut.set(daysOut);
    }

    protected void setArrivalDate(String arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }

    protected void setDepartureDate(String departureDate) {
        this.departureDate.set(departureDate);
    }

    protected void setDepartureDate(int nights) {
        setNights(nights);
        this.departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
    }

    protected void setArrivalDate(int daysOut) {
        setDaysOut(daysOut);
        this.arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
    }

    protected void setBook(ReplaceAllForTravelPlanSegment book) {
        this.book.set(book);
    }

    protected void setRetrieve(Retrieve retrieve) {
        this.retrieve.set(retrieve);
    }

    protected void setFixedDates(Boolean fixedDates) {
        this.fixedDates.set(fixedDates);
    }

    protected void setSkipExternalRef(Boolean skip) {
        skipExternalRef.set(skip);
    }

    protected void setTpId(String tpId) {
        this.tpId.set(tpId);
    }

    protected void setTpsId(String tpsId) {
        this.tpsId.set(tpsId);
    }

    protected void setTcgId(String tcgId) {
        this.tcgId.set(tcgId);
    }

    protected void setTcId(String tcId) {
        this.tcId.set(tcId);
    }

    protected String getTpId() {
        return this.tpId.get();
    }

    protected String getTpsId() {
        return this.tpsId.get();
    }

    protected String getTcgId() {
        return this.tcgId.get();
    }

    protected String getTcId() {
        return this.tcId.get();
    }

    protected void setCampusId(String campusId) {
        this.campusId.set(campusId);
    }

    public static String getEnvironment() {
        return environment;
    }

    public int getDaysOut() {
        return daysOut.get();
    }

    public int getNights() {
        return nights.get();
    }

    public String getArrivalDate() {
        return arrivalDate.get();
    }

    public String getDepartureDate() {
        return departureDate.get();
    }

    public String getLocationId() {
        return locationId.get();
    }

    public String getResortCode() {
        return resortCode.get();
    }

    public String getFacilityId() {
        return facilityId.get();
    }

    public String getSourceAccountingCenter() {
        return sourceAccoutingCenter.get();
    }

    public String getRoomTypeCode() {
        return roomTypeCode.get();
    }

    public String getGuestId() {
        return guestId.get();
    }

    public String getPartyId() {
        return partyId.get();
    }

    public String getPackageCode() {
        return packageCode.get();
    }

    public String getAddressGuestLocatorId() {
        return guestAddressLocatorId.get();
    }

    public String getExternalRefNumber() {
        return externalRefNumber.get();
    }

    public void setExternalRefNumber(String extRefNum) {
        externalRefNumber.set(extRefNum);
    }

    public String getExternalRefSource() {
        return externalRefSource;
    }

    public String getCampusId() {
        return campusId.get();
    }

    public Map<String, String> getNoPackageCodes() {
        return noPackageCodes;
    }

    public HouseHold getHouseHold() {
        return hh.get();
    }

    public void setHouseHold(HouseHold hh) {
        this.hh.set(hh);
    }

    public ReplaceAllForTravelPlanSegment getBook() {
        return book.get();
    }

    public Retrieve getRetrieve() {
        return retrieve.get();
    }

    public void setAgeType(String ageType) {
        this.ageType.set(ageType);
    }

    public String getAgeType() {
        return ageType.get();
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getAge() {
        return age.get();
    }

    public void setPackageBillCode(String packageBillCode) {
        this.packageBillCode.set(packageBillCode);
    }

    public String getPackageBillCode() {
        return packageBillCode.get();
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription.set(packageDescription);
    }

    public String getPackageDescription() {
        return packageDescription.get();
    }

    public void setPackageType(String packageType) {
        this.packageType.set(packageType);
    }

    public String getPackageType() {
        return packageType.get();
    }

    /**
     * This flag is used to determine if a particular wholesaler (Group# 01825) is to be used for booking
     *
     * @param isLibgoBooking
     */
    public void setIsWdtcBooking(Boolean isWdtcBooking) {
        this.isWdtcBooking.set(isWdtcBooking);
    }

    /**
     * This flag is used to determine if a particular wholesaler (Group# 01825) is to be used for booking
     *
     * @param isLibgoBooking
     */
    public Boolean isWdtcBooking() {
        return this.isWdtcBooking.get();
    }

    public void setIsADA(Boolean isADA) {
        this.isADA.set(isADA);
    }

    public Boolean isADA() {
        return this.isADA.get();
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle.set(isBundle);
    }

    public Boolean isBundle() {
        return this.isBundle.get();
    }

    public void setIsDining(Boolean isDining) {
        this.isDining.set(isDining);
    }

    public Boolean isDining() {
        return this.isDining.get();
    }

    public void setIsRSR(Boolean isRSR) {
        this.isRSR.set(isRSR);
    }

    public Boolean isRSR() {
        return this.isRSR.get();
    }

    public void setIsShared(Boolean isShared) {
        this.isShared.set(isShared);
    }

    public Boolean isShared() {
        return this.isShared.get();
    }

    public void setAddGuest(Boolean addGuest) {
        this.addGuest.set(addGuest);
    }

    public Boolean getAddGuest() {
        return this.addGuest.get();
    }

    public void setAddChildGuest(Boolean addChildGuest) {
        setAddNewGuest(addChildGuest);
        this.addChildGuest.set(addChildGuest);
    }

    public Boolean getAddChildGuest() {
        return this.addChildGuest.get();
    }

    public void setSkipDeposit(Boolean skipDeposit) {
        this.skipDeposit.set(skipDeposit);
    }

    public Boolean getSkipDeposit() {
        return this.skipDeposit.get();
    }

    public void setSendRequest(Boolean sendRequest) {
        this.sendRequest.set(sendRequest);
    }

    public Boolean getSendRequest() {
        return this.sendRequest.get();
    }

    public void setAddNewGuest(Boolean addNewGuest) {
        this.addNewGuest.set(addNewGuest);
        this.addGuest.set(addNewGuest);
    }

    public Boolean getAddNewGuest() {
        return this.addNewGuest.get();
    }

    public String getFirstDiningTcg() {
        return this.firstDiningTcg.get();
    }

    public void setBundleTcg(String bundleTcg) {
        this.bundleTcg.set(bundleTcg);
    }

    public String getBundleTcg() {
        return this.bundleTcg.get();
    }

    public void setAddTickets(Boolean addTickets) {
        this.addTickets.set(addTickets);
    }

    public void setAddTickets(String ticketDescription) {
        this.setTicketDescription(ticketDescription);
        this.addTickets.set(true);
    }

    public Boolean getAddTickets() {
        return this.addTickets.get();
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription.set(ticketDescription);
    }

    public String getTicketDescription() {
        return this.ticketDescription.get();
    }

    public void setSetTickets(Boolean setTickets) {
        this.setTickets.set(setTickets);
    }

    public void setSetTickets(String ticketDescription) {
        this.setTicketDescription(ticketDescription);
        this.setTickets.set(true);
    }

    public Boolean getSetTickets() {
        return this.setTickets.get();
    }

    public void setBypassFreeze(Boolean bypassFreeze) {
        this.bypassFreeze.set(bypassFreeze);
    }

    public Boolean getBypassFreeze() {
        return this.bypassFreeze.get();
    }

    public void setAddConfirmationDetails(Boolean addConfirmatinoDetails) {
        this.addConfirmatinoDetails.set(addConfirmatinoDetails);
    }

    public Boolean getAddConfirmationDetails() {
        return this.addConfirmatinoDetails.get();
    }

    public void setAddGathering(Boolean addGathering) {
        this.addGathering.set(addGathering);
    }

    public Boolean getAddGathering() {
        return this.addGathering.get();
    }

    public void setGatheringData(Map<String, String> gatheringData) {
        setAddGathering(true);
        this.gatheringData.set(gatheringData);
    }

    public Map<String, String> getGatheringData() {
        return this.gatheringData.get();
    }

    public void setMembershipData(Map<String, String> membershipData) {
        setAddPrimaryGuestMembership(true);
        this.membershipData.set(membershipData);
    }

    public Map<String, String> getMembershipData() {
        return this.membershipData.get();
    }

    public void setAddComments(Boolean addComments) {
        this.addComments.set(addComments);
    }

    public Boolean getAddComments() {
        return this.addComments.get();
    }

    public void setAddInternalComments(Boolean addComments) {
        setAddComments(addComments);
        this.addInternalComments.set(addComments);
    }

    public Boolean getAddInternalComments() {
        return this.addInternalComments.get();
    }

    public void setAddRoomResDetailsComments(Boolean addComments) {
        setAddComments(addComments);
        this.addRoomResDetailsComments.set(addComments);
    }

    public Boolean getAddRoomResDetailsComments() {
        return this.addRoomResDetailsComments.get();
    }

    public void setNonZeroVip(Boolean setNonZeroVip) {
        this.nonZeroVip.set(setNonZeroVip);
    }

    public Boolean getNonZeroVip() {
        return this.nonZeroVip.get();
    }

    public void setCommentsData(HashMap<String, String> comments) {
        setAddComments(true);
        this.comments.set(comments);
    }

    public Map<String, String> getCommentsData() {
        return this.comments.get();
    }

    public void setAdditionalGuests(HashMap<Integer, Guest> additionalGuests) {
        this.additionalGuests.set(additionalGuests);
    }

    public Map<Integer, Guest> getAdditionalGuests() {
        return this.additionalGuests.get();
    }

    /**
     * This flag is used to determine if a particular wholesaler (Group# 01905) is to be used for booking
     *
     * @param isLibgoBooking
     */
    public void setIsLibgoBooking(Boolean isLibgoBooking) {
        this.isLibgoBooking.set(isLibgoBooking);
    }

    /**
     * This flag is used to determine if a particular wholesaler (Group# 01905) is to be used for booking
     *
     * @param isLibgoBooking
     */
    public Boolean getIsLibgoBooking() {
        return this.isLibgoBooking.get();
    }

    public void setTicketsHelper(TicketsHelper ticketsHelper) {
        this.ticketsHelper.set(ticketsHelper);
    }

    public TicketsHelper getTicketsHelper() {
        return this.ticketsHelper.get();
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel.set(vipLevel);
    }

    public String getVipLevel() {
        return this.vipLevel.get();
    }

    public void setAddPrimaryGuestMembership(Boolean addPrimaryGuestMembership) {
        this.addPrimaryGuestMembership.set(addPrimaryGuestMembership);
    }

    public Boolean getAddPrimaryGuestMembership() {
        return this.addPrimaryGuestMembership.get();
    }

    public void setAddPrimaryGuestODS(Boolean addPrimaryGuestODS) {
        this.addPrimaryGuestODS.set(addPrimaryGuestODS);
    }

    public Boolean getAddPrimaryGuestODS() {
        return this.addPrimaryGuestODS.get();
    }

    public void setAddTravelAgency(Boolean addTravelAgency) {
        this.addTravelAgency.set(addTravelAgency);
    }

    public Boolean getAddTravelAgency() {
        return this.addTravelAgency.get();
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters("environment")
    public void beforeSuite(String environment) {
        skipCancel.set(false);
        BaseSoapService.isExactResponseRequired(false);
        this.isComo = System.getenv("isComo") == null ? "false" : System.getenv("isComo");
        setEnvironment(environment);
        String dbEnv = "";
        if (getEnvironment().toLowerCase().contains("_cm")) {
            dbEnv = getEnvironment().toLowerCase().replace("_cm", "");
        } else {
            dbEnv = getEnvironment();
        }
        Database db = new OracleDatabase(dbEnv, "DREAMS");
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getRoomTypesWithHighRoomCounts()));
        for (int i = 0; i < roomTypeAndFacInfo.length; i++) {
            roomTypeAndFacInfo[i][0] = rs.getValue("NUMROOMS", i + 1);
            roomTypeAndFacInfo[i][1] = rs.getValue("ROOM_TYPE", i + 1);
            roomTypeAndFacInfo[i][2] = rs.getValue("RESORT", i + 1);
            roomTypeAndFacInfo[i][3] = rs.getValue("ROOM_DESC", i + 1);
            roomTypeAndFacInfo[i][4] = rs.getValue("RSRT_FAC_ID", i + 1);
            roomTypeAndFacInfo[i][5] = rs.getValue("LOC_ID", i + 1);
            TestReporter.logStep("**NUMBER OF ROOMS: " + roomTypeAndFacInfo[i][0] +
                    " **ROOM TYPE: " + roomTypeAndFacInfo[i][1] +
                    " **RESORT: " + roomTypeAndFacInfo[i][2] +
                    " **ROOM DESCRIPTION: " + roomTypeAndFacInfo[i][3] +
                    " **FACILITY ID: " + roomTypeAndFacInfo[i][4] +
                    " **LOCATION ID: " + roomTypeAndFacInfo[i][5]);
            System.out.println("**NUMBER OF ROOMS: " + roomTypeAndFacInfo[i][0] +
                    " **ROOM TYPE: " + roomTypeAndFacInfo[i][1] +
                    " **RESORT: " + roomTypeAndFacInfo[i][2] +
                    " **ROOM DESCRIPTION: " + roomTypeAndFacInfo[i][3] +
                    " **FACILITY ID: " + roomTypeAndFacInfo[i][4] +
                    " **LOCATION ID: " + roomTypeAndFacInfo[i][5]);
        }
        setSendRequest(true);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        daysOut.set(Randomness.randomNumberBetween(1, 12));
        nights.set(Randomness.randomNumberBetween(1, 3));
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));

        setIsWdtcBooking(false);
        setValues();
        setSendRequest(true);
        bookReservation();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            if (skipCancel == null || skipCancel.get() == null || skipCancel.get() != true) {
                cancel();
            }
        } catch (Exception e) {
        }
    }

    public void cancel() {
        Cancel cancel = new Cancel(getEnvironment(), "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        if (getBook() != null) {
            if (getBook().getTravelComponentGroupingId() != null) {
                cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
            }
        } else {
            cancel.setTravelComponentGroupingId(tcgId.get());
        }
        cancel.sendRequest();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        String mesage = noPackageCodes.values().toString();
        String[] messages = mesage.substring(1, mesage.length() - 1).split(":");
        System.out.println("*********************************************************************************");
        System.out.println("************** START: OUTPUT INVALID PACKAGE CODE SEARCH CONFIGS ****************");
        System.out.println("*********************************************************************************");
        for (int i = 0; i < messages.length; i++) {
            System.out.println(messages[i]);
        }
        System.out.println("*********************************************************************************");
        System.out.println("*************** END: OUTPUT INVALID PACKAGE CODE SEARCH CONFIGS *****************");
        System.out.println("*********************************************************************************");
    }

    public void createHouseHold() {
        hh.set(new HouseHold(1));
    }

    public void bookReservation() {
        if (getHouseHold() == null) {
            createHouseHold();
            getHouseHold().primaryGuest().primaryAddress().setCity("Winston Salem");
        }

        book.set(new ReplaceAllForTravelPlanSegment(getEnvironment(), "roomOnlyWithoutTickets"));

        if (skipExternalRef.get() == null || skipExternalRef.get() == false) {
            externalRefNumber.set(Randomness.randomNumber(12));
            getBook().setExternalReference(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), getExternalRefSource(), "RESERVATION");
            getBook().setRoomDetails_ExternalRefs(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), getExternalRefSource(), "RESERVATION");
        }

        PackageCodes pkg = new PackageCodes();
        int maxTries = 10;
        int tries = 0;
        do {
            getBook().setAreaPeriod(Randomness.generateCurrentXMLDate(getDaysOut()), Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
            getBook().setRoomDetails_ResortPeriod(Randomness.generateCurrentXMLDate(getDaysOut()), Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
            getBook().setRoomDetailsBookingDate(Randomness.generateCurrentXMLDate());

            if (isWdtcBooking() != null && isWdtcBooking() == true) {
                setPackageBillCode("*WDTC");
                setPackageDescription("R MYW Pkg + Deluxe Dining");
                setPackageType("WDW PKG");
                try {
                    getBook().setRoomDetailsBlockCode("01825");
                } catch (XPathNotFoundException e) {
                    getBook().setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("blockCode"));
                    getBook().setRoomDetailsBlockCode("01825");
                }
                if (skipExternalRef.get() == null || skipExternalRef.get() == false) {
                    getBook().setExternalReference("01825", getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
                    getBook().setRoomDetails_ExternalRefs("01825", getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
                }
            } else if (isValid(getIsLibgoBooking()) && getIsLibgoBooking() == true) {
                setPackageBillCode("*DWSL");
                setPackageDescription("ANN MYW Pkg + Dining");
                setPackageType("WHOLESALE");
                try {
                    getBook().setRoomDetailsBlockCode("01905");
                } catch (XPathNotFoundException e) {
                    getBook().setRequestNodeValueByXPath("//replaceAllForTravelPlanSegment/request/roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("blockCode"));
                    getBook().setRoomDetailsBlockCode("01905");
                }
                if (skipExternalRef.get() == null || skipExternalRef.get() == false) {
                    getBook().setExternalReference("01905", getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
                    getBook().setRoomDetails_ExternalRefs("01905", getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
                }
            } else {
                setPackageBillCode("");
                setPackageDescription("");
                setPackageType("DRC RO");
            }
            pkg = new PackageCodes();
            boolean success = false;
            int pkgMaxTries = 15;
            int pkgTries = 0;
            do {
                try {
                    packageCode.set(pkg.retrievePackageCode(getEnvironment(), String.valueOf(getDaysOut()),
                            getLocationId(), getPackageType(), getPackageBillCode(), getResortCode(), getRoomTypeCode(), getPackageDescription()));
                    success = true;
                } catch (AssertionError e) {
                    setValues();
                    pkg.setUseBookingDates(false);
                }
                pkgTries++;
            } while (!success && pkgTries < pkgMaxTries);
            getBook().setRoomDetailsPackageCode(getPackageCode());
            getBook().setRoomDetailsResortCode(getResortCode());
            getBook().setRoomDetailsRoomTypeCode(getRoomTypeCode());
            getBook().setRoomDetailsLocationId(getLocationId());
            getBook().setRoomDetails_RoomReservationDetail_GuestRefDetails(getHouseHold().primaryGuest());
            getBook().setTravelPlanGuest(getHouseHold().primaryGuest());
            // getBook().setRoomDetails_RoomReservationDetail_GuestRefDetails(getHouseHold().primaryGuest());

            if (isADA() != null && isADA() == true) {
                getBook().setRoomDetailsSpecialNeedsRequested("true");
            }

            if (isRSR() != null && isRSR() == true) {
                getBook().setRoomDetailsRsrReservation("true");
            }

            if (isShared() != null && isShared() == true) {
                getBook().setRoomDetailsShared("true");
            }

            if (getAddGuest() != null && getAddGuest() == true) {
                addGuest();
            }

            if (isValid(getSetTickets()) && getSetTickets() == true) {
                if ((isValid(isWdtcBooking()) && isWdtcBooking() == true) || (isValid(getIsLibgoBooking()) && getIsLibgoBooking())) {
                    ticketsHelper.set(new TicketsHelper(getEnvironment(), getBook(), getPackageCode()));
                } else {
                    ticketsHelper.set(new TicketsHelper(getEnvironment(), getBook()));
                }
                ticketsHelper.get().setAdultTicket(true);
                if (isValid(getTicketDescription())) {
                    ticketsHelper.get().setTickets(getTicketDescription(), getHouseHold().primaryGuest());
                } else {
                    ticketsHelper.get().setTickets("2 Day Base Ticket", getHouseHold().primaryGuest());
                }
            }

            if (isValid(getAddTickets()) && getAddTickets() == true) {
                if ((isValid(isWdtcBooking()) && isWdtcBooking() == true) || (isValid(getIsLibgoBooking()) && getIsLibgoBooking())) {
                    ticketsHelper.set(new TicketsHelper(getEnvironment(), getBook(), getPackageCode()));
                } else {
                    ticketsHelper.set(new TicketsHelper(getEnvironment(), getBook()));
                }
                ticketsHelper.get().setAdultTicket(true);
                if (isValid(getTicketDescription())) {
                    ticketsHelper.get().addTickets(getTicketDescription(), getHouseHold().primaryGuest());
                } else {
                    ticketsHelper.get().addTickets("2 Day Base Ticket", getHouseHold().primaryGuest());
                }
            }

            if (isValid(getBypassFreeze()) && getBypassFreeze() == true) {
                try {
                    getBook().setByPassFreeze("true");
                } catch (XPathNotFoundException e) {
                    getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request", BaseSoapCommands.ADD_NODE.commandAppend("byPassFreeze"));
                    getBook().setByPassFreeze("true");
                }
            }

            if (isValid(getAddConfirmationDetails()) && getAddConfirmationDetails() == true) {
                getBook().setConfirmationDetails("0", "true", "Email", "true", "true", "1", "0", "0", getHouseHold().primaryGuest());
            }

            if (isValid(getAddGathering()) && getAddGathering() == true) {
                addGathering();
            }

            if (isValid(getAddComments())) {
                if (isValid(getAddInternalComments()) && getAddInternalComments() == true) {
                    setCommentsData(new HashMap<String, String>());
                    getCommentsData().put(COMMENT_TEXT, Randomness.randomString(20));
                    getCommentsData().put(COMMENT_TYPE, Randomness.randomString(20));

                    getBook().setInternalComments(getCommentsData().get(COMMENT_TEXT), getCommentsData().get(COMMENT_TYPE), BaseSoapCommands.REMOVE_NODE.toString());
                }
                if (isValid(getAddRoomResDetailsComments()) && getAddRoomResDetailsComments() == true) {
                    setCommentsData(new HashMap<String, String>());
                    getCommentsData().put(COMMENT_TEXT, Randomness.randomString(20));
                    getCommentsData().put(COMMENT_FROM, Randomness.randomString(20));
                    getCommentsData().put(COMMENT_TO, Randomness.randomString(20));

                    getBook().setRoomDetails_RoomReservationDetail_Comments(BaseSoapCommands.REMOVE_NODE.toString(),
                            getCommentsData().get(COMMENT_TEXT),
                            "true",
                            getCommentsData().get(COMMENT_FROM),
                            "CREUN",
                            getCommentsData().get(COMMENT_TO));
                }
            }

            if (isValid(getNonZeroVip())) {
                int intVipLevel = Randomness.randomNumberBetween(1, 4);
                setVipLevel(null);
                switch (intVipLevel) {
                    case 1:
                        setVipLevel("ONE");
                        break;
                    case 2:
                        setVipLevel("TWO");
                        break;
                    case 3:
                        setVipLevel("THREE");
                        break;
                    default:
                        setVipLevel("FOUR");
                        break;
                }
                getBook().setVipLevel(getVipLevel());
            }

            if (isValid(getAddPrimaryGuestMembership()) && getAddPrimaryGuestMembership() == true) {
                setMembershipData(new HashMap<String, String>());
                if (!isValid(getMembershipData())) {
                    getMembershipData().put(MEMBERSHIP_EXP_DATE, Randomness.generateCurrentXMLDate());
                    getMembershipData().put(MEMBERSHIP_GUEST_MEMBERSHIP_ID, Randomness.randomNumber(12));
                    getMembershipData().put(MEMBERSHIP_ID, Randomness.randomNumber(8));
                    getMembershipData().put(MEMBERSHIP_ID, getMembershipData().get(MEMBERSHIP_ID).startsWith("0") ? getMembershipData().get(MEMBERSHIP_ID).replaceFirst("0", "1") : getMembershipData().get(MEMBERSHIP_ID));
                    getMembershipData().put(MEMBERSHIP_POLICY_ID, Randomness.randomNumber(6));
                    getMembershipData().put(MEMBERSHIP_POLICY_ID, getMembershipData().get(MEMBERSHIP_POLICY_ID).startsWith("0") ? getMembershipData().get(MEMBERSHIP_POLICY_ID).replaceFirst("0", "1") : getMembershipData().get(MEMBERSHIP_POLICY_ID));
                    getMembershipData().put(MEMBERSHIP_PROD_CHANNEL_ID, Randomness.randomNumber(6));
                    getMembershipData().put(MEMBERSHIP_PROD_CHANNEL_ID, getMembershipData().get(MEMBERSHIP_PROD_CHANNEL_ID).startsWith("0") ? getMembershipData().get(MEMBERSHIP_PROD_CHANNEL_ID).replaceFirst("0", "1") : getMembershipData().get(MEMBERSHIP_PROD_CHANNEL_ID));
                    getMembershipData().put(MEMBERSHIP_TYPE, Randomness.randomString(8));
                }
                getBook().setRoomDetails_RoomReservationDetail_GuestRefDetails_MembershipDetails(getMembershipData().get(MEMBERSHIP_EXP_DATE),
                        getMembershipData().get(MEMBERSHIP_TYPE), getMembershipData().get(MEMBERSHIP_ID), getMembershipData().get(MEMBERSHIP_POLICY_ID),
                        getMembershipData().get(MEMBERSHIP_PROD_CHANNEL_ID), getMembershipData().get(MEMBERSHIP_GUEST_MEMBERSHIP_ID));
                getBook().setTravelPlanGuest_Guest_MembershipDetails(getMembershipData().get(MEMBERSHIP_EXP_DATE),
                        getMembershipData().get(MEMBERSHIP_TYPE), getMembershipData().get(MEMBERSHIP_ID), getMembershipData().get(MEMBERSHIP_POLICY_ID),
                        getMembershipData().get(MEMBERSHIP_PROD_CHANNEL_ID), getMembershipData().get(MEMBERSHIP_GUEST_MEMBERSHIP_ID));
            }

            if (isValid(getAddPrimaryGuestODS()) && getAddPrimaryGuestODS() == true) {
                if (getHouseHold().primaryGuest().getOdsId().equals("0")) {
                    getHouseHold().sendToApi(Environment.getBaseEnvironmentName(getEnvironment()));
                }
                getBook().setTravelPlanGuest_GuestIdReferences("ODS", getHouseHold().primaryGuest().getOdsId());
                getBook().setRoomDetails_RoomReservationDetail_GuestRefDetails_GuestIdRefs("ODS", getHouseHold().primaryGuest().getOdsId());
            }

            if (isValid(getAddTravelAgency()) && getAddTravelAgency() == true) {
                getBook().setTravelAgency("99999998");
            }

            if (getSendRequest() == null || getSendRequest() == true) {
                getBook().sendRequest();
                TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
                tries++;
            } else {
                tries = maxTries;
            }
        } while ((getSendRequest() == null ? !getBook().getResponseStatusCode().equals("200") : false) && tries < maxTries);

        if (getSendRequest() == null || getSendRequest() == true) {
            retrieveReservation();
        }

        if (isBundle() != null && isBundle() == true) {
            addBundle();
        }
        if (isDining() != null && isDining() == true) {
            addDining();
        }
    }

    public void addGathering() {
        if (!isValid(getGatheringData())) {
            createGathering();
        }
        addGathering(getGatheringData());
    }

    public void addGathering(Map<String, String> gatheringData) {
        getBook().setGatheringDetail(getGatheringData().get(GATHERING_ID), getGatheringData().get(GATHERING_NAME), getGatheringData().get(GATHERING_TYPE));
    }

    public void createGathering() {
        setGatheringData(new HashMap<String, String>());
        getGatheringData().put(GATHERING_ID, Randomness.randomString(12));
        getGatheringData().put(GATHERING_NAME, Randomness.randomString(12));
        getGatheringData().put(GATHERING_TYPE, "TW");
    }

    private void addBundle() {
        details = new RetrieveDetailsByTravelPlanId(environment, "Main");
        details.setTravelPlanId(getBook().getTravelPlanId());
        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());

        add = new AddBundle(environment, "Main");
        add.setGuestsGuestNameFirstName(hh.get().primaryGuest().getFirstName());
        add.setGuestsGuestNameLastName(hh.get().primaryGuest().getLastName());
        add.setGuestsGuestReferenceId(details.getGuestsId());
        add.setGuestsId(details.getGuestsId());
        add.setPackageBundleRequestsBookDate(Randomness.generateCurrentXMLDate());
        add.setPackageBundleRequestsContactName(hh.get().primaryGuest().getFirstName() + " " + hh.get().primaryGuest().getLastName());
        add.setPackageBundleRequestsEndDate(Randomness.generateCurrentXMLDate(getDaysOut() + 2) + "T00:00:00");
        add.setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(details.getGuestsId());
        add.setPackageBundleRequestsStartDate(Randomness.generateCurrentXMLDate(getDaysOut() + 1) + "T00:00:00");
        add.setTravelPlanId(getBook().getTravelPlanId());
        add.retrieveSalesOrderId(getBook().getTravelPlanId());
        add.setSalesOrderId(add.getBundleSalesOrderIds()[0]);

        FindMiscPackages find = new FindMiscPackages(environment, "MinimalInfo");
        find.setArrivalDate(Randomness.generateCurrentXMLDate(getDaysOut()));
        find.setBookDate(Randomness.generateCurrentXMLDate());
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred adding a bundle to TP ID [" + getBook().getTravelPlanId() + "]: " + add.getFaultString());
        add.setPackageBundleRequestsCode(find.getPackageCode());

        add.sendRequest();
        // convos.put("add", add.getRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@conversationId"));
        TestReporter.assertEquals(add.getResponseStatusCode(), "200", "An error occurred while adding a bundle.\nRequest:\n" + add.getRequest() + "\nResonse:\n" + add.getResponse());

        setBundleTcg(findBundleTcg(getBook().getTravelPlanId()));

        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());

        retrieveReservation();
        if (getSkipDeposit() != null && getSkipDeposit() == false) {
            makeFirstNightDeposit();
        }
        retrieveReservation();
    }

    private void addGuest() {
        Guest guest;
        if (additionalGuests.get() == null) {
            setAdditionalGuests(new HashMap<Integer, Guest>());
        }
        if (getAddNewGuest() != null && getAddNewGuest() == true) {
            guest = new HouseHold(1).primaryGuest();
            getAdditionalGuests().put(additionalGuests.get().size() + 1, guest);
        } else {
            guest = getHouseHold().primaryGuest();
            getAdditionalGuests().put(additionalGuests.get().size() + 1, guest);
        }

        if (isValid(getAddChildGuest()) && getAddChildGuest() == true) {
            guest.setAge("3");
        }
        guest.primaryAddress().setCity("Winston Salem");
        getBook().addRoomDetails_RoomReservationDetail_GuestReferenceDetailGuest(false, false, guest);
    }

    public String freezeInventory() {
        String freezeId = "";
        FreezeInventory freeze = new FreezeInventory(getEnvironment(), "Main");
        freeze.setPackageCode(getPackageCode());
        freeze.setPartyMixFirstName(getHouseHold().primaryGuest().getFirstName(), "1");
        freeze.setPartyMixLastName(getHouseHold().primaryGuest().getLastName(), "1");
        freeze.setResortCode(getResortCode());
        freeze.setRoomCode(getRoomTypeCode());
        freeze.setResortPeriodStartDate(getArrivalDate());
        freeze.setResortPeriodEndDate(getDepartureDate());
        freeze.sendRequest();
        //
        try {
            if (freeze.getResponseStatusCode().equals("200")) {
                throw new AutomationException();
            }
            freezeId = freeze.getFreezeId();
        } catch (AutomationException e) {
        }
        return freezeId;
    }

    public void retrieveReservation() {
        Sleeper.sleep(5000);
        retrieve.set(new Retrieve(Environment.getBaseEnvironmentName(getEnvironment()), "Main"));
        getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", getBook().getTravelPlanId());
        getRetrieve().setRequestNodeValueByXPath("//request/locationId", getLocationId());
        getRetrieve().sendRequest();
        if (getRetrieve().getFaultString().toLowerCase().replaceAll("\\s", "").contains("No Accommodation Component found".toLowerCase().replaceAll("\\s", ""))) {
            String sql = "select d.WRK_LOC_ID "
                    + "from rsrc_inv.wrk_loc d "
                    + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                    + "where a.tp_id = '" + getBook().getTravelPlanId() + "' "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_nb = c.tc_grp_nb "
                    + "and c.fac_id is not null )";
            Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));
            for (int i = 1; i <= rs.getRowCount(); i++) {
                getRetrieve().setRequestNodeValueByXPath("//request/locationId", rs.getValue("WRK_LOC_ID", i));
                getRetrieve().sendRequest();
                if (getRetrieve().getResponseStatusCode().equals("200")) {
                    break;
                }
            }
        }
        TestReporter.assertTrue(getRetrieve().getResponseStatusCode().equals("200"), "Verify that an error did not occurred retrieving the prereq reservation: " + getRetrieve().getFaultString());
        partyId.set(getRetrieve().getPartyId());
        guestId.set(getRetrieve().getGuestId());
        guestAddressLocatorId.set(getRetrieve().getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest/addressDetails/guestLocatorId"));
    }

    public void retrieveReservation(Book book) {
        Sleeper.sleep(5000);
        retrieve.set(new Retrieve(Environment.getBaseEnvironmentName(getEnvironment()), "Main"));
        getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", book.getTravelPlanId());
        getRetrieve().setRequestNodeValueByXPath("//request/locationId", getLocationId());
        getRetrieve().sendRequest();
        if (getRetrieve().getFaultString().toLowerCase().replaceAll("\\s", "").contains("No Accommodation Component found".toLowerCase().replaceAll("\\s", ""))) {
            String sql = "select d.WRK_LOC_ID "
                    + "from rsrc_inv.wrk_loc d "
                    + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                    + "where a.tp_id = '" + getBook().getTravelPlanId() + "' "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_nb = c.tc_grp_nb "
                    + "and c.fac_id is not null )";
            Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));
            for (int i = 1; i <= rs.getRowCount(); i++) {
                getRetrieve().setRequestNodeValueByXPath("//request/locationId", rs.getValue("WRK_LOC_ID", i));
                getRetrieve().sendRequest();
                if (getRetrieve().getResponseStatusCode().equals("200")) {
                    break;
                }
            }
        }
        TestReporter.assertTrue(getRetrieve().getResponseStatusCode().equals("200"), "Verify that an error did not occurred retrieving the prereq reservation: " + getRetrieve().getFaultString());
        partyId.set(getRetrieve().getPartyId());
        guestId.set(getRetrieve().getGuestId());
        guestAddressLocatorId.set(getRetrieve().getResponseNodeValueByXPath("//travelPlanInfo/travelPlanGuests/guest/addressDetails/guestLocatorId"));
    }

    protected void setValues() {
        boolean success = false;
        int index;
        if (getEnvironment() == null) {
            throw new AutomationException("The environment variable is null.");
        }
        do {
            try {
                index = Randomness.randomNumberBetween(0, roomTypeAndFacInfo.length - 1);
                setFacilityId(roomTypeAndFacInfo[index][4]);
                setRoomTypeCode(roomTypeAndFacInfo[index][1]);
                setLocationId(roomTypeAndFacInfo[index][5]);

                String sql = "select d.WRK_LOC_ID "
                        + "from rsrc_inv.wrk_loc d "
                        + "where d.HM_RSRT_FAC_ID = '" + getFacilityId() + "' "
                        + "and d.TXN_ACCT_CTR_ID is not null "
                        + "order by d.CREATE_DTS asc";
                Database db = new OracleDatabase(getEnvironment().toLowerCase().replace("_cm", ""), Database.DREAMS);
                Recordset rs = new Recordset(db.getResultSet(sql));

                if (rs.getRowCount() == 0) {
                    throw new AutomationException("No Location ID for Facility [ " + getFacilityId() + " ]");
                }
                if (!rs.getValue("WRK_LOC_ID").equals(getLocationId())) {
                    setLocationId(rs.getValue("WRK_LOC_ID", 1));
                }

                if (ResortInfo.getFacilityCD(ResortColumns.FACILITY_ID, getFacilityId()).isEmpty()) {
                    throw new NullPointerException();
                }
                setResortCode(ResortInfo.getFacilityCD(ResortColumns.FACILITY_ID, getFacilityId()));
                setSourceAccountingCenter(ResortInfo.getSourceAccountingCenterId(ResortColumns.FACILITY_ID, getFacilityId()));

                db = new OracleDatabase(getEnvironment(), "DREAMS");
                rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getCampusIdByFacilityId().replace("{FAC_ID}", getFacilityId())));
                setCampusId(rs.getValue("CMPS_ID", 1));

                success = true;
            } catch (NullPointerException | AutomationException e) {
                // System.out.println();
            }
        } while (!success);
    }

    protected void setValues(String environment) {
        setEnvironment(environment);
        setValues();
    }

    protected void setValues(String facilityId, String roomTypeCode, String locationId) {
        setFacilityId(facilityId);
        setRoomTypeCode(roomTypeCode);
        setLocationId(locationId);
        if (ResortInfo.getFacilityCD(ResortColumns.FACILITY_ID, getFacilityId()).isEmpty()) {
            throw new NullPointerException();
        }
        setResortCode(ResortInfo.getFacilityCD(ResortColumns.FACILITY_ID, getFacilityId()));
        setSourceAccountingCenter(ResortInfo.getSourceAccountingCenterId(ResortColumns.FACILITY_ID, getFacilityId()));
    }

    public String getAssignmentOwnerId(String tpId) {
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getAccommodationComponentAssignemtnOwnerIDByTpId(tpId)));
        return rs.getValue("ASGN_OWN_ID", 1);
    }

    public static String getAgeTypeByAge(int age) {
        return getAgeTypeByAge(String.valueOf(age));
    }

    public static String getAgeTypeByAge(String age) {
        int intAge = Integer.parseInt(age);
        String ageType = null;

        if (intAge <= 2) {
            ageType = "INFANT";
        } else if (intAge <= 9) {
            ageType = "CHILD";
        } else if (intAge <= 17) {
            ageType = "JUNIOR";
        } else {
            ageType = "ADULT";
        }

        return ageType;
    }

    protected void makeFirstNightDeposit() {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(environment, "UI booking");
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getBook().getTravelPlanId());
        } else {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, tpId.get());
        }

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            sqlTpId = getBook().getTravelPlanId();
        } else {
            sqlTpId = tpId.get();
        }
        String sql = "select d.WRK_LOC_ID "
                + "from rsrc_inv.wrk_loc d "
                + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + sqlTpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.fac_id is not null )";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            setLocationId(rs.getValue("WRK_LOC_ID", i));

            retrieveBalance.setLocationId(getLocationId());
            retrieveBalance.sendRequest();
            if (retrieveBalance.getResponseStatusCode().equals("200")) {
                break;
            }
        }
        if (!retrieveBalance.getResponseStatusCode().equals("200")) {
            if (getBook() != null) {
                TestReporter.log("\n\nAn error occurred retrieving the balance for the TP ID [" + getBook().getTravelPlanId() + "],");
            } else if (tpId != null && tpId.get() != null) {
                TestReporter.log("\n\nAn error occurred retrieving the balance for the TP ID [" + tpId.get() + "],");
            } else {
                TestReporter.log("\n\nAn error occurred retrieving the balance");
            }
            TestReporter.log("Fault String: " + retrieveBalance.getFaultString());
            TestReporter.log("ENDPOINT: " + retrieveBalance.getServiceURL());
            TestReporter.logNoXmlTrim("\n\nRQ:\n\n" + retrieveBalance.getRequest());
            TestReporter.logNoXmlTrim("\n\nRS:\n\n" + retrieveBalance.getResponse());
        }
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for the reservation: " + retrieveBalance.getFaultString());

        PostCardPayment postPayment = new PostCardPayment(environment, "Visa-CreditCard");
        postPayment.setAmount(retrieveBalance.getDepositRequired());
        postPayment.setFolioId(retrieveBalance.getFolioId());
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, getBook().getTravelPlanId());
        } else {
            postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, tpId.get());
        }
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, getBook().getTravelComponentId());
        } else {
            postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, tcId.get());
        }
        postPayment.setLocationId(getLocationId());
        postPayment.setPartyId(getPartyId());
        try {
            postPayment.setPrimaryLastname(hh.get().primaryGuest().getLastName());
        } catch (NullPointerException | AutomationException e) {
            postPayment.setPrimaryLastname(getHouseHold().primaryGuest().getLastName());
        }
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            postPayment.setTravelPlanId(getBook().getTravelPlanId());
        } else {
            postPayment.setTravelPlanId(tpId.get());
        }
        if (getBook() != null && getBook().getTravelPlanSegmentId() != null) {
            postPayment.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        } else {
            postPayment.setTravelPlanSegmentId(tpsId.get());
        }
        postPayment.setRetreivalReferenceNumber();
        postPayment.sendRequest();
        TestReporter.assertEquals(postPayment.getResponseStatusCode(), "200", "Response was not 200");
        TestReporter.log("Payment ID: " + postPayment.getPaymentId());
    }

    protected void makeFirstNightDeposit(Book book) {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(environment, "UI booking");
        if (book != null && book.getTravelPlanId() != null) {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, book.getTravelPlanId());
        } else {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, tpId.get());
        }

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        if (book != null && book.getTravelPlanId() != null) {
            sqlTpId = book.getTravelPlanId();
        } else {
            sqlTpId = tpId.get();
        }
        String sql = "select d.WRK_LOC_ID "
                + "from rsrc_inv.wrk_loc d "
                + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + sqlTpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.fac_id is not null )";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            setLocationId(rs.getValue("WRK_LOC_ID", i));

            retrieveBalance.setLocationId(getLocationId());
            retrieveBalance.sendRequest();
            if (retrieveBalance.getResponseStatusCode().equals("200")) {
                break;
            }
        }
        if (!retrieveBalance.getResponseStatusCode().equals("200")) {
            if (book != null) {
                TestReporter.log("\n\nAn error occurred retrieving the balance for the TP ID [" + book.getTravelPlanId() + "],");
            } else if (tpId != null && tpId.get() != null) {
                TestReporter.log("\n\nAn error occurred retrieving the balance for the TP ID [" + tpId.get() + "],");
            } else {
                TestReporter.log("\n\nAn error occurred retrieving the balance");
            }
            TestReporter.log("Fault String: " + retrieveBalance.getFaultString());
            TestReporter.log("ENDPOINT: " + retrieveBalance.getServiceURL());
            TestReporter.logNoXmlTrim("\n\nRQ:\n\n" + retrieveBalance.getRequest());
            TestReporter.logNoXmlTrim("\n\nRS:\n\n" + retrieveBalance.getResponse());
        }
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for the reservation: " + retrieveBalance.getFaultString());

        PostCardPayment postPayment = new PostCardPayment(environment, "Visa-CreditCard");
        postPayment.setAmount(retrieveBalance.getDepositRequired());
        postPayment.setFolioId(retrieveBalance.getFolioId());
        if (book != null && book.getTravelPlanId() != null) {
            postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, book.getTravelPlanId());
        } else {
            postPayment.setBookingReference(ServiceConstants.BookingSource.DREAMS_TP, tpId.get());
        }
        if (book != null && book.getTravelPlanId() != null) {
            postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, book.getTravelComponentId());
        } else {
            postPayment.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TC, tcId.get());
        }
        postPayment.setLocationId(getLocationId());
        postPayment.setPartyId(getPartyId());
        try {
            postPayment.setPrimaryLastname(hh.get().primaryGuest().getLastName());
        } catch (NullPointerException | AutomationException e) {
            postPayment.setPrimaryLastname(getHouseHold().primaryGuest().getLastName());
        }
        if (book != null && book.getTravelPlanId() != null) {
            postPayment.setTravelPlanId(book.getTravelPlanId());
        } else {
            postPayment.setTravelPlanId(tpId.get());
        }
        if (book != null && book.getTravelPlanSegmentId() != null) {
            postPayment.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
        } else {
            postPayment.setTravelPlanSegmentId(tpsId.get());
        }
        postPayment.setRetreivalReferenceNumber();
        postPayment.sendRequest();
        TestReporter.assertEquals(postPayment.getResponseStatusCode(), "200", "Response was not 200");
        TestReporter.log("Payment ID: " + postPayment.getPaymentId());
    }

    public String findBundleTcg(String tpId) {
        String baseSql = DVCSalesDreams.getReservationInfoByTpId(tpId).replace("and rownum = 1", "").replace("*", "unique(c.TC_GRP_NB)");
        String sql = "select PROD_TYP_NM from res_mgmt.tc a where a.tc_grp_nb in({INPUT})";
        Database db = new OracleDatabase(DVCSalesBaseTest.removeCM(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(baseSql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            String locSql = sql;
            locSql = locSql.replace("{INPUT}", rs.getValue("TC_GRP_NB", i));
            Recordset rs2 = new Recordset(db.getResultSet(locSql));
            if (rs2.getRowCount() > 1) {
                for (int j = 1; j <= rs2.getRowCount(); j++) {
                    if (rs2.getValue("PROD_TYP_NM", j).contains("Memory Maker")) {
                        return rs.getValue("TC_GRP_NB", i);
                    }
                }
            }
        }
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No bundle was found to be associated with TP ID [" + tpId + "].");
        }
        return null;
    }

    private void addDining() {
        diningRes = new ShowDiningReservation(environment.toLowerCase().replace("_cm", ""), hh.get());
        diningRes.setTravelPlanId(getBook().getTravelPlanId());
        diningRes.setFacilityName("Pioneer Hall");
        diningRes.setProductName("Hoop-Dee-Doo-Cat 2-1st Show");
        diningRes.setServiceStartDate(getArrivalDate());
        diningRes.book("NoComponentsNoAddons");

        firstDiningTcg.set(findDiningResTcg(diningRes.getConfirmationNumber()));
    }

    private String findDiningResTcg(String confirmationNumber) {
        Database db = new OracleDatabase(environment.toLowerCase().replace("_cm", ""), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getReservationInfoByTpsId(confirmationNumber)));
        return rs.getValue("TC_GRP_NB", 1);
    }

    protected void checkingIn(String environment) {

        FindRoomForReservation findRoom = new FindRoomForReservation(environment, "UI Booking");
        findRoom.setTravelPlanId(getBook().getTravelPlanId());
        findRoom.setNumberOfResponseRows("50");
        findRoom.sendRequest();
        TestReporter.assertTrue(findRoom.getResponseStatusCode().equals("200"), "Verify no error occurred finding a room for a reservation: " + findRoom.getFaultString());

        String resourceId = null;
        String roomNumber = null;
        AssignRoomForReservation assignRoom = null;
        boolean roomAdded = false;
        Map<String, String> values = findRoom.getAllRoomAndResourceIds();
        Iterator<Entry<String, String>> it = values.entrySet().iterator();
        while (!roomAdded && it.hasNext()) {
            Entry<String, String> et = it.next();
            roomNumber = et.getKey();
            resourceId = et.getValue();

            assignRoom = new AssignRoomForReservation(environment, "UI Booking");
            assignRoom.setArrivalAndDepartureDaysOut(String.valueOf(getDaysOut()), String.valueOf(getNights()));
            assignRoom.setAssignmentOwnerNumber(findRoom.getAssignmentOwnerNumber());
            assignRoom.setFacilityId(getFacilityId());
            assignRoom.setRoomNumber(roomNumber);
            assignRoom.setRoomResourceNumber(resourceId);
            assignRoom.sendRequest();
            if (assignRoom.getFaultString().contains("LOCK ASSIGNMENT ERROR")) {
                Sleeper.sleep(Randomness.randomNumberBetween(3, 7) * 1000);
                assignRoom.sendRequest();
            }
            if (assignRoom.getResponseStatusCode().equals("200")) {
                roomAdded = true;
            }
        }
        ;
        TestReporter.assertTrue(roomAdded, "Verify no error occurred assigning a room to a reservation: " + assignRoom.getFaultString());

        CheckingIn checkingIn = new CheckingIn(environment, "UI_Booking");
        checkingIn.setLocationId(getLocationId());
        checkingIn.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        checkingIn.sendRequest();
        TestReporter.assertTrue(checkingIn.getResponseStatusCode().equals("200"), "Verify that no error occurred checking-in TP ID [" + getBook().getTravelPlanId() + "]: " + getBook().getFaultString());

    }

    // public static boolean isValid(Object o) {
    // boolean valid = false;
    // if (o == null) {
    // valid = false;
    // }
    //
    // if (o instanceof String) {
    // if (StringUtils.isEmpty((String) o)) {
    // valid = false;
    // } else {
    // valid = true;
    // }
    // }
    //
    // return valid;
    // }

    public static Boolean isValid(Object obj) {
        Boolean valid = null;
        if (obj != null) {
            valid = true;
        } else {
            valid = false;
        }

        if (obj instanceof String) {
            if (StringUtils.isEmpty(((String) obj))) {
                return false;
            } else {
                return true;
            }

        } else if (obj instanceof Collection<?>) {
            if (((Collection<?>) obj).isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else if (obj instanceof Map<?, ?>) {
            if (((Map<?, ?>) obj).isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            return valid;
        }
    }
}