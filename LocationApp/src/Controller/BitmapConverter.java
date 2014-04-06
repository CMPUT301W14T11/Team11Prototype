package Controller;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
/**
 * use this class to convert Bitmap to Json, and vice-versa
 * @author yazhou
 *
 */
public class BitmapConverter implements JsonDeserializer<Bitmap>,JsonSerializer<Bitmap>{
	
	@Override
	public JsonElement serialize(Bitmap arg0, Type arg1,
			JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		arg0.compress(Bitmap.CompressFormat.JPEG,10, stream);
		String base64Encoded = Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP);
		return new JsonPrimitive(base64Encoded);
		
	}

	@Override
	public Bitmap deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		String base64Encoded = arg0.getAsJsonPrimitive().getAsString();
		byte[] data = Base64.decode(base64Encoded, Base64.NO_WRAP);
		return BitmapFactory.decodeByteArray(data, 0, data.length);
		
	}



}
