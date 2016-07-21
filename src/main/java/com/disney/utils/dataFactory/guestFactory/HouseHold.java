package com.disney.utils.dataFactory.guestFactory;

import java.util.ArrayList;
import java.util.Calendar;

import org.testng.Reporter;

import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

/**
 * @summary Container for multiple Guests to store random or pre-defined Guest
 *          Information
 * @author Justin Phlegar
 * @version 11/28/2015 Justin Phlegar - original
 * @see com.disney.utils.dataFactory.guestFactory.Guest
 */
public class HouseHold {
	private ArrayList<Guest> guests = new ArrayList<Guest>();

	/**
	 * @summary Create guests and populate them with random data. The first
	 *          guest created will be marked as primary. The first address,
	 *          phone and email will be marked as primary for each guest
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public HouseHold(int numberOfGuests) {
		TestReporter.logDebug("Entering HouseHold#init with number of Guests");
		TestReporter.logInfo("Creating party with ["+numberOfGuests+"] guests");
		
		for (int x = 0; x < numberOfGuests; x++) {
			TestReporter.logDebug("Generating Guest [" + (numberOfGuests+1) + "]");	
			addGuest(new Guest());

			TestReporter.logDebug("Setting Guest [" +(x+1)+"] Address, Phone and Email to primary Guest");
			guests.get(x).getAllAddresses().get(0).setPrimary(true);
			guests.get(x).getAllAddresses().get(0).setStreetName(guests.get(0).primaryAddress().getStreetName());
			guests.get(x).getAllAddresses().get(0).setStreetNumber(guests.get(0).primaryAddress().getStreetNumber());
			guests.get(x).getAllAddresses().get(0).setCity(guests.get(0).primaryAddress().getCity());
			guests.get(x).getAllAddresses().get(0).setState(guests.get(0).primaryAddress().getState());
			guests.get(x).getAllAddresses().get(0).setStateAbbv(guests.get(0).primaryAddress().getStateAbbv());
			guests.get(x).getAllAddresses().get(0).setZipCode(guests.get(0).primaryAddress().getZipCode());
			guests.get(x).getAllAddresses().get(0).setOptIn(true);
			guests.get(x).getAllPhones().get(0).setPrimary(true);
			guests.get(x).getAllEmails().get(0).setPrimary(true);
			TestReporter.logInfo("\n"+guests.get(x).toString().replace("<br/>", "\n"));
		}

		TestReporter.logDebug("Set first guest as Primary Guest");
		guests.get(0).setPrimary(true);
		TestReporter.logDebug("Ensure first guest is older than 18");
		if (Integer.parseInt(guests.get(0).getAge()) <= 18) {
			guests.get(0).setAge("45");
			guests.get(0).setChild(false);
			guests.get(0).setBirthDate("1970-01-14");
		}
		TestReporter.logDebug("Entering HouseHold#init with number of Guests");
	}

	public HouseHold(String guestScenario) {
		TestReporter.logDebug("Entering HouseHold#init with Guest Scenario");
		TestReporter.logInfo("Creating party with scenario ["+guestScenario+"]");
		Datatable datatable = new Datatable();
		datatable.setVirtualtablePath("METADATA_");
		datatable.setVirtualtablePage("GUESTS");
		datatable.setVirtualtableScenario(guestScenario);
		boolean finishedAdults = false;
		boolean finishedChildren = false;
		int numberAdultsCreated = 0;
		int numberChildrenCreated = 0;
		int numberInfantsCreated = 0;

		int numberAdults = Integer.valueOf(datatable.getDataParameter("NumberAdults"));
		int numberChildren = Integer.valueOf(datatable.getDataParameter("NumberChildren"));
		int numberInfants = Integer.valueOf(datatable.getDataParameter("NumberInfants"));
		int houseHoldSize = numberAdults + numberChildren + numberInfants;
		
		TestReporter.logInfo(String.format("Adults [%s], Children [%s], Infants [%s]",numberAdults,numberChildren,numberInfants));
		for (int x = 0; x < houseHoldSize; x++) {

			TestReporter.logDebug("Setting Guest [" +(x+1)+"] Address, Phone and Email to primary Guest");
			addGuest(new Guest());

			guests.get(x).getAllAddresses().get(0).setPrimary(true);
			guests.get(x).getAllAddresses().get(0).setStreetName(guests.get(0).primaryAddress().getStreetName());
			guests.get(x).getAllAddresses().get(0).setStreetNumber(guests.get(0).primaryAddress().getStreetNumber());
			guests.get(x).getAllAddresses().get(0).setCity(guests.get(0).primaryAddress().getCity());
			guests.get(x).getAllAddresses().get(0).setState(guests.get(0).primaryAddress().getState());
			guests.get(x).getAllAddresses().get(0).setStateAbbv(guests.get(0).primaryAddress().getStateAbbv());
			guests.get(x).getAllAddresses().get(0).setZipCode(guests.get(0).primaryAddress().getZipCode());
			guests.get(x).getAllAddresses().get(0).setOptIn(true);
			guests.get(x).getAllPhones().get(0).setPrimary(true);
			guests.get(x).getAllEmails().get(0).setPrimary(true);
			TestReporter.logInfo("\n "+guests.get(x).toString().replace("<br/>", "\n"));

			if (numberAdultsCreated < numberAdults
					/*&& Integer.valueOf(guests.get(x).getAge()) < 18*/) {
				guests.get(x).setAge(
						String.valueOf(Randomness.randomNumberBetween(18, 99)));
				guests.get(x).setBirthDate(
						"01/01/"
								+ (Integer.valueOf(Calendar.getInstance().get(
										Calendar.YEAR)) - Integer
										.valueOf(guests.get(x).getAge())));
				numberAdultsCreated++;
			}

			else
				finishedAdults = true;

			if (finishedAdults && numberChildrenCreated < numberChildren) {
				guests.get(x).setAge(
						String.valueOf(Randomness.randomNumberBetween(3, 17)));
				guests.get(x).setBirthDate(
						"01/01/"
								+ (Integer.valueOf(Calendar.getInstance().get(
										Calendar.YEAR)) - Integer
										.valueOf(guests.get(x).getAge())));
				numberChildrenCreated++;
			} else if (numberChildrenCreated == numberChildren) {
				finishedChildren = true;
			}

			if (finishedAdults && finishedChildren
					&& numberInfantsCreated < numberInfants) {
				guests.get(x).setAge(
						String.valueOf(Randomness.randomNumberBetween(1, 2)));
				guests.get(x).setBirthDate(
						"01/01/"
								+ (Integer.valueOf(Calendar.getInstance().get(
										Calendar.YEAR)) - Integer
										.valueOf(guests.get(x).getAge())));
				numberInfantsCreated++;
			}
		}


		TestReporter.logDebug("Set first guest as Primary Guest");
		guests.get(0).setPrimary(true);
		TestReporter.logDebug("Ensure first guest is older than 18");
		if (Integer.valueOf(guests.get(0).getAge()) < 18) {
			guests.get(0).setAge(
					String.valueOf(Randomness.randomNumberBetween(18, 99)));
		}

		TestReporter.logDebug("Exiting HouseHold#init with Guest Scenario");
	}

	public void sendToApi(String environment) {
		TestReporter.logStep("Creating guest via GoMaster API");
		for (int x = 0; x < guests.size(); x++) {
			guests.get(x).sendToApi(environment);
			TestReporter.log(guests.get(x).toString());
			Reporter.log("");
		}
	}

	/**
	 * @summary Associate a new Guest to the guest using preset data
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public void addGuest(Guest guest) {
		guests.add(guest);
	}

	/**
	 * @summary Return the guest marked as Primary
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the HouseHolds primary Guest
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public Guest primaryGuest() {
		Guest primaryGuest = null;

		for (Guest guest : guests) {
			if (guest.isPrimary())
				primaryGuest = guest;
		}

		return primaryGuest;
	}

	/**
	 * @summary Return all Guests associated to the HouseHold
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return all Guests as an ArrayList
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public ArrayList<Guest> getAllGuests() {
		return guests;
	}

	/**
	 * @summary Return the number of Children currently in Household
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the number of children in HouseHold
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public String numberOfChildren() {
		int numberOfChildren = 0;
		for (Guest guest : guests) {
			if (guest.isChild() && Integer.valueOf(guest.getAge()) > 2
					&& Integer.valueOf(guest.getAge()) < 18)
				numberOfChildren++;
		}
		return String.valueOf(numberOfChildren);
	}

	public String numberOfInfants() {
		int numberOfInfant = 0;
		for (Guest guest : guests) {
			if (guest.isChild() && Integer.valueOf(guest.getAge()) <= 2)
				numberOfInfant++;
		}
		return String.valueOf(numberOfInfant);
	}

	/**
	 * @summary Return the number of adults currently in Household
	 * @author Justin Phlegar
	 * @version 11/28/2015 Justin Phlegar - original
	 * @return the number of adults in HouseHold
	 * @see com.disney.utils.dataFactory.guestFactory.Guest
	 */
	public String numberOfAdults() {
		int numberOfAdults = 0;
		for (Guest guest : guests) {
			if (!guest.isChild())
				numberOfAdults++;
		}
		return String.valueOf(numberOfAdults);
	}
}