package org.xBaseJ.test;

import junit.framework.TestCase;
import org.xBaseJ.fields.DateField;
import org.xBaseJ.xBaseJException;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class TestDatePutCalendar extends TestCase {

	public void testDatePutCalendar() {
		try {
			DateField df = new DateField("test");
			df.put(Calendar.getInstance());
			df.getCalendar(TimeZone.getDefault());
		}
		catch (IOException | xBaseJException e) {
			e.printStackTrace();
		}
	}


	public void testDatePutDate() {
		try {
			DateField df = new DateField("test");
			df.put(Calendar.getInstance().getTime());
		}
		catch (IOException | xBaseJException e) {
			e.printStackTrace();
		}
	}
}
