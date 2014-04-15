package orion.ms.windbuoy;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Wind {

	int direction = 0, previousDirection = 0;
	float velocity = 0, previousVelocity = 0;
	
	public void getWind(String data, Wind wind) throws JSONException{
			
		//-------> windObject
			//{
			//"st-mathieu_wind_rt":
			
					
					//{
					//"date":1392148699, 
					//"dir":270,
					//"force":18.248
					//}
			
			//}
		
		JSONObject jObj = new JSONObject(data);
		//Log.i("JSONObject jObj",jObj.toString());
		
		JSONObject windObject = getObject("st-mathieu_wind_rt", jObj);
		//Log.i("JSONObject windObject",windObject.toString());
		
		
		this.setDirection(getInt("dir", windObject));
		Log.i("String Wind","" + wind.direction);
		
		this.setVelocity( (float) Utils.round(getFloat("force", windObject))  );
		//Log.i("String Wind","" + wind.velocity);
			
		//{
			//    "valid": true,
			//    "id": "1",
			//    "gps": {
			//	        "time": 1396560015,
			//	        "longitude": "0.99711",
			//	        "latitude": "47.4108"
			//    },
			//    "wind": {
			//	        "time": 1396560016,
			//	        "gust10": 0.8,
			//	        "avg10": 0.4,
			//	        "snapshot": 0.6,
			//	        "way": "82"
			//    },
			//    "temperature": {
			//	        "time": 1396560016,
			//	        "air": 22
			//    }
		//}
			
		/*
			JSONObject jObj = new JSONObject(data);
			Log.i("JSONObject jObj",jObj.toString());
			
			//check if the server send valid data
		    boolean validity = getBool("valid", jObj);
		    Log.i("Validity","" + validity);
			
			//check if the data come from the first or the second buoy
			int buoyNumber = getInt("id", jObj);
		    Log.i("buoyNumber","" + buoyNumber );
		
			//gather data from gps of the buoy
			JSONObject gpsObject = getObject("gps", jObj);
			Log.i("JSONObject gpsObject",gpsObject.toString());
		
			//get the wind
		    JSONObject windObject = getObject("wind", jObj);
			Log.i("JSONObject windObject",windObject.toString());
						
			
			this.setDirection(getInt("way", windObject));
			Log.i("String Wind","" + wind.direction);
			
			this.setVelocity( (float) Utils.round(getFloat("snapshot", windObject))  );
			Log.i("String Wind","" + wind.velocity);
			*/
			
			//Calculate delta
			int deltaDirection = java.lang.Math.abs( this.previousDirection - this.direction );
			if (deltaDirection > 180) deltaDirection = java.lang.Math.abs(deltaDirection-360);
			if (this.direction - this.previousDirection < 0) deltaDirection = -deltaDirection;
			Log.i("deltaDirection","" + deltaDirection);
			
			
			//todo
			float deltaVelocity = this.velocity - this.previousVelocity;
			//Log.i("deltaVelocity","" + wind.deltaVelocity);
			
			//todo
			//refresh data
			this.setPreviousDirection(this.direction);
			Log.i("previousDirection","" + wind.previousDirection);
			
			this.setPreviousVelocity(this.velocity);
			//Log.i("previousVelocity","" + wind.previousVelocity);
			
			
			
			
			
		}

	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
	    return (float) jObj.getDouble(tagName);
	}
	 
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getInt(tagName);
	}
	
	private static boolean  getBool(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getBoolean(tagName);
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

	public int getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(int previousDirection) {
		this.previousDirection = previousDirection;
	}

	public float getPreviousVelocity() {
		return previousVelocity;
	}

	public void setPreviousVelocity(float previousVelocity) {
		this.previousVelocity = previousVelocity;
	}
	
	
	
		
}
