package orion.ms.windbuoy;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Wind {

	int direction = 0;
	float velocity = 0;
	
	public void getWind(String data, Wind wind) throws JSONException{
			
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
			
			JSONObject windObject = getObject("st-mathieu_wind_rt", jObj);
			Log.i("JSONObject windObject",windObject.toString());
			
			this.setDirection(getInt("dir", windObject));
			Log.i("String Wind","" + wind.direction);
			
			this.setVelocity( (float) Utils.round(getFloat("force", windObject))  );
			Log.i("String Wind","" + wind.velocity);
		}

	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}
	
	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
	    return (float) jObj.getDouble(tagName);
	}
	 
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getInt(tagName);
	}
	
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
		
}
