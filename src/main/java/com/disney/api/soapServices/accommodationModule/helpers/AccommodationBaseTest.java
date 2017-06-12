package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.disney.AutomationException;
import com.disney.api.WebServiceException;
import com.disney.api.restServices.BaseRestTest;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.availabilityWSPort.operations.FreezeInventory;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.CreateSettlementMethod;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.RetrieveFolioBalanceDue;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCardPayment;
import com.disney.utils.Datatable;
import com.disney.utils.GenerateCard;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.ResortInfo;
import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class AccommodationBaseTest extends BaseRestTest {
    /*
     * Define an email address to be used for guest messaging. This email
     * needs to be a Disney email as the emails that are sent contain meta
     * data that pertains to the Disney network. The current email is that
     * one of the authors of this test suite. Once this account becomes
     * inactive, another email will have to replace it. It was desired
     * to have a testing-dedicated email address, but at the time of
     * creation, none was available.
     */
    private String emailAddress = "waightstill.w.avery.-nd@disney.com";

    /*
     * Currently information from the LACD project is being returned in Latest,
     * and not in the COMO Latest.
     * This is causing 1-to-1 validations to fail. The offending LACD node is to
     * be whitelisted until the
     * LACD code is deployed. This date is defined below, after which time the
     * LACD node will no longer be whitelisted.
     */
    // private static String lacdDeployDate = "2017-01-01";

    protected static String environment; // Base environment under test
    // used
    // for
    // booking
    // accommodations
    // (may
    // be
    // different
    // than
    // the
    // environment
    // under
    // test)
    protected ThreadLocal<Integer> daysOut = new ThreadLocal<Integer>(); // Number
    // of
    // days
    // out
    // for
    // the
    // reservation
    protected ThreadLocal<Integer> nights = new ThreadLocal<Integer>(); // Number
    // of
    // nights
    // for
    // the
    // reservation
    protected ThreadLocal<String> arrivalDate = new ThreadLocal<String>(); // Arrival
    // date
    // for
    // the
    // reservation
    protected ThreadLocal<String> departureDate = new ThreadLocal<String>(); // Departure
    // date
    // for
    // the
    // reservation
    private ThreadLocal<String> locationId = new ThreadLocal<String>(); // Reservation
    // location
    // ID
    private ThreadLocal<String> resortCode = new ThreadLocal<String>(); // Reservation
    // resort
    // code
    private ThreadLocal<String> facilityId = new ThreadLocal<String>(); // Reservation
    // facility
    // ID
    private ThreadLocal<String> campusId = new ThreadLocal<String>(); // Reservation
    // campus
    // ID;
    private ThreadLocal<String> sourceAccoutingCenter = new ThreadLocal<String>(); // Reservation
    // source
    // accounting
    // center
    private ThreadLocal<String> roomTypeCode = new ThreadLocal<String>(); // Reservation
    // room
    // type
    // code
    private ThreadLocal<String> agencyId = new ThreadLocal<String>(); // Reservation
    // travel
    // agency
    // ID
    private ThreadLocal<String> guestId = new ThreadLocal<String>(); // Reservation
    // guest ID
    private ThreadLocal<String> partyId = new ThreadLocal<String>(); // Reservation
    // party ID
    private ThreadLocal<String> packageCode = new ThreadLocal<String>(); // Reservation
    // package
    // code
    private ThreadLocal<String> guestAddressLocatorId = new ThreadLocal<String>(); // Reservation
    // guest
    // address
    // locator
    // ID
    protected ThreadLocal<Boolean> skipExternalRef = new ThreadLocal<Boolean>(); // Flag
    // used
    // to
    // determine
    // if
    // external
    // references
    // are
    // to
    // be
    // added
    // to
    // the
    // request
    private ThreadLocal<String> externalRefNumber = new ThreadLocal<String>(); // Reservation
    // external
    // reference
    // number
    private String externalRefSource = "DPMSProperty"; // Reservation external
    // reservation source
    private static String[][] roomTypeAndFacInfo = new String[40][6]; // Map of
    // resort/roomtype
    // configurations
    private Map<String, String> noPackageCodes = new HashMap<String, String>(); // Map
    // of
    // configurations
    // that
    // failed
    // to
    // return
    // package
    // codes
    protected ThreadLocal<Boolean> fixedDates = new ThreadLocal<Boolean>(); // Flag
    // used
    // to
    // determine
    // if
    // specific
    // dates
    // are
    // to
    // be
    // used
    // for
    // the
    // booking
    private ThreadLocal<HouseHold> hh = new ThreadLocal<HouseHold>(); // Contains
    // all of
    // the
    // party
    // guests
    private ThreadLocal<Book> book = new ThreadLocal<Book>(); // Contains the
    // Book object
    private ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>(); // Contains
    // the
    // Retrieve
    // object
    protected ThreadLocal<String> tpId = new ThreadLocal<String>(); // Reservation
    // travel
    // plan ID
    protected ThreadLocal<String> tpsId = new ThreadLocal<String>(); // Reservation
    // travel
    // plan
    // segment
    // ID
    protected ThreadLocal<String> tcgId = new ThreadLocal<String>(); // Reservation
    // travel
    // component
    // grouping
    // ID
    protected ThreadLocal<String> tcId = new ThreadLocal<String>(); // Reservation
    // travel
    // component
    // ID
    protected String isComo = ""; // Flag used to determine if execution is
    // occurring in a Composite Modernization
    // environment
    private ThreadLocal<Boolean> skipCancel = new ThreadLocal<Boolean>();
    private ThreadLocal<String> ageType = new ThreadLocal<String>();
    private ThreadLocal<String> age = new ThreadLocal<String>();

    /**
     * Method to add a configuration to the list of configurations that failed
     * to retrieve a package code
     *
     * @param key
     *            - String, indicating the failied configuration (EX:
     *            getResortCode()+":"+getLocationId()+":"+getRoomTypeCode())
     * @param value
     *            - String, indicating the failed configuration (EX:
     *            "No package code found for resort["
     *            +getResortCode()+"], locationId["
     *            +getLocationId()+"], and roomType["+getRoomTypeCode()+"]:")
     */
    protected void addToNoPackageCodes(String key, String value) {
        noPackageCodes.put(key, value);
    }

    // **************************************
    // **************************************
    // **************************************
    // Setters
    // **************************************
    // **************************************
    // **************************************

    public static void setEnvironment(String env) {
        environment = env;
    }

    /**
     * Sets the reservation facility ID
     *
     * @param facilityId
     *            - reservation facility ID
     */
    private void setFacilityId(String facilityId) {
        this.facilityId.set(facilityId);
    }

    /**
     * Sets the reservation location ID
     *
     * @param locationId
     *            - reservation location ID
     */
    private void setLocationId(String locationId) {
        this.locationId.set(locationId);
    }

    /**
     * Sets the reservation resort code
     *
     * @param resortCode
     *            - reservation resort code
     */
    private void setResortCode(String resortCode) {
        this.resortCode.set(resortCode);
    }

    /**
     * Sets the reservation source accounting center ID
     *
     * @param sourceAccoutingCenter
     *            - reservation source accounting center ID
     */
    private void setSourceAccountingCenter(String sourceAccoutingCenter) {
        this.sourceAccoutingCenter.set(sourceAccoutingCenter);
    }

    /**
     * Sets the reservation room type code
     *
     * @param roomTypeCode
     *            - reservation room type code
     */
    private void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode.set(roomTypeCode);
    }

    /**
     * Sets the reservation travel agency ID
     *
     * @param agencyId
     *            - reservation travel agency ID
     */
    protected void setAgencyId(String agencyId) {
        this.agencyId.set(agencyId);
    }

    /**
     * Sets the number of nights for the reservation
     *
     * @param nights
     *            - number of nights for the reservation
     */
    protected void setNights(int nights) {
        this.nights.set(nights);
    }

    /**
     * Sets the number of days out for the reservation
     *
     * @param nights
     *            - number of days out for the reservation
     */
    protected void setDaysOut(int daysOut) {
        this.daysOut.set(daysOut);
    }

    /**
     * Sets the arrival date for the reservation
     *
     * @param arrivalDate
     *            - arrival date for the reservation
     */
    protected void setArrivalDate(String arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }

    /**
     * Sets the departure date for the reservation
     *
     * @param departureDate
     *            - departure date for the reservation
     */
    protected void setDepartureDate(String departureDate) {
        this.departureDate.set(departureDate);
    }

    /**
     * Overloaded method that sets the number of nights and then the departure
     * date for the reservation
     *
     * @param nights
     *            - number of nights for the reservation
     */
    protected void setDepartureDate(int nights) {
        setNights(nights);
        this.departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
    }

    /**
     * Overloaded method that sets the number of arrival days out and then the
     * arrival date for the reservation
     *
     * @param daysOut
     *            - number of days out for the reservation
     */
    protected void setArrivalDate(int daysOut) {
        setDaysOut(daysOut);
        this.arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
    }

    /**
     * Sets the local Book object
     *
     * @param book
     *            - local Book object
     */
    protected void setBook(Book book) {
        this.book.set(book);
    }

    /**
     * Sets the local Retrieve object
     *
     * @param book
     *            - local Retrieve object
     */
    protected void setRetrieve(Retrieve retrieve) {
        this.retrieve.set(retrieve);
    }

    /**
     * Sets the flag which is used to determine if fixed dates are required
     *
     * @param fixedDates
     *            - flag which is used to determine if fixed dates are required
     */
    protected void setFixedDates(Boolean fixedDates) {
        this.fixedDates.set(fixedDates);
    }

    /**
     * Sets the flag which is used to determine if exteranl references are to be
     * skipped
     *
     * @param skip
     *            - flag which is used to determine if exteranl references are
     *            to be skipped
     */
    protected void setSkipExternalRef(Boolean skip) {
        skipExternalRef.set(skip);
    }

    /**
     * Sets the local travel plan ID
     *
     * @param tpId
     *            - local travel plan ID
     */
    protected void setTpId(String tpId) {
        this.tpId.set(tpId);
    }

    /**
     * Sets the local travel plan segment ID
     *
     * @param tpId
     *            - local travel plan segment ID
     */
    protected void setTpsId(String tpsId) {
        this.tpsId.set(tpsId);
    }

    /**
     * Sets the local travel component grouping ID
     *
     * @param tpId
     *            - local travel component grouping ID
     */
    protected void setTcgId(String tcgId) {
        this.tcgId.set(tcgId);
    }

    /**
     * Sets the local travel component ID
     *
     * @param tpId
     *            - local travel component ID
     */
    protected void setTcId(String tcId) {
        this.tcId.set(tcId);
    }

    /**
     * Sets the campus ID
     *
     * @param campusId
     */
    protected void setCampusId(String campusId) {
        this.campusId.set(campusId);
    }

    // **************************************
    // **************************************
    // **************************************
    // Getters
    // **************************************
    // **************************************
    // **************************************

    public static String getEnvironment() {
        return environment;
    }

    /**
     * Gets the number of days out for the reservation
     *
     * @return - number of days out for the reservation
     */
    public int getDaysOut() {
        return daysOut.get();
    }

    /**
     * Gets the number of nights for the reservation
     *
     * @return - number of nights for the reservation
     */
    public int getNights() {
        return nights.get();
    }

    /**
     * Gets the arrival date for the reservation
     *
     * @return - arrival date for the reservation
     */
    public String getArrivalDate() {
        return arrivalDate.get();
    }

    /**
     * Gets the departure date for the reservation
     *
     * @return - departure date for the reservation
     */
    public String getDepartureDate() {
        return departureDate.get();
    }

    /**
     * Gets reservation location ID
     *
     * @return - reservation location ID
     */
    public String getLocationId() {
        return locationId.get();
    }

    /**
     * Gets reservation resort code
     *
     * @return - reservation resort code
     */
    public String getResortCode() {
        return resortCode.get();
    }

    /**
     * Gets reservation facility ID
     *
     * @return - reservation facility ID
     */
    public String getFacilityId() {
        return facilityId.get();
    }

    /**
     * Gets reservation source accounting ID
     *
     * @return - reservation source accounting ID
     */
    public String getSourceAccountingCenter() {
        return sourceAccoutingCenter.get();
    }

    /**
     * Gets reservation room type code
     *
     * @return - reservation room type code
     */
    public String getRoomTypeCode() {
        return roomTypeCode.get();
    }

    /**
     * Gets reservation guets ID
     *
     * @return - reservation guest ID
     */
    public String getGuestId() {
        return guestId.get();
    }

    /**
     * Gets reservation party ID
     *
     * @return - reservation party ID
     */
    public String getPartyId() {
        return partyId.get();
    }

    /**
     * Gets reservation package code
     *
     * @return - reservation package code
     */
    public String getPackageCode() {
        return packageCode.get();
    }

    /**
     * Gets reservation address location ID
     *
     * @return - reservation address location ID
     */
    public String getAddressGuestLocatorId() {
        return guestAddressLocatorId.get();
    }

    /**
     * Gets reservation primary guest email
     *
     * @return - reservation primary guest email
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets reservation external reservation number
     *
     * @return - reservation external reservation number
     */
    public String getExternalRefNumber() {
        return externalRefNumber.get();
    }

    /**
     * Gets reservation external reservation source
     *
     * @return - reservation external reservation source
     */
    public String getExternalRefSource() {
        return externalRefSource;
    }

    /**
     * Gets reservation campus ID
     *
     * @return - reservation campus ID
     */
    public String getCampusId() {
        return campusId.get();
    }

    /**
     * Gets the map of the configurations that failed to return package codes
     *
     * @return - map of the configurations that failed to return package codes
     */
    public Map<String, String> getNoPackageCodes() {
        return noPackageCodes;
    }

    /**
     * Gets the HouseHold object
     *
     * @return - the HouseHold object
     */
    public HouseHold getHouseHold() {
        return hh.get();
    }

    /**
     * Sets the HouseHold object
     *
     * @param - the HouseHold object
     */
    public void setHouseHold(HouseHold hh) {
        this.hh.set(hh);
    }

    /**
     * Gets the Book object
     *
     * @return - the Book object
     */
    public Book getBook() {
        return book.get();
    }

    /**
     * Gets the Retrieve object
     *
     * @return - the Retrieve object
     */
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

    // public static String getLacdDeployDate() {
    // return lacdDeployDate;
    // }

    /**
     * Unless overridden in the test class, this will serve as the entry point
     * for every TravelPlan suite. Along
     * with defining some flags and fields, a query is performed in the DREAMS
     * database to find the resort/roomtype
     * configurations with the highest number of physical beds. This 'should'
     * afford each test a high probability
     * of avoiding availability issues.
     *
     * @param environment
     *            - environment under test, passed from the TextNG XML
     */
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
    }

    /**
     * Unless overridden in the test class, this will be the method executed
     * directly prior to the @Test method.
     * Contained within is code that sets the travel plan dates, the
     * accommodation metadata (resort, roomtype, facility ID, etc.),
     * as well as a call to a method that books the actual reservation.
     *
     * @param environment
     *            - environment under test, passed from the TextNG XML
     */
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

        setValues();
        bookReservation();
    }

    /**
     * Unless overridden in the test class, this will be the method executed
     * directly after to the @Test method.
     * In an effort to reduce the footprint of automated tests in the test
     * environments, this method will attempt
     * to cancel the reservation to release inventory back into the environment.
     */
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

    /**
     * Unless overridden in the test class, this will serve as the exit point
     * for every TravelPlan suite. This method
     * can serve many purposes, such as outputting messages collected during the
     * running of the suite, data reporting, etc.
     * At the time of development, this method is used to report and
     * configuration that did not return a package code.
     */
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

    /**
     * Create a household locally. If required, this household can be created in
     * the GoMaster database using the getHouseHold().getsendToApi() method.
     */
    public void createHouseHold() {
        hh.set(new HouseHold(1));
    }

    /**
     * This method is the main method that books the reservation. The sequence
     * of calls is listed below:
     * <ol>
     * <li>Create a local household</li>
     * <li>Retrieves a package code</li>
     * <ul>
     * <li>Modify the query to use the booking metadata (arrival date, location
     * ID, resort code, and room type code)</li>
     * <li>If no package code is returned, new metadata is generated and package
     * code retrieval is attempted again</li>
     * <li>A maximum of attempts (defined prior to the loop) is performed. If
     * that limit is reached, the loop is broken and the test will fail. If
     * successful, the loop is broken and the test proceeds.</li>
     * </ul>
     * <li>Metadata specific to the booking is set (guest info, resort,
     * roomtype, package code, etc.)</li>
     * <li>An attempt is made to freeze accommodation inventory. At the time of
     * development, this is commented out as a permanent solution has not been
     * approved by the application developers.</li>
     * <li>The book request is sent to the service</li>
     * <li>If the booking is not successful, new metadata is generated and the
     * entire booking process is attempted again.</li>
     * <li>A maximum of attempts (defined prior to the loop) is performed. If
     * that limit is reached, the loop is broken and the test will fail. If
     * successful, the loop is broken and the test proceeds.</li>
     * <li>After a successful booking, a call to
     * AccommodationSalesServicePort#retrieve is made to capture reservation
     * details.</li>
     * </ol>
     */
    public void bookReservation() {
        if (getHouseHold() == null) {
            createHouseHold();
            getHouseHold().primaryGuest().primaryAddress().setCity("Winston Salem");
        }

        try {
            book.set(new Book(getEnvironment(), "bookWithoutTickets"));
        } catch (WebServiceException e) {
            Sleeper.sleep(Randomness.randomNumberBetween(5, 10) * 1000);
            book.set(new Book(getEnvironment(), "bookWithoutTickets"));
        }

        if (skipExternalRef.get() == null || skipExternalRef.get() == false) {
            externalRefNumber.set(Randomness.randomNumber(12));
            try {
                getBook().setExternalRefNum(getExternalRefNumber());
            } catch (XPathNotFoundException e) {
                getBook().setRequestNodeValueByXPath("//book/request", "fx:addnode;node:externalReference");
                getBook().setRequestNodeValueByXPath("//book/request/externalReference", "fx:addnode;node:externalReferenceNumber");
                getBook().setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceNumber", getExternalRefNumber());
                getBook().setRequestNodeValueByXPath("//book/request/externalReference", "fx:addnode;node:externalReferenceSource");
                getBook().setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceSource", getExternalRefSource());
            }
        }

        PackageCodes pkg = new PackageCodes();
        boolean bookSuccess = false;
        int maxTries = 10;
        int tries = 0;
        do {
            getBook().setArrivalDate(String.valueOf(getDaysOut()));
            getBook().setDeptDate(String.valueOf(getDaysOut() + getNights()));

            pkg = new PackageCodes();
            boolean success = false;
            int pkgMaxTries = 15;
            int pkgTries = 0;
            do {
                try {
                    packageCode.set(pkg.retrievePackageCode(getEnvironment(), String.valueOf(getDaysOut()),
                            getLocationId(), "DRC RO", "", getResortCode(), getRoomTypeCode(), ""));
                    success = true;
                } catch (AssertionError e) {
                    if (!noPackageCodes.containsKey(getResortCode() + ":" + getLocationId() + ":" + getRoomTypeCode())) {
                        String message = "No package code found for resort[" + getResortCode() + "], locationId[" + getLocationId() + "], and roomType[" + getRoomTypeCode() + "]:";
                        noPackageCodes.put(getResortCode() + ":" + getLocationId() + ":" + getRoomTypeCode(), message);
                    }
                    setValues();
                }
                pkgTries++;
            } while (!success && pkgTries < pkgMaxTries);
            if (!success) {
                throw new AutomationException("No package code was found after [" + maxTries + "] attempts. Last attempt:"
                        + "\nEnvironment: " + getEnvironment()
                        + "\nDate: " + Randomness.generateCurrentXMLDate(getDaysOut())
                        + "\nLocation ID: " + getLocationId()
                        + "\nResort Code: " + getResortCode()
                        + "\nRoom Type Code: " + getRoomTypeCode());
            }
            // if(agencyId.get() != null) getBook().setAgency(agencyId.get());
            getBook().setPackageCode(getPackageCode());
            getBook().setResortCode(getResortCode());
            getBook().setRoomTypeCode(getRoomTypeCode());
            getBook().setLocationID(getLocationId());

            if (ageType.get() != null) {
                getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/ageType", getAgeType());
            }
            if (age.get() != null) {
                getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/age", getAge());
            }
            getBook().setPrimaryGuestFirstName(getHouseHold().primaryGuest().getFirstName());
            getBook().setPrimaryGuestFirstNameGuestRefDetails(getHouseHold().primaryGuest().getFirstName());
            getBook().setPrimaryGuestFirstNameTravelPlan(getHouseHold().primaryGuest().getFirstName());
            getBook().setPrimaryGuestLastName(getHouseHold().primaryGuest().getLastName());
            getBook().setPrimaryGuestLastNameGuestRefDetails(getHouseHold().primaryGuest().getLastName());
            getBook().setPrimaryGuestLastNameTravelPlan(getHouseHold().primaryGuest().getLastName());
            getBook().setPrimaryGuestMiddleName(getHouseHold().primaryGuest().getMiddleName());
            getBook().setPrimaryGuestMiddleNameGuestRefDetails(getHouseHold().primaryGuest().getMiddleName());
            getBook().setPrimaryGuestMiddleNameTravelPlan(getHouseHold().primaryGuest().getMiddleName());
            getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
            getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
            getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName", getHouseHold().primaryGuest().primaryAddress().getState());
            getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
            getBook().setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
            getBook().setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
            getBook().setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
            getBook().setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
            getBook().setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
            getBook().setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/regionName", getHouseHold().primaryGuest().primaryAddress().getState());
            getBook().setPhoneNumber(getHouseHold().primaryGuest().primaryPhone().getNumber());
            getBook().setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
            getBook().setEmail(getHouseHold().primaryGuest().primaryEmail().getEmail());
            getBook().setRequestNodeValueByXPath("/Envelope/Body/book/request/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

            // freezeInventory();
            // getBook().setRequestNodeValueByXPath("//book/request/roomDetail/overideFreeze",
            // "true");

            getBook().sendRequest();
            TestReporter.logAPI(false, "", getBook());
            if (getBook().getResponseStatusCode().equals("200")) {
                bookSuccess = true;
            }
            else {
                if (fixedDates != null) {
                    if (fixedDates.get() != null) {
                        if (fixedDates.get() != true) {
                            daysOut.set(Randomness.randomNumberBetween(15, 120));
                            nights.set(Randomness.randomNumberBetween(3, 5));
                            arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
                            departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
                        }
                    }
                }
                setValues();
            }
            tries++;
        } while (!bookSuccess && tries < maxTries);
        if (!getBook().getResponseStatusCode().equals("200")) {
            TestReporter.log("\n\nRQ:\n\n");
            TestReporter.log(getBook().getRequest());
            TestReporter.log("\n\nRS:\n\n");
            TestReporter.log(getBook().getResponse());
        }
        TestReporter.assertEquals(getBook().getResponseStatusCode(), "200", "An error occurred booking a prereq reservations: " + getBook().getFaultString());
        retrieveReservation();
    }

    /**
     * Overloaded method for booking a reservation. Typically this will be
     * called in the test class
     *
     * @param locEnv
     *            - environment under test
     */
    public void bookReservation(String locEnv) {
        bookReservation();
    }

    /**
     * This method attempts to freeze accommodation inventory
     *
     * @return freeze ID
     */
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

    /**
     * This method retrieves the booking and sets some reservation data (guest
     * ID, party ID, etc.)
     */
    public void retrieveReservation() {
        Sleeper.sleep(5000);
        try {
            retrieve.set(new Retrieve(environment.toUpperCase().replace("_CM", ""), "Main"));
        } catch (WebServiceException e) {
            Sleeper.sleep(Randomness.randomNumberBetween(5, 10) * 1000);
            retrieve.set(new Retrieve(getEnvironment().toUpperCase().replace("_CM", ""), "Main"));
        }
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", getBook().getTravelPlanId());
        } else {
            getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", tpId.get());
        }
        getRetrieve().setRequestNodeValueByXPath("//request/locationId", getLocationId());
        // getRetrieve().setRequestNodeValueByXPath("//request/locationId",
        // "1");
        getRetrieve().sendRequest();
        if (getRetrieve().getFaultString().contains("SocketTimeoutException")) {
            Sleeper.sleep(Randomness.randomNumberBetween(5, 10) * 1000);
            getRetrieve().sendRequest();
        }
        if (getRetrieve().getFaultString().toLowerCase().replaceAll("\\s", "").contains("No Accommodation Component found".toLowerCase().replaceAll("\\s", ""))) {
            // TestReporter.logNoXmlTrim("\n\nRQ:\n\n" +
            // getRetrieve().getRequest());
            // TestReporter.logNoXmlTrim("\n\nRS:\n\n" +
            // getRetrieve().getResponse());
            // skipCancel.set(true);
            String sqlTpId;
            if (getBook() != null && getBook().getTravelPlanId() != null) {
                getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", getBook().getTravelPlanId());
                sqlTpId = getBook().getTravelPlanId();
            }
            else {
                getRetrieve().setRequestNodeValueByXPath("//request/travelPlanId", tpId.get());
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

    /**
     * Overloaded method for retrieving a reservation. Typically this will be
     * called in the test class
     *
     * @param locEnv
     *            - environment under test
     */
    public void retrieveReservation(String retrieveEnv) {
        retrieveReservation();
    }

    /**
     * This method retrieves folio information for a reservation and the makes a
     * payment that satisfies the deposit requirement.
     */
    protected void makeFirstNightDeposit() {
        RetrieveFolioBalanceDue retrieveBalance = new RetrieveFolioBalanceDue(getEnvironment(), "UI booking");
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getBook().getTravelPlanId());
        } else {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, tpId.get());
        }

        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        String sqlTpId;
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            sqlTpId = getBook().getTravelPlanId();
        }
        else {
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

        PostCardPayment postPayment = new PostCardPayment(getEnvironment(), "Visa-CreditCard");
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
            postPayment.setPrimaryLastname(getBook().getPrimaryGuestLastName());
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

    protected void createSettlementMethod(String scenario, String tpId) {
        setTpId(tpId);
        createSettlementMethod(scenario);
    }

    protected void createSettlementMethod(String scenario) {
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
        if (getBook() != null && getBook().getTravelPlanId() != null) {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, getBook().getTravelPlanId());
        } else {
            retrieveBalance.setExternalReference(ServiceConstants.FolioExternalReference.DREAMS_TP, tpId.get());
        }
        retrieveBalance.setFolioType(ServiceConstants.FolioType.INDIVIDUAL);
        retrieveBalance.setLocationId(getLocationId());
        retrieveBalance.sendRequest();
        TestReporter.assertEquals(retrieveBalance.getResponseStatusCode(), "200", "Verify that no error occurred retrieving the balance for TP ID [" + tpId.get() + "]: " + retrieveBalance.getFaultString());

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
     * Overloaded method for making a deposit for a reservation. Typically this
     * will be called in the test class
     *
     * @param locEnv
     *            - environment under test
     */
    protected void makeFirstNightDeposit(String locEnv) {
        makeFirstNightDeposit();
    }

    /**
     * Method to set reservation metadata. This method loops until all data is
     * deemed successfully populated.
     * A resort/roomtype configuration is randomly chosen from the list of data
     * that is populated in the @BeforeSuite method
     * <ul>
     * <li>Facility ID</li>
     * <li>Room Type Code</li>
     * <li>Location ID</li>
     * <li>Resort Code</li>
     * <li>Source Accounting Center</li>
     * </ul>
     */
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
        // System.out.println();
    }

    protected void setValues(String environment) {
        setEnvironment(environment);
        setValues();
    }

    /**
     * Overloaded method to set specific metadata for a reservation, and
     * generate other metadata based on the values passed to the method
     * <ul>
     * <li>Facility ID</li>
     * <li>Room Type Code</li>
     * <li>Location ID</li>
     * <li>Resort Code</li>
     * <li>Source Accounting Center</li>
     * </ul>
     *
     * @param facilityId
     *            - facility ID under test
     * @param roomTypeCode
     *            - room type code under test
     * @param locationId
     *            - location ID under test
     */
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

    public static String getStateAbbv(String state) {
        switch (state) {
            case "North Carolina":
                return "NC";
            case "BUCKINGHAMSHIRE":
                return "BKM";
            default:
                throw new AutomationException("The state abbrviation [" + state + "] is not an expected type.");
        }
    }

    public String getAssignmentOwnerId(String tpId) {
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getAccommodationComponentAssignemtnOwnerIDByTpId(tpId)));
        return rs.getValue("ASGN_OWN_ID", 1);
    }
}
