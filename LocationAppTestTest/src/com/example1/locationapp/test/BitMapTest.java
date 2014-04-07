package com.example1.locationapp.test;

import com.example1.locationapp.SubCommetsRead;
import com.google.gson.JsonElement;

import Controller.BitmapConverter;
import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

/**
* JUnit test cases for BitmapConverter.
*
* @author cye2
*
*/

public class BitMapTest extends ActivityInstrumentationTestCase2<SubCommetsRead> {

	private Bitmap bitmap = Bitmap.createBitmap(10,10 ,Bitmap.Config.ARGB_8888);
	private BitmapConverter bitmapConverter = new BitmapConverter();
	public BitMapTest() {
		super(SubCommetsRead.class);
	}

	
	/**
	* This is a test for BitmapConverter.
	* From the test below we can see that
	* BitmapConverter can both serizlize and deserizlize
	*
	*/
	public void testLBitmapConverter() throws Exception {
		
		JsonElement jsonElement = bitmapConverter.serialize(bitmap, null, null);
		Bitmap newBitmap = bitmapConverter.deserialize(jsonElement, null, null);

		
		
		assertEquals(bitmap.getByteCount(), newBitmap.getByteCount());
		assertEquals(bitmap.getHeight(), newBitmap.getHeight());
		assertEquals(bitmap.getDensity(), newBitmap.getDensity());
		assertEquals(bitmap.getRowBytes(), newBitmap.getRowBytes());

		tearDown();
}

}
