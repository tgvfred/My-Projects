package com.disney.api.soapServices.applicationError;


public final class FolioErrorCode {


	/**
	 * Folio uses error code in the range 2001-2900. 2901-3000 is used by
	 * Entitlement.
	 */
	public static final String APP_NAME = "LILO_FOLIO";
	
	public static final String LILO_RESM ="LILO_RESM";
	
	public static final String LILO_PARTY ="LILO_PARTY";
	
	public static final String APPLICATION_NAME = "APP_NAME";

	public static final ApplicationErrorCode SALES_LOCATION_PRODUCT_ERROR = new ApplicationErrorCode(
			LILO_PARTY, 13736,
			"Exception while processing Sales Location Product");

	public static final ApplicationErrorCode REQ_FIELD_MISSING = new ApplicationErrorCode(
			APP_NAME, 2001, "Missing required fields."
			);

	public static final ApplicationErrorCode INVALID_INPUT = new ApplicationErrorCode(
			APP_NAME, 2002, "Invalid input fields."
			);

	public static final ApplicationErrorCode DUPLICATE_CHRG = new ApplicationErrorCode(
			APP_NAME, 2003, "Duplicate charge."
			);

	public static final ApplicationErrorCode AMT_REQ = new ApplicationErrorCode(
			APP_NAME, 2004, "Amount after Spread is not Zero"
			);

	public static final ApplicationErrorCode NO_EXT_REF_FOUND = new ApplicationErrorCode(
			APP_NAME, 2005, "No External References found"
			);

	public static final ApplicationErrorCode TRX_AMT_VAL_INV = new ApplicationErrorCode(
			APP_NAME, 2006, "Amount to Transfer is invalid"
			);

	public static final ApplicationErrorCode RESP_SRCH_CRIT_INV = new ApplicationErrorCode(
			APP_NAME, 2007, "Invalid Responsible Party Search Criteria"
			);

	public static final ApplicationErrorCode REQ_PST_DATES = new ApplicationErrorCode(
			APP_NAME, 2008, "Posting Dates are Required"
			);

	public static final ApplicationErrorCode DATE_RANGE_TOO_LARGE = new ApplicationErrorCode(
			APP_NAME, 2009, "Date range id too large"
			);

	public static final ApplicationErrorCode REQ_PARTY_AND_PARTY_TYP = new ApplicationErrorCode(
			APP_NAME, 2010, "Party name and party type are required together"
			);

	public static final ApplicationErrorCode REQ_DOC_NBR = new ApplicationErrorCode(
			APP_NAME, 2011,
			"Document number requires non-cash payment method type"
			);

	public static final ApplicationErrorCode REQ_BOOKING_SOURCE = new ApplicationErrorCode(
			APP_NAME, 2012,
			"If no booking source is entered, a reservation number or "
					+ "party name is required"
			);

	public static final ApplicationErrorCode NO_XFER_UNAPP = new ApplicationErrorCode(
			APP_NAME, 2013, "Cannot transfer from unapplied to unapplied"
			);

	public static final ApplicationErrorCode NO_REV_TXN = new ApplicationErrorCode(
			APP_NAME, 2014, "Cannot reverse same payment more than once"
			);

	public static final ApplicationErrorCode NO_MODIFY_LAST_NAME_PRIMARY_GUEST = new ApplicationErrorCode(
			APP_NAME, 2015,
			"Last Name of the Primary Guest should not be modified"
			);

	public static final ApplicationErrorCode REQ_TXN = new ApplicationErrorCode(
			APP_NAME, 2016, "Unapplied refunds must supply a transaction ID"
			);

	public static final ApplicationErrorCode NO_PMT_RSR = new ApplicationErrorCode(
			APP_NAME, 2017, "Cannot post payment to RSR folio"
			);

	public static final ApplicationErrorCode NEG_AMT = new ApplicationErrorCode(
			APP_NAME, 2018, "Payments cannot be negative"
			);

	public static final ApplicationErrorCode DDRAH_PAYMENT = new ApplicationErrorCode(
			APP_NAME, 2019, "Payment cannot be made to a non-disney hotel"
			);

	public static final ApplicationErrorCode NO_EVENT_CHARGE = new ApplicationErrorCode(
			APP_NAME, 2020, "No Event Charge details found"
			);

	public static final ApplicationErrorCode NO_COMMENTS = new ApplicationErrorCode(
			APP_NAME, 2021, "No Comment details found"
			);

	public static final ApplicationErrorCode CARD_GRP_PAY = new ApplicationErrorCode(
			APP_NAME, 2022, "Cannot post card payment to group pay folio"
			);

	public static final ApplicationErrorCode MULTI_FOLIOS = new ApplicationErrorCode(
			APP_NAME, 2023, "Multiple Folios Found"
			);

	public static final ApplicationErrorCode FOLIO_NOT_UNAPPLIED = new ApplicationErrorCode(
			APP_NAME, 2024, "Folio is not unapplied"
			);

	public static final ApplicationErrorCode MAX_AMT = new ApplicationErrorCode(
			APP_NAME, 2025,
			"The Payment Amount for the credit card is too high"
			);

	public static final ApplicationErrorCode INV_ACCT_CTR_PST_ACCT = new ApplicationErrorCode(
			APP_NAME, 2026, "Accounting Center posting account null"
			);

	public static final ApplicationErrorCode INV_BANK_ACCT_CTR = new ApplicationErrorCode(
			APP_NAME, 2027, "Banking Accounting Center is invalid"
			);

	public static final ApplicationErrorCode ONLY_SUPPRESS_TRANS_CAN_UNSUPPRESS = new ApplicationErrorCode(
			APP_NAME, 2028,
			"Only suppressed transactions can be un-suppressed."
			);

	public static final ApplicationErrorCode NO_CARD_ON_FILE = new ApplicationErrorCode(
			APP_NAME, 2029, "Credit card on file not found"
			);

	// 2030 is empty

	// 2031 is empty ,

	// 2032 is empty ,

	// 2034 is empty,

	// 2035 is empty

	public static final ApplicationErrorCode CARD_NAME_INV = new ApplicationErrorCode(
			APP_NAME, 2036, "Card name is invalid"
			);

	public static final ApplicationErrorCode EXPDATE_FMT_INV = new ApplicationErrorCode(
			APP_NAME, 2037, "Expiration date format invalid"
			);

	public static final ApplicationErrorCode EXPDATE_INV = new ApplicationErrorCode(
			APP_NAME, 2038, "Expiration Date may be incorrect"
			);

	public static final ApplicationErrorCode EXP_DATE_PARSE_EXCEPTION = new ApplicationErrorCode(
			APP_NAME, 2039, "Expiration Date invalid"
			);

	public static final ApplicationErrorCode FOLIO_NOT_EXIST = new ApplicationErrorCode(
			APP_NAME, 2040, "No Folio Found"
			);

	public static final ApplicationErrorCode AMT_NOT_REASONABLE = new ApplicationErrorCode(
			APP_NAME, 2041, "Amount not Reasonable"
			);

	public static final ApplicationErrorCode GRP_AMT_LESS_THAN_DELEGATES = new ApplicationErrorCode(
			APP_NAME,
			2042,
			"Remaining Amount for Group is less than total Transfer Amount to Delegates"
			);

	public static final ApplicationErrorCode REQ_SYS_NBR = new ApplicationErrorCode(
			APP_NAME, 2043, "System payment reference number is required"
			);

	public static final ApplicationErrorCode REQ_CHK_NBR = new ApplicationErrorCode(
			APP_NAME, 2044, "Check number is required"
			);

	public static final ApplicationErrorCode REQ_VCHR_NBR = new ApplicationErrorCode(
			APP_NAME, 2045, "Voucher number is required"
			);

	public static final ApplicationErrorCode REQ_BNK_FAC = new ApplicationErrorCode(
			APP_NAME, 2046, "Banking Accounting Center is required"
			);

	public static final ApplicationErrorCode DUP_AMT = new ApplicationErrorCode(
			APP_NAME, 2047, "Duplicate Amount"
			);

	public static final ApplicationErrorCode DUP_DOC = new ApplicationErrorCode(
			APP_NAME, 2048, "Duplicate Document Number"
			);

	public static final ApplicationErrorCode ARRVL_DEP_DT_INV = new ApplicationErrorCode(
			APP_NAME, 2049, "LOS Minimum duration not met"
			);

	public static final ApplicationErrorCode INV_ANCESTOR = new ApplicationErrorCode(
			APP_NAME, 2050, "Invalid ancestor"
			);

	public static final ApplicationErrorCode RES_PAST_VISIT = new ApplicationErrorCode(
			APP_NAME,
			2051,
			"This guest is checked out, do you want to proceed with this payment?"
			);

	public static final ApplicationErrorCode RES_CANCELLED = new ApplicationErrorCode(
			APP_NAME, 2052,
			"Reservation is Cancelled, cannot apply charge/payment."
			);

	public static final ApplicationErrorCode REQ_DISBURSEMENT_AMT = new ApplicationErrorCode(
			APP_NAME, 2053, "Disbursement Amount is a required parameter"
			);

	public static final ApplicationErrorCode REQ_DELEGATE_PAYMENT = new ApplicationErrorCode(
			APP_NAME, 2054, "Delegates is a required parameter"
			);

	public static final ApplicationErrorCode REQ_DISBURSEMENT_TYPE = new ApplicationErrorCode(
			APP_NAME, 2055, "Disbursement Type is a required parameter"
			);

	public static final ApplicationErrorCode REQ_GROUP_TOTAL = new ApplicationErrorCode(
			APP_NAME, 2056, "Group Total is a required parameter"
			);

	public static final ApplicationErrorCode REM_AMT_GREATER_THAN_XER_AMT = new ApplicationErrorCode(
			APP_NAME,
			2057,
			"Remaining Amount for Group is less than total Transfer Amount to Delegates"
			);

	public static final ApplicationErrorCode NAME_NOT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2058, "Name not Found"
			);

	public static final ApplicationErrorCode INV_NAME = new ApplicationErrorCode(
			APP_NAME, 2059, "Invalid Name" );

	public static final ApplicationErrorCode INV_CHRG_GRP_PERIOD = new ApplicationErrorCode(
			APP_NAME, 2060, "Invalid Charge Group Period"
			);

	public static final ApplicationErrorCode INV_CHRG_GRP_DESC = new ApplicationErrorCode(
			APP_NAME, 2061, "Invalid Charge Group - missing Description"
			);

	public static final ApplicationErrorCode INV_CHRG_GRP_PRI_REF = new ApplicationErrorCode(
			APP_NAME, 2062, "Invalid Charge Group - missing primary reference"
			);

	public static final ApplicationErrorCode INV_CHRG_GRP_REF = new ApplicationErrorCode(
			APP_NAME, 2063, "Invalid Charge Group reference"
			);

	public static final ApplicationErrorCode DUP_CHRG_GRP_REF = new ApplicationErrorCode(
			APP_NAME, 2064, "Cannot have two identical Primary References"
			);

	public static final ApplicationErrorCode INV_RPA_REQUEST = new ApplicationErrorCode(
			APP_NAME, 2065, "Invalid ResponsiblePartyAccount Request"
			);

	public static final ApplicationErrorCode INV_CHRG_GRP_STS = new ApplicationErrorCode(
			APP_NAME, 2066, "Invalid Charge group status"
			);

	public static final ApplicationErrorCode STTL_METHOD_REQ = new ApplicationErrorCode(
			APP_NAME, 2067, "Settlement method is required after check in"
			);

	public static final ApplicationErrorCode INV_PRMRY_PTY = new ApplicationErrorCode(
			APP_NAME, 2068, "Responsible Party cannot be primary"
			);

	public static final ApplicationErrorCode INVALID_EXPRESS_CHKOUT = new ApplicationErrorCode(
			APP_NAME, 2069, "Invalid Express Check Out Option"
			);

	public static final ApplicationErrorCode CANNOT_INACTIVATE_PRMRY_PTY = new ApplicationErrorCode(
			APP_NAME, 2070,
			"Primary Responsible Party Account cannot be inactivated"
			);

	public static final ApplicationErrorCode CANNOT_INACTIVATE_PTY_ACCT = new ApplicationErrorCode(
			APP_NAME,
			2071,
			"Responsible Party Account with non zero balance cannot be inactivated"
			);

	public static final ApplicationErrorCode NO_FOLIO_FOUND = new ApplicationErrorCode(
			APP_NAME, 2072, "No Folio found"
			);

	public static final ApplicationErrorCode GST_EXCD_SPND_LMT = new ApplicationErrorCode(
			APP_NAME, 2073, "Guest exceeds spend limit for folio."
			);

	public static final ApplicationErrorCode CHRG_EXCD_FOLIO_ACCT = new ApplicationErrorCode(
			APP_NAME, 2074, "Charge exceeds folio account limit."
			);

	public static final ApplicationErrorCode MOP_INV = new ApplicationErrorCode(
			APP_NAME, 2075,
			"Payment Method not available for selected payment."
			);

	public static final ApplicationErrorCode INV_DRCT_BILL_INFO = new ApplicationErrorCode(
			APP_NAME, 2076, "Invalid direct bill information."
			);

	public static final ApplicationErrorCode INV_CRDT_CARD_INFO = new ApplicationErrorCode(
			APP_NAME, 2077, "Invalid Creditcard information."
			);

	// 2078 is empty

	public static final ApplicationErrorCode NO_PAYMENT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2079, "No Payment found."
			);

	public static final ApplicationErrorCode INV_GUEST_REF = new ApplicationErrorCode(
			APP_NAME, 2080, "Invalid Guest reference."
			);

	public static final ApplicationErrorCode RESP_PARTY_EXIST = new ApplicationErrorCode(
			APP_NAME, 2081, "Responsible party already exists."
			);

	public static final ApplicationErrorCode INV_PARTY = new ApplicationErrorCode(
			APP_NAME, 2082, "Invaild party information."
			);

	public static final ApplicationErrorCode CANNOT_INACTIVATE_SETTL_METH = new ApplicationErrorCode(
			APP_NAME, 2083,
			"There must be an Active Settlement Method for each Responsible "
					+ "Party Account on a Reservation."
			);

	public static final ApplicationErrorCode CANNOT_ADD_SETTL_METH = new ApplicationErrorCode(
			APP_NAME, 2084,
			"Settlement Method cannot be added to a Reservation in a Cancelled or "
					+ "Checked Out Status." );

	public static final ApplicationErrorCode INV_RSR_SETTL_METH = new ApplicationErrorCode(
			APP_NAME, 2085, "Invaild RSR Settlement Method."
			);

	public static final ApplicationErrorCode RESP_PARTY_ACCT_INACTIVE = new ApplicationErrorCode(
			APP_NAME, 2086, "Responsible party account is inactive."
			);

	public static final ApplicationErrorCode NO_ACCT_SETTL_METH = new ApplicationErrorCode(
			APP_NAME, 2087, "No active settlement method found."
			);

	public static final ApplicationErrorCode NO_CHRG_PRIVLGE = new ApplicationErrorCode(
			APP_NAME, 2088, "Guest has no charging privileges."
			);

	public static final ApplicationErrorCode NO_CHRG_ALLOC_FOUND = new ApplicationErrorCode(
			APP_NAME, 2089, "No Allocation found"
			);

	public static final ApplicationErrorCode AMT_VAL_INV = new ApplicationErrorCode(
			APP_NAME, 2090, "Amount is invalid"
			);

	public static final ApplicationErrorCode INV_CHRG_GRP = new ApplicationErrorCode(
			APP_NAME, 2091, "Invalid Charge Group"
			);

	public static final ApplicationErrorCode CHRG_GRP_DATE_VALID_STR_DATE = new ApplicationErrorCode(
			APP_NAME, 2092, "Start Date Not Valid"
			);

	public static final ApplicationErrorCode CHRG_GRP_DATE_VALID_END_DATE = new ApplicationErrorCode(
			APP_NAME, 2093, "End Date Not Valid"
			);

	public static final ApplicationErrorCode BILLCODE_ALLOC_OVERLAP = new ApplicationErrorCode(
			APP_NAME, 2094, "Bill Code Allocations Overlap"
			);

	public static final ApplicationErrorCode INV_BILLCODE_NM = new ApplicationErrorCode(
			APP_NAME, 2095, "Invalid BillCode Name Passed"
			);

	public static final ApplicationErrorCode INV_ALLOCATION_TYPE = new ApplicationErrorCode(
			APP_NAME, 2096,
			"Invalid Allocation Type found-It should be BillCodeAllocation"
			);

	public static final ApplicationErrorCode SRC_ACCT_CNTR_MISMATCH = new ApplicationErrorCode(
			APP_NAME, 2097, "SourceAccountingCenter does not match."
			);

	public static final ApplicationErrorCode INV_CHRG_TRANSFER = new ApplicationErrorCode(
			APP_NAME, 2098, "Can't transfer Charge."
			);

	public static final ApplicationErrorCode CHRG_NOT_RECOG = new ApplicationErrorCode(
			APP_NAME, 2104, "Charge Not Recognized."
			);

	public static final ApplicationErrorCode NO_BANK_OUT = new ApplicationErrorCode(
			APP_NAME, 2099, "User is already banked in."
			);

	public static final ApplicationErrorCode INV_BAG_NUMBER = new ApplicationErrorCode(
			APP_NAME, 2100, "Bag Number already exists"
			);

	public static final ApplicationErrorCode NO_BANK_IN = new ApplicationErrorCode(
			APP_NAME, 2101, "User is not Banked In"
			);

	public static final ApplicationErrorCode BANK_OUT_SUSPENDED = new ApplicationErrorCode(
			APP_NAME, 2102, "User has suspended Bank Out"
			);

	public static final ApplicationErrorCode INV_REQUEST = new ApplicationErrorCode(
			APP_NAME, 2103, "Invalid Request"
			);

	public static final ApplicationErrorCode CHRG_NOT_ELGBL_FOR_ACB = new ApplicationErrorCode(
			APP_NAME, 3130,
			"This location/charge is not eligible for Auto Charge Back."
			);

	public static final ApplicationErrorCode CHRG_NOT_ELGBL_FOR_VOID = new ApplicationErrorCode(
			APP_NAME, 2105, "This charge is not eligible for Void."
			);

	public static final ApplicationErrorCode SENDING_EMAIL_FAILED = new ApplicationErrorCode(
			APP_NAME, 2106, "Sending Email failed"
			);

	public static final ApplicationErrorCode INV_SETTLEMENT_METHOD = new ApplicationErrorCode(
			APP_NAME, 2107, "Invalid Settlement Method"
			);

	public static final ApplicationErrorCode INV_ALLOCATION_DATA = new ApplicationErrorCode(
			APP_NAME, 2108, "Invalid Allocation set up"
			);

	public static final ApplicationErrorCode NO_BILL_CODE_FOUND = new ApplicationErrorCode(
			APP_NAME, 2109, "Cannot find Bill Code or Bill Code is inactive"
			);

	public static final ApplicationErrorCode INV_REV_CLS = new ApplicationErrorCode(
			APP_NAME, 2110, "Invaild Revenue Classification."
			);

	public static final ApplicationErrorCode INV_REV_TYP = new ApplicationErrorCode(
			APP_NAME, 2111, "Invaild Revenue Type."
			);

	public static final ApplicationErrorCode USER_NOT_BANKIN = new ApplicationErrorCode(
			APP_NAME, 2112, "User is not banked in"
			);

	public static final ApplicationErrorCode NO_MRCHNT_INFO = new ApplicationErrorCode(
			APP_NAME, 2113,
			"Unable to determine merchant information to authorization"
			);

	public static final ApplicationErrorCode CANNOT_INACTIVATE_FOLIO = new ApplicationErrorCode(
			APP_NAME, 2114,
			"Cannot inactivate folio - there is a balance on the folio"
			);

	public static final ApplicationErrorCode NO_XFER_INACTV_FOLIO = new ApplicationErrorCode(
			APP_NAME, 2115, "Cannot transfer payment to inactive folio"
			);

	public static final ApplicationErrorCode INV_BLOCK = new ApplicationErrorCode(
			APP_NAME, 2116, "No booking source found for block code"
			);

	public static final ApplicationErrorCode NO_RFND_MOP = new ApplicationErrorCode(
			APP_NAME, 2117,
			"No Refund Method defined for Accounting Center Payment Method"
			);

	public static final ApplicationErrorCode REQ_BATCH_ID = new ApplicationErrorCode(
			APP_NAME, 2118, "Batch ID is required for LockBox Payment"
			);

	public static final ApplicationErrorCode PRINTING_JOB_FAILED = new ApplicationErrorCode(
			APP_NAME, 2119, "Printing job failed"
			);

	public static final ApplicationErrorCode PRICING_ERROR = new ApplicationErrorCode(
			APP_NAME, 2120, "Error From Pricing"
			);

	public static final ApplicationErrorCode PACKAGING_ERROR = new ApplicationErrorCode(
			APP_NAME, 2121, "Error From Packaging"
			);

	public static final ApplicationErrorCode NO_DISCLAIMER_MSG = new ApplicationErrorCode(
			APP_NAME, 2122, "No Disclaimer text found for location"
			);

	public static final ApplicationErrorCode NO_BUSINESS_UNIT = new ApplicationErrorCode(
			APP_NAME, 2123, "No Business Unit defined for location"
			);

	public static final ApplicationErrorCode NO_REV_CLS = new ApplicationErrorCode(
			APP_NAME, 2124, "Revenue Classification not found"
			);

	public static final ApplicationErrorCode SRCH_NO_RESULTS = new ApplicationErrorCode(
			APP_NAME, 2125, "Search returned no results"
			);

	public static final ApplicationErrorCode NO_LOCATION = new ApplicationErrorCode(
			APP_NAME, 2126,
			"No Location found for Transaction accounting center"
			);

	public static final ApplicationErrorCode SERVICE_NOT_AVAILABLE = new ApplicationErrorCode(
			APP_NAME, 2127, "external service not available"
			);

	public static final ApplicationErrorCode TXN_NOT_AVAILABLE = new ApplicationErrorCode(
			APP_NAME,
			2128,
			"No Corresponding Transaction details available for the folio Item Container"
			);

	public static final ApplicationErrorCode INV_SEARCH_CRITERIA = new ApplicationErrorCode(
			APP_NAME, 2129, "Invalid Search Criteria"
			);

	public static final ApplicationErrorCode INV_SEARCH_DATE = new ApplicationErrorCode(
			APP_NAME, 2130, "Departure To Date must be >= today's date"
			);

	public static final ApplicationErrorCode NO_LOCATION_FOR_BUSINESS_UNIT = new ApplicationErrorCode(
			APP_NAME, 2131, "No Location found for Business Unit"
			);

	public static final ApplicationErrorCode NO_EXPRCHKOUT_EMAIL = new ApplicationErrorCode(
			APP_NAME,
			2132,
			"No Email address defined for guest to send express check out bill"
			);

	public static final ApplicationErrorCode NO_DEFAULT_REVENUETYPE_REVENUECLASS = new ApplicationErrorCode(
			APP_NAME, 2133,
			"Default RevenueClassification/RevenueType not found for "
					+ "TransactionAccountingCenter"
			);

	public static final ApplicationErrorCode INVALID_TRANSACTION_ACCOUNTINGCENTER = new ApplicationErrorCode(
			APP_NAME, 2134, "Invalid Transaction Accounting Center"
			);

	public static final ApplicationErrorCode CHARGE_GROUP_NOT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2135, "Charge Group Not Found."
			);

	public static final ApplicationErrorCode INVALID_LOCATION = new ApplicationErrorCode(
			APP_NAME, 2136, "Invalid Location"
			);

	public static final ApplicationErrorCode FOLIO_NOT_FOUND_FOR_TRAPPING = new ApplicationErrorCode(
			APP_NAME, 2137, "No Folio Found to Trap the Charge"
			);

	public static final ApplicationErrorCode NO_ACCOUNTINGCENTER_REVCLASS = new ApplicationErrorCode(
			APP_NAME, 2138,
			"No Accounting Center Revenue Classification set up found"
			);

	public static final ApplicationErrorCode INVALID_REVENUETYPE = new ApplicationErrorCode(
			APP_NAME, 2139,
			"Invalid Revenue Type set up for AccountingCenter and RevenuClassification"
					+ " combination" );

	
	public static final ApplicationErrorCode INVALID_INFORMATION = new ApplicationErrorCode(
			APP_NAME, 2480, "Invalid Information Entered"
			);

	public static final ApplicationErrorCode NO_RESULT = new ApplicationErrorCode(
			APP_NAME, 2970, "Search returned no results."
			);

	public static final ApplicationErrorCode INVALID_REQUEST = new ApplicationErrorCode(
			APP_NAME, 2180, "All required fields  have not been entered."
			);

	public static final ApplicationErrorCode INV_RES_ACTION = new ApplicationErrorCode(
			APP_NAME, 2181,
			"Operation can only be performed on a PreArrival reservation"
			);

	public static final ApplicationErrorCode NO_ACCT_CTR = new ApplicationErrorCode(
			APP_NAME, 2182, "No Accounting Center found"
			);

	public static final ApplicationErrorCode NO_PRINTER = new ApplicationErrorCode(
			APP_NAME, 2183, "No Printers defined for location"
			);

	public static final ApplicationErrorCode NO_ACB_LOCATION = new ApplicationErrorCode(
			APP_NAME, 2184,
			"This location / charge is not eligible for Auto Charge Back."
			);

	public static final ApplicationErrorCode NO_TXN_FOUND = new ApplicationErrorCode(
			APP_NAME, 2185, "No Transaction found"
			);

	public static final ApplicationErrorCode NO_XFER_SUPP_PMT = new ApplicationErrorCode(
			APP_NAME, 2186, "Cannot transfer suppressed payment"
			);

	public static final ApplicationErrorCode NO_PMT_METH = new ApplicationErrorCode(
			APP_NAME, 2187, "No Payment Method Found"
			);

	public static final ApplicationErrorCode NO_AUTH_INFO = new ApplicationErrorCode(
			APP_NAME, 2188, "Missing Authorization Information"
			);

	public static final ApplicationErrorCode AUTH_ERROR = new ApplicationErrorCode(
			APP_NAME, 2189, "Error Authorizing Payment"
			);

	public static final ApplicationErrorCode INV_TENDER_TYPE = new ApplicationErrorCode(
			APP_NAME, 2190, "Invalid tender type"
			);

	public static final ApplicationErrorCode NO_CHRG_BALANCE = new ApplicationErrorCode(
			APP_NAME, 2191, "Cannot transfer more than available balance"
			);

	public static final ApplicationErrorCode CANNOT_BANKOUT_ZERO = new ApplicationErrorCode(
			APP_NAME, 2192, "Cannot bank out zero amount"
			);

	public static final ApplicationErrorCode AMT_EXCEEDS_BALANCE = new ApplicationErrorCode(
			APP_NAME, 2193, "Amount Exceeds Balance"
			);

	public static final ApplicationErrorCode GUEST_LASTNAME_INVALID = new ApplicationErrorCode(
			APP_NAME, 2194, "InCorrect Guest Last Name"
			);

	public static final ApplicationErrorCode PARTY_NOT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2195, "Party not found in travelling guests"
			);

	public static final ApplicationErrorCode CANNOT_REV_CARD = new ApplicationErrorCode(
			APP_NAME, 2196, "Cannot reverse card payment"
			);

	public static final ApplicationErrorCode CANNOT_REF_RECOGNIZED = new ApplicationErrorCode(
			APP_NAME, 2197, "Cannot refund payment that has been recognized"
			);

	public static final ApplicationErrorCode RESRVATION_PASTVISIT = new ApplicationErrorCode(
			APP_NAME, 2240, " Reservation is Past Visit, cannot apply charge"
			);

	public static final ApplicationErrorCode INVALID_ROOM_NUMBER = new ApplicationErrorCode(
			APP_NAME, 2300, "Room Number is invalid."
			);

	public static final ApplicationErrorCode INVALID_LASTNAME = new ApplicationErrorCode(
			APP_NAME, 2210, "Last Name does not match."
			);

	public static final ApplicationErrorCode INVALID_HOTELCODE = new ApplicationErrorCode(
			APP_NAME, 2310, "Hotel Code is Invalid."
			);

	public static final ApplicationErrorCode INACTIVE_KEY = new ApplicationErrorCode(
			APP_NAME, 2320, "InActive Kttw Number"
			);

	public static final ApplicationErrorCode TRAVELLING_GUEST_NOT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2321, "Travelling Guest Not Found"
			);

	public static final ApplicationErrorCode GUEST_NOT_FOUND_IN_PARTY_DOMAIN = new ApplicationErrorCode(
			APP_NAME, 2321, "Guest Not Found"
			);

	public static final ApplicationErrorCode INV_EVNT_CHRG_GRP_STS = new ApplicationErrorCode(
			APP_NAME, 2322, "Invalid Charge group status"
			);

	public static final ApplicationErrorCode INVALID_RESERVATION_NUMBER = new ApplicationErrorCode(
			APP_NAME, 2230, "Invalid Reservation Number"
			);

	public static final ApplicationErrorCode CHARGESUSPENSEFOLIO_NOTFOUND = new ApplicationErrorCode(
			APP_NAME, 2231,
			"ChargeSuspenseFolio not found for RevenueClassification"
			);

	public static final ApplicationErrorCode NO_ACCT_GTGY = new ApplicationErrorCode(
			APP_NAME, 2232, "No accounting category defiend for the process"
			);

	public static final ApplicationErrorCode CHARGE_GROUP_CANCELLED = new ApplicationErrorCode(
			APP_NAME, 2233, "Charge group is cancelled"
			);

	public static final ApplicationErrorCode SUPPRESS_CHARGE_ERROR = new ApplicationErrorCode(
			APP_NAME, 2234, "Suppress charge cannot be Transfered"
			);

	public static final ApplicationErrorCode NO_PMT_NOSHOW = new ApplicationErrorCode(
			APP_NAME, 2235, "Cannot take auto payment for NO SHOW reservation"
			);

	public static final ApplicationErrorCode INVALID_ENTITLEMENTPLANSETUP = new ApplicationErrorCode(
			APP_NAME, 2236, "Invalid Entitlement Plan Set Up"
			);

	public static final ApplicationErrorCode BANKAC_NOT_MATCH = new ApplicationErrorCode(
			APP_NAME,
			2237,
			"Payment Banking accounting center does "
					+ "not match with banking accounting center for banked in user"
			);

	public static final ApplicationErrorCode TAXGROUPID = new ApplicationErrorCode(
			APP_NAME, 2238, "Tax Group ID Not Set Up"
			);

	public static final ApplicationErrorCode CHARGE_WITHOUTEXCEPTION = new ApplicationErrorCode(
			APP_NAME, 2239, "Charge Being Transferred has not been"
					+ " registered in Transaction Domain"
			);

	public static final ApplicationErrorCode SUSPENSE_XFER_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME, 2241, "Cannot transfer Charge from suspense folio to "
					+ " an unearned folio" );

	public static final ApplicationErrorCode XFER_TO_SUSPENSE_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME, 2242, "Cannot transfer Charge from folio to "
					+ " suspense folio" );

	public static final ApplicationErrorCode XFER_ACROSS_LEDGER_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME, 2243, "Cannot transfer from Earned to Unearned Folio, "
					+ " or from Unearned to Earned Folio"
			);

	public static final ApplicationErrorCode NO_ENTTL_PLAN_FOUND = new ApplicationErrorCode(
			APP_NAME, 2922, "No entitlement plan found."
			);

	public static final ApplicationErrorCode NO_TILL = new ApplicationErrorCode(
			APP_NAME, 2923, "Bank in with Till required."
			);

	public static final ApplicationErrorCode REPOST_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME, 2912,
			"Respost Not allowed for The Transaction Accounting Center"
			);

	public static final ApplicationErrorCode NEGATIVE_CHARGE_TRANFER = new ApplicationErrorCode(
			APP_NAME, 2924,
			" When the original amount of the transfer is a negative amount, "
					+ "	the amount to transfer cannot be greater than zero "
			);

	public static final ApplicationErrorCode POSITIVE_CHARGE_TRANFER = new ApplicationErrorCode(
			APP_NAME, 2925,
			" When the original amount of the transfer is a positive amount, "
					+ "	the amount to transfer cannot be less than zero "
			);

	public static final ApplicationErrorCode TAX_EXEMT_FROM_TO_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME,
			2926,
			"Transfer of charges are not allowed from tax exempt folio to non tax exempt folio."
			);

	public static final ApplicationErrorCode TAX_EXEMT_TO_FROM_NOT_ALLOWED = new ApplicationErrorCode(
			APP_NAME,
			2927,
			"Transfer of charges are not allowed from non tax exempt folio to tax exempt folio."
			);

	public static final ApplicationErrorCode CANNOT_UPDT_GRP_PAY_SETTLEMENT = new ApplicationErrorCode(
			APP_NAME, 2928,
			"Cannot update settlement method for a group pay folio"
			);

	public static final ApplicationErrorCode CHARGE_ACC_NOT_ACTIVE = new ApplicationErrorCode(
			APP_NAME, 2401, "Charge account is not active."
			);

	public static final ApplicationErrorCode PIN_MISMATCH = new ApplicationErrorCode(
			APP_NAME, 2402, "Pin number is invalid."
			);

	public static final ApplicationErrorCode CHRG_ACC_ALREADY_EXIST = new ApplicationErrorCode(
			APP_NAME, 2403, "Charge account already exist."
			);

	public static final ApplicationErrorCode CHRG_ACC_NOT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2400, "Charge account not found."
			);

	public static final ApplicationErrorCode INVALID_PIN = new ApplicationErrorCode(
			APP_NAME,
			2404,
			"Pin must be 4 nonsequential digits in length and all digits cannot be the same"
			);

	public static final ApplicationErrorCode PAYMENT_METHOD_END_DATE_EARLIER_THAN_CURRENT_DATE = new ApplicationErrorCode(
			APP_NAME, 2405,
			"Payment method end date should not be earlier than current date"
			);

	public static final ApplicationErrorCode CHARGEACCT_PIN_NOT_SET = new ApplicationErrorCode(
			APP_NAME, 2406, "Pin is not set on the Charge Account"
			);

	public static final ApplicationErrorCode MEDIA_NOT_ACTIVE = new ApplicationErrorCode(
			APP_NAME, 2407, "Media is not Active."
			);

	public static final ApplicationErrorCode BATCH_PROC_KILLED = new ApplicationErrorCode(
			APP_NAME, 17287, "Batch Process Terminated"
			);

	public static final ApplicationErrorCode SUSP_FOLIO_SEARCH_START_DATE_GRTR_ERROR = new ApplicationErrorCode(
			APP_NAME, 2930, "Start date cannot be greater than end date"
			);

	public static final ApplicationErrorCode SUSP_FOLIO_SEARCH_DATE_DIFF_ERROR = new ApplicationErrorCode(
			APP_NAME,
			2929,
			"Difference between start date and end date must be less than or equal to 7 days"
			);

	public static final ApplicationErrorCode SETTLEMENT_METHOD_ALREADY_INACTIVE = new ApplicationErrorCode(
			APP_NAME, 2408, "Settlement method is already inactive."
			);

	public static final ApplicationErrorCode PAYMENT_BANK_IN = new ApplicationErrorCode(
			APP_NAME, 2931,
			"Payments cannot be reversed from non Bank IN Locations "
			);

	public static final ApplicationErrorCode ALGORITHM_NOT_SUPPORTED = new ApplicationErrorCode(
			APP_NAME, 2932, "No Such Algorithm Exists"
			);

	public static final ApplicationErrorCode ENCODING_NOT_SUPPORTED = new ApplicationErrorCode(
			APP_NAME, 2933, "The Encoding Is Not Supported"
			);
	
	public static final ApplicationErrorCode INVALID_GUEST = new ApplicationErrorCode(
			APP_NAME, 2934, "Invalid Guest Information"
			);

	public static final ApplicationErrorCode INVALID_NUMBER_FORMAT = new ApplicationErrorCode(
			APP_NAME, 2935, "Invalid Number format"
			);

	public static final ApplicationErrorCode NO_ACCT_CTR_PMT_METH = new ApplicationErrorCode(
			APP_NAME, 2936, "No active Accounting Center Payment Method found"
			);

	public static final ApplicationErrorCode NO_CHRG_FOUND = new ApplicationErrorCode(
			APP_NAME, 2937, "No Charge Found"
			);

	public static final ApplicationErrorCode NO_PMT_FOUND = new ApplicationErrorCode(
			APP_NAME, 2938, "No Payment Found"
			);

	public static final ApplicationErrorCode CNTRY_NEEDS_ZIP = new ApplicationErrorCode(
			APP_NAME, 2939, "USA and CAN require zip codes for search"
			);

	public static final ApplicationErrorCode APPLICATION_SQL_EXCEPTION = new ApplicationErrorCode(
			APP_NAME, 2940, "Application SQL exception"
			);
	
	public static final ApplicationErrorCode ENCRYPTION_ERROR = new ApplicationErrorCode(
			APP_NAME, 2941, "Input PIN could not be encrypted"
			);

	public static final ApplicationErrorCode MODIFY_BILLCODE_ERROR = new ApplicationErrorCode(
			APP_NAME, 2942, "Could not retrieve delegate folio balance or Write off Amount while modifying delgate folio"
			);
	
	public static final ApplicationErrorCode DATE_PARSE_EXCEPTION = new ApplicationErrorCode(
			APP_NAME, 2943, "Could not parse the date"
			);
	public static final ApplicationErrorCode INVALID_SETTLEMENT_METHOD = new ApplicationErrorCode(
                APP_NAME, 2937, "Invalid Settlement method."
                );
	public static final ApplicationErrorCode INVALID_EXT_REF= new ApplicationErrorCode(
                  APP_NAME, 2948, "Invalid External Reference."
                  );
    public static final ApplicationErrorCode NO_PKG_ADJ= new ApplicationErrorCode(
                              APP_NAME, 2951, "Package adjustment can not be done."
                              );

    public static final ApplicationErrorCode CAN_NOT_CREATE_GSR_EVENT = new ApplicationErrorCode(
    		LILO_RESM, 9123, "Unable to Create a TPS Event"
			);

	public static final ApplicationErrorCode CAN_NOT_CREATE_CONTAINER_MODIFY_EVENT = new ApplicationErrorCode(
			LILO_RESM, 9124, "Unable to Create a Container Modify Event"
			);

	public static final ApplicationErrorCode CAN_NOT_CREATE_BILLING_PROFILE_MODIFY_EVENT = new ApplicationErrorCode(
			LILO_RESM, 9125, "Unable to Create a Billing Profile Modify Event"
			);

	public static final ApplicationErrorCode CAN_NOT_CREATE_BILLING_PROFILE_MAINTAIN_EVENT = new ApplicationErrorCode(
			LILO_RESM, 9126, "Unable to Create a Billing Profile Maintain Event"
			);

	public static final ApplicationErrorCode CAN_NOT_CREATE_CHARGE_ACCOUNT_EVENT = new ApplicationErrorCode(
			LILO_RESM, 9127, "Unable to Create a Charge Account Event"
			);

	public static final ApplicationErrorCode DFLT_FOLIO_NOT_FOUND= new ApplicationErrorCode(
			APP_NAME, 2952, "Default Folio could not be found"
			);
	
	// Release 3.3 
	public static final ApplicationErrorCode CANNOT_CREATE_SUB_ACCOUNT = new ApplicationErrorCode(
			APPLICATION_NAME, 9128, "Guest External Reference/Info not found in the request for SUB ACCOUNT "
			);
	
	public static final ApplicationErrorCode INVALID_OWNER_ACCOUNT = new ApplicationErrorCode(
			APPLICATION_NAME, 9129, "Owner Account should be a ROOT and not NODE"
			);
	
	public static final ApplicationErrorCode INVALID_SUB_ACCOUNT = new ApplicationErrorCode(
			APPLICATION_NAME, 9130, "Sub Account should be a NODE and not ROOT"
			);
	
	public static final ApplicationErrorCode PRIM_ACCT_NOT_FOUND = new ApplicationErrorCode(
			APPLICATION_NAME, 9131, "Primary Account not found for Sub Account"
			);
	
	public static final ApplicationErrorCode OWNER_CHARGE_GROUP_ID_NOT_FOUND = new ApplicationErrorCode(
			APPLICATION_NAME, 9131, "Owner Charge Group Id not found"
			);
	
	public static final ApplicationErrorCode SUBACCOUNT_CAN_BE_OF_TYPE_NODE_ONLY = new ApplicationErrorCode(
			APPLICATION_NAME, 9131, "subaccount can be of type node only not root"
			);
	public static final ApplicationErrorCode ACCOVIA_DUP_AMT= new ApplicationErrorCode(
			APP_NAME, 2953, "Duplicate Accovia Amount for Same Day"
			);
			
	public static final ApplicationErrorCode DUP_ACC_ENTRY= new ApplicationErrorCode(
			APP_NAME, 2954, "Accovia payment already processed"
			);
	
	public static final ApplicationErrorCode INVALID_ROOT_FOR_NODE= new ApplicationErrorCode(
                APP_NAME, 2955, "Node's root does not match with Root (which is existing) coming in the request."
                );
		
	public static final ApplicationErrorCode INVALID_PMT_METH= new ApplicationErrorCode(
                APP_NAME, 2956, "Payment Method is invalid- payment method:"
                );
					
	public static final ApplicationErrorCode INVALID_PMT_METH_TYP= new ApplicationErrorCode(
                APP_NAME, 2957, "Payment Method Type is invalid- payment method:"
                );
	
	public static final ApplicationErrorCode APPLICATION_EXCEPTION= new ApplicationErrorCode(
            APP_NAME, 2958, "Application Exception"
            );
	//Fix for replacing FATAL_EXCEPTION with EXCEPTION as the fatal exception causes batch to exit and drain all remaining threads from the ThreadPoolExecutor! [Fix for INC0370501]
	public static final ApplicationErrorCode KTTW_REQUIRED = new ApplicationErrorCode(
            APP_NAME, 2959, "Experience card number is required"
            );
	
	public static final ApplicationErrorCode GRP_MST_PAST_VISIT = new ApplicationErrorCode(
			APP_NAME, 2960, " Group Master folio is Past Vist or Inactive. Cannot transfer charges to this folio."
			);
	
}