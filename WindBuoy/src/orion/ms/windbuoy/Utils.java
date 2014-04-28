package orion.ms.windbuoy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Utils {

	public static String request(String adress){
		String answer = "", temp = ""; 
		try {
			URL url = new URL(adress);
			HttpURLConnection URLConnexion = (HttpURLConnection) url.openConnection();
			InputStream in = URLConnexion.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ( ( temp = reader.readLine() ) != null ) {
				answer = answer + temp ;
			}
			reader.close();
		} catch (Exception e) {}
		return answer;
	}
	
	/*public static void getWind(String data, Wind wind) throws JSONException{
		
		//-------> jObj
		//{
		//"st-mathieu_wind_rt":
				
				//-------> windObject
				//{
				//"date":1392148699, 
				//"dir":270,
				//"force":18.248
				//}
		
		//}
				
		JSONObject jObj = new JSONObject(data);
		Log.i("JSONObject jObj",jObj.toString());
		
		//JSONObject windObject = getObject("st-mathieu_wind_rt", jObj);
		JSONObject windObject = getObject("Renard_wind_rt", jObj);
		Log.i("JSONObject windObject",windObject.toString());
		
		wind.setDirection(getInt("dir", windObject));
		Log.i("String Wind","" + wind.direction);
		
		wind.setVelocity( (float) Utils.round(getFloat("force", windObject))  );
		Log.i("String Wind","" + wind.velocity);
	}*/
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	@SuppressWarnings("unused")
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}
	
	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
	    return (float) jObj.getDouble(tagName);
	}
	 
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getInt(tagName);
	}
	
	
	
	public static double round(float val) {return (Math.floor(val*10))/10;};
	
}
