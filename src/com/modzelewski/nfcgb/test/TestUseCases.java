package com.modzelewski.nfcgb.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.Solo;
import com.modzelewski.nfcgb.MainActivity;
import com.modzelewski.nfcgb.R;

public class TestUseCases extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;

	public TestUseCases() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		solo.assertCurrentActivity("Check on first activity",
				MainActivity.class);
	}

	@Suppress
	public void testUseCase01CreateEvent() {
		Spinner eventSpinner = (Spinner) solo.getView(R.id.events_spinner);
		int eventCount = eventSpinner.getCount();
		Log.i(getName(), "Eventcount = " + String.valueOf(eventCount));
		assertNotNull(eventSpinner);
		assertNotNull(eventCount);

		View labelForCreateEvent = solo.getView(R.id.om_add_event);
		solo.clickOnView(labelForCreateEvent);

		EditText eventNameEditText = (EditText) solo.getView(R.id.ed_eventname);
		solo.enterText(eventNameEditText,
				solo.getString(R.string.use_case_1_eventname));

		solo.clickOnButton(solo.getString(R.string.ok_button));

//		solo.clickOnView(labelForCreateEvent); // EventSpinner changes his count
												// only at click on it
		
		solo.clickOnView(eventSpinner);
		int newEventCount = eventSpinner.getCount();
		Log.i(getName(), "newEventCount = " + String.valueOf(newEventCount));
		assertTrue(newEventCount == eventCount + 1);
	}

	@Suppress
	public void testUseCase02EditEvent() {
		Spinner eventSpinner = (Spinner) solo.getView(R.id.events_spinner);
		int eventCount = eventSpinner.getCount();
		String newText = solo.getString(R.string.use_case_2_eventname);
		assertNotNull(eventSpinner);
		assertNotNull(eventCount);

		String labelForEditEvent = solo.getString(R.string.edit_event);
		solo.clickLongOnView(eventSpinner);
		solo.clickOnText(labelForEditEvent);

		EditText eventNameEditText = (EditText) solo.getView(R.id.ed_eventname);
		solo.clearEditText(eventNameEditText);
		solo.enterText(eventNameEditText, newText);

		solo.clickOnButton(solo.getString(R.string.ok_button));

		String eventSpinnerLabel = eventSpinner.getSelectedItem().toString();
		Log.i(getName(), "Spinnerlabel = " + eventSpinnerLabel);

		assertTrue(eventSpinnerLabel.contains(newText));

	}
	@Suppress
	public void testUseCase03ChangeEvent() {
		Spinner eventSpinner = (Spinner) solo.getView(R.id.events_spinner);
		
		//create 2 events - same as in BackgroundModelTest
		int eventCountBefore = eventSpinner.getCount();
		assertNotNull(eventSpinner);
		assertNotNull(eventCountBefore);
		View labelForCreateEvent = solo.getView(R.id.om_add_event);
		// add event 1
		solo.clickOnView(labelForCreateEvent);
		EditText eventNameEditText = (EditText) solo.getView(R.id.ed_eventname);
		solo.enterText(eventNameEditText, solo.getString(R.string.use_case_1_eventname));
		solo.clickOnButton(solo.getString(R.string.ok_button));
		//add event 2
		solo.clickOnView(labelForCreateEvent);
		EditText eventNameEditText2 = (EditText) solo.getView(R.id.ed_eventname);
		solo.enterText(eventNameEditText2, solo.getString(R.string.use_case_3_eventname));
		solo.clickOnButton(solo.getString(R.string.ok_button));
		solo.clickOnView(eventSpinner);
		int eventCountAfter = eventSpinner.getCount();
		assertTrue(eventCountAfter -2 == eventCountBefore);
		
		solo.clickOnText(solo.getString(R.string.use_case_1_eventname));
//		assertTrue(); don't know
	}

	public void testUseCase04RemoveEvent() {
		Spinner eventSpinner = (Spinner) solo.getView(R.id.events_spinner);
		int eventCountBefore = eventSpinner.getCount();
		Log.i(getName(), "Eventcount = " + String.valueOf(eventCountBefore));
		assertNotNull(eventSpinner);
		assertNotNull(eventCountBefore);

		String labelForRemoveEvent = solo.getString(R.string.remove_event);
		solo.clickLongOnView(eventSpinner);
		solo.clickOnText(labelForRemoveEvent);
		solo.clickOnButton(solo.getString(R.string.ok_button));

		solo.clickOnView(eventSpinner);
		int eventCountAfter = eventSpinner.getCount();
		Log.i("USECASEREMOVE", "count before = \n\r" + eventCountBefore + "\ncount after = \n\t" + eventCountAfter);
		assertEquals(eventCountAfter, eventCountBefore-1);
	}
	
	public void testUseCase04RemoveEventWhileMoreThanOne() {
		Spinner eventSpinner = (Spinner) solo.getView(R.id.events_spinner);
		String labelForRemoveEvent = solo.getString(R.string.remove_event);

		int i = eventSpinner.getCount();
		while(i > 1) {
			assertNotNull(eventSpinner);
			solo.clickLongOnView(eventSpinner);
			solo.clickOnText(labelForRemoveEvent);
			solo.clickOnButton(solo.getString(R.string.ok_button));
			i--;
		}
		
//		Log.i("USECASEREMOVE", "count before = \n\r" + eventCountBefore + "\ncount after = \n\t" + eventCountAfter);
		
	}
	
	@Override
	protected void tearDown() throws Exception {

		solo.finishOpenedActivities();
	}
}