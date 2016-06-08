package org.xBaseJ.test;
/**
 * xBaseJ - Java access to dBase files
 * <p>Copyright 1997-2014 - American Coders, LTD  - Raleigh NC USA
 * <p>All rights reserved
 * <p>Currently supports only dBase III format DBF, DBT and NDX files
 * <p>                        dBase IV format DBF, DBT, MDX and NDX files
 * <p>American Coders, Ltd
 * <br>P. O. Box 97462
 * <br>Raleigh, NC  27615  USA
 * <br>1-919-846-2014
 * <br>http://www.americancoders.com
 *
 * @author Joe McVerry, American Coders Ltd.
 * @Version 20140310
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Library Lesser General Public
 * License along with this library; if not, write to the Free
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


import junit.framework.TestCase;
import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.Field;

import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;

/**
 * @author Joe McVerry - American Coders, Ltd.
 *         <p/>
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestLockUpdateClose extends TestCase {

	public void testLockUpdateWithClose() {
		DBF writer = null;
		try {
			writer = new DBF("testfiles/temp2.dbf", true);

			Field str_field = new CharField("st", 10);
			writer.addField(str_field);

			str_field.put("abcd");
			writer.write(true);

			str_field.put("abcd2");
			writer.write(true);
			// update the first record
			writer.gotoRecord(1, true);
			str_field.put("updated");
		}
		catch (OverlappingFileLockException ignored) {
		}
		catch (Exception ex2) {
			ex2.printStackTrace();
			fail(ex2.getMessage());
		}
		try {
			writer.update(); // ----> OverlappingFileLockException
		}

		catch (Exception ex1) {
			ex1.printStackTrace();
			fail(ex1.getMessage());
		}
		try {
			writer.unlock();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
		// delete the second record
		try {
			writer.gotoRecord(2, true);
		}
		catch (Exception ex2) {
			ex2.printStackTrace();
			fail(ex2.getMessage());
		}
		try {
			writer.delete();
		}
		catch (Exception ex2) {
			ex2.printStackTrace();
			fail(ex2.getMessage());
		}
		try {
			writer.unlock();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
		try {
			writer.pack();
		}
		catch (Exception ex2) {
			ex2.printStackTrace();
			fail(ex2.getMessage());
		}

		try {
			writer.close(); // -----> incorrect descriptor
		}
		catch (IOException ex2) {
			fail(ex2.getMessage());
		}
	}
}