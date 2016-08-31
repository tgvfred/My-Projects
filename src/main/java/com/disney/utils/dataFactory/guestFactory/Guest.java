package com.disney.utils.dataFactory.guestFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.disney.api.soapServices.bussvcsModule.guestServiceV2.operations.Create;
import com.disney.api.soapServices.bussvcsModule.guestServiceV2.operations.SearchByNameAndAddress;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.partyModule.partyService.operations.CreateParty;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

/**
 * @summary Container to store random or pre-defined Guest Information
 * @author Justin Phlegar
 * @version 11/28/2015 Justin Phlegar - original
 * @see com.disney.utils.dataFactory.guestFactory.HouseHold
 */
@SuppressWarnings("unused")
public class Guest {
	Random random = new Random();
	private ArrayList<Address> addresses = new ArrayList<Address>();
	private ArrayList<Phone> phones = new ArrayList<Phone>();
	private ArrayList<Email> emails = new ArrayList<Email>();
	private String guestSeedPath = "/com/disney/utils/guestFactory/seeds/";
	private String[] maleFirstNames = Randomness.seedReader(guestSeedPath
			+ "MaleFirstNames");
	private String[] femaleFirstNames = Randomness.seedReader(guestSeedPath
			+ "FemaleFirstNames");
	private String[] lastNames = Randomness.seedReader(guestSeedPath
			+ "LastNames");
	private boolean isPrimary = false;
	private String title = "";
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
	private String suffix = "";
	private String certification = "";
	private String nickname = "";
	private String birthDate = "";
	private String age = "";
	private String languagePreference = "";
	private String ssn = "";
	private String username = "";
	private String password = "";
	private boolean deceased = false;
	private boolean isChild = false;
	private String odsId = "0";
	private String partyId = "0";
	private String guestId = "0";
	private Create guest = null;
	
	private SearchByNameAndAddress search = null;
	/**
	 * @summary Upon instantiation, generate name guest data, along with
	 *          address, phone, and email data
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 */
	public Guest() {
		populateSeededData();	
		if (Integer.parseInt(getAge()) <= 18) {
			setAge("45");
			setChild(false);
			setBirthDate("1970-01-14");
		}
	}

	public void sendToApi(String environment) {
		sendToApi(environment, false);
	}
	
	public void sendToApi(String environment, boolean isPartyV3) {
		if(!environment.equalsIgnoreCase("Development") && !environment.contains("_CM") && !environment.contains("_cm")){
			guest = new Create(environment, "Main");
			guest.setPrefix(title);
			guest.setFirstName(firstName);
			guest.setMiddleName(middleName);
			guest.setLastName(lastName);
			guest.setAge(age);
			guest.setPhoneNumber(phones.get(0).getNumber());
			guest.setEmail(emails.get(0).getEmail());
			guest.setAddress1(addresses.get(0).getAddress1());
			guest.setCity(addresses.get(0).getCity());
			guest.setCountry(addresses.get(0).getCountry());
			guest.setState(addresses.get(0).getStateAbbv());
			guest.setPostalCode(addresses.get(0).getZipCode());
			guest.sendRequest();
			try{
				addresses.get(0).setZipCode(guest.getPostalCode());
			}catch(XPathNotFoundException x){

				guest.sendRequest();
				try{
					addresses.get(0).setZipCode(guest.getPostalCode());
				}catch(XPathNotFoundException x2){
					TestReporter.logAPI(true, "GoMaster Guest create Zipcode not found", guest);
				}
			}
			}
		Sleeper.sleep(2000);
		if(isPartyV3 || environment.equalsIgnoreCase("Development") || environment.equalsIgnoreCase("Latest_CM")){
			com.disney.api.soapServices.partyModule.partyV3.operations.CreateParty partyV3 = null;
			partyV3 = new com.disney.api.soapServices.partyModule.partyV3.operations.CreateParty(environment, "SampleCreate");
			if(!environment.equalsIgnoreCase("Development") && !environment.contains("_CM") && !environment.contains("_cm")){
				odsId = guest.getOdsGuestId();
				addresses.get(0).setZipCode(guest.getPostalCode());
				addresses.get(0).setLocatorId(guest.getAddressLocatorId());
				phones.get(0).setLocatorId(guest.getPhoneLocatorId());
				emails.get(0).setLocatorId(guest.getEmailLocatorId());
			
			}else{
				odsId = String.valueOf(Randomness.randomNumberBetween(1000000, 9999999));
			}
			
			partyV3.setExternalReferenceValue(odsId);
			partyV3.setPrimaryGuestAge(age);
			partyV3.setPrimaryGuestFirstName(firstName);
			partyV3.setPrimaryGuestLastName(lastName);
			
			partyV3.setAddressLocatorId(addresses.get(0).getLocatorId());
			partyV3.setAddressLine1(addresses.get(0).getAddress1());
			partyV3.setAddressCity(addresses.get(0).getCity());
			partyV3.setAddressState(addresses.get(0).getState());
			partyV3.setAddressZipCode(addresses.get(0).getZipCode());
			partyV3.setAddressCountry(addresses.get(0).getCountry());
			
			partyV3.setPhoneNumberLocatorId(phones.get(0).getLocatorId());
			partyV3.setPhoneNumber(phones.get(0).getNumber());
			
			partyV3.setEmailAddressLocatorId(emails.get(0).getLocatorId());
			partyV3.setEmailAddress(emails.get(0).getEmail());
			
			//party.setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/externalreference/externalReferenceSource", "ODS");
			partyV3.sendRequest();
			
			this.partyId = partyV3.getPartyid();
		}else{
			CreateParty party = null;
			if(!environment.equalsIgnoreCase("Development") && !environment.contains("_CM") && !environment.contains("_cm")){
				odsId = guest.getOdsGuestId();
				addresses.get(0).setZipCode(guest.getPostalCode());
				addresses.get(0).setLocatorId(guest.getAddressLocatorId());
				phones.get(0).setLocatorId(guest.getPhoneLocatorId());
				emails.get(0).setLocatorId(guest.getEmailLocatorId());
			}else{
				odsId = String.valueOf(Randomness.randomNumberBetween(1000000, 9999999));
			}
			
			party = new CreateParty(environment, "SampleCreate");
			party.setExternalReferenceValue(odsId);
			party.setPrimaryGuestAge(age);
			party.setPrimaryGuestFirstName(firstName);
			party.setPrimaryGuestLastName(lastName);
			
			party.setAddressLocatorId(addresses.get(0).getLocatorId());
			party.setAddressLine1(addresses.get(0).getAddress1());
			party.setAddressCity(addresses.get(0).getCity());
			party.setAddressState(addresses.get(0).getState());
			party.setAddressZipCode(addresses.get(0).getZipCode());
			party.setAddressCountry(addresses.get(0).getCountry());
			
			party.setPhoneNumberLocatorId(phones.get(0).getLocatorId());
			party.setPhoneNumber(phones.get(0).getNumber());
			
			party.setEmailAddressLocatorId(emails.get(0).getLocatorId());
			party.setEmailAddress(emails.get(0).getEmail());
			
			//party.setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/externalreference/externalReferenceSource", "ODS");
			
			party.sendRequest();
			
			this.partyId = party.getPartyid();
		}
	/*	search = new SearchByNameAndAddress(environment,
				"GuestSearch-LastName_FirstName_StreetAddress_ZipCode");
		search.setLastName(lastName);
		search.setFirstName(firstName);
		search.setAddress(addresses.get(0).getAddress1());
		search.setPostalCode(addresses.get(0).getZipCode());
		search.sendRequest();
		
		try{
			odsId = guest.getOdsGuestId();
		}catch(Exception ee){
			throw new AutomationException("An error occurred retrieving the ODS Guest ID. \nGuest Response:\n "+guest.getResponse()+"\nSearch Request:\n"+search.getRequest()+"Search Response:\n" + search.getResponse());
		}*/
	}

	public void  printGuestCreateResponse(){
		System.out.println(guest.getResponse());
	}
	public void  printGuestSearchResponse(){
	//	System.out.println(search.getResponse());
	}
	public String getOdsId() {
		return odsId;
	}
	public String getPartyId(){
		return partyId;
	}

	public String getGuestId(){
		return guestId;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.substring(0, 1).toUpperCase()
				+ firstName.substring(1).toLowerCase();
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.substring(0, 1).toUpperCase()
				+ lastName.substring(1).toLowerCase();
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getAge() {
		if (Integer.parseInt(age) < 0)
			return "0";
		return age;
	}

	public void setAge(String age) {
		this.age = age;
		if (Integer.parseInt(age) <= 18)
			isChild = true;
		else
			isChild = false;
	}

	public String getLanguagePreference() {
		return languagePreference;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public boolean getDeceased() {
		return deceased;
	}

	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isChild() {
		return (Integer.parseInt(getAge()) <= 18);
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	public void setOdsId(String odsId) {
		this.odsId = odsId;
	}
	public void setPartyId(String partyId){
		this.partyId = partyId;
	}

	public void setGuestId(String guestId){
		this.guestId = guestId;
	}
	/**
	 * @summary Return all addresses associated to the Guest
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return all addresses as an ArrayList
	 * @see com.disney.utils.dataFactory.guestFactory.Address
	 */
	public ArrayList<Address> getAllAddresses() {
		return addresses;
	}

	/**
	 * @summary Associate a new Address to the guest using random data. Will be
	 *          marked not a primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Address
	 */
	public void addAddress() {
		addresses.add(new Address());
	}

	/**
	 * @summary Associate a new Address to the guest using preset data
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Address
	 */
	public void addAddress(Address address) {
		addresses.add(address);
	}

	/**
	 * @summary Return all phone numbers associated to the Guest
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return all phones as an ArrayList
	 * @see com.disney.utils.dataFactory.guestFactory.Phone
	 */
	public ArrayList<Phone> getAllPhones() {
		return phones;
	}

	/**
	 * @summary Associate a new Phone to the guest using random data. Will be
	 *          marked not a primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Phone
	 */
	public void addPhone() {
		phones.add(new Phone());
	}

	/**
	 * @summary Associate a new Phone to the guest using preset data
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Phone
	 */
	public void addPhone(Phone phone) {
		phones.add(phone);
	}

	/**
	 * @summary Return all email addresses associated to the Guest
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return all email addresses as an ArrayList
	 * @see com.disney.utils.dataFactory.guestFactory.Email
	 */
	public ArrayList<Email> getAllEmails() {
		return emails;
	}

	/**
	 * @summary Associate a new Email to the guest using random data. Will be
	 *          marked not a primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Email
	 */
	public void addEmail() {
		emails.add(new Email());
	}

	/**
	 * @summary Associate a new Email to the guest using preset data
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Email
	 */
	public void addEmail(Email email) {
		emails.add(email);
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * @summary Return the address marked as Primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the Guest's primary Address
	 * @see com.disney.utils.dataFactory.guestFactory.Address
	 */
	public Address primaryAddress() {
		Address primaryAddress = null;

		for (Address address : addresses) {
			if (address.isPrimary())
				primaryAddress = address;
		}

		return primaryAddress;
	}

	/**
	 * @summary Return the phone number marked as Primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the Guest's primary Phone Number
	 * @see com.disney.utils.dataFactory.guestFactory.Phone
	 */
	public Phone primaryPhone() {
		Phone primaryPhone = null;

		for (Phone phone : phones) {
			if (phone.isPrimary())
				primaryPhone = phone;
		}

		return primaryPhone;
	}

	/**
	 * @summary Return the Email Address marked as Primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the Guest's primary Email Address
	 * @see com.disney.utils.dataFactory.guestFactory.Email
	 */
	public Email primaryEmail() {
		Email primaryEmail = null;

		for (Email email : emails) {
			if (email.isPrimary())
				primaryEmail = email;
		}

		return primaryEmail;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = "<br/>";

		result.append(" Name: " + title + " " + firstName + " " + lastName
				+ NEW_LINE);
		result.append(" Age: " + age + NEW_LINE);
		result.append(" Primary Street Address: "
				+ primaryAddress().getAddress1() + NEW_LINE);
		result.append(" Priamry Local Info: " + primaryAddress().getCity()
				+ ", " + primaryAddress().getStateAbbv() + " "
				+ primaryAddress().getZipCode() + NEW_LINE);
		result.append(" Primary Phone: " + primaryPhone().getNumber()
				+ NEW_LINE);
		result.append(" Primary Email: " + primaryEmail().getEmail() + NEW_LINE);

		return result.toString();
	}

	private void populateSeededData() {

		boolean isMale = (Math.random() < .5);

		if (isMale) {
			this.title = "Mr.";
			setFirstName((String) Randomness.randomizeArray(maleFirstNames));
		} else {
			this.title = "Mrs.";
			setFirstName((String) Randomness.randomizeArray(femaleFirstNames));
		}

		this.middleName = "Automation";
		setLastName((String) Randomness.randomizeArray(lastNames));

		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
		Date date = Randomness.randomDate();
		String dateOfBirth = format.format(date);

		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		Calendar today = Calendar.getInstance();
		int convertedAge = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
			convertedAge--;

		if (convertedAge < 0)
			convertedAge = 0;
		this.age = String.valueOf(convertedAge);
		this.birthDate = DateTimeConversion.convert(dateOfBirth.toString(),
				"yyyy-MM-dd'T'hh:mm:ss'Z'", "yyyy-MM-dd");
		this.isChild = Integer.parseInt(getAge()) <= 18;
		this.username = getFirstName() + "." + getLastName();
		this.password = "Blah123!";
		this.ssn = Randomness.randomNumber(3) + "-"
				+ Randomness.randomNumber(2) + "-" + Randomness.randomNumber(4);
		addAddress(new Address());
		addPhone(new Phone());
		addEmail(new Email());
		addresses.get(0).setPrimary(true);
		phones.get(0).setPrimary(true);
		emails.get(0).setPrimary(true);
		emails.get(0).setEmail(
				getFirstName() + "." + getLastName() + "@AutomatedTesting.com");
	}

}
