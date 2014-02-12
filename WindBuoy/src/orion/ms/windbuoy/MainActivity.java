package orion.ms.windbuoy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	private TextView directionTextView;
	public TextView velocityTextView;
	
	Wind wind = new Wind();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		directionTextView = (TextView) findViewById(R.id.direction);
		velocityTextView = (TextView) findViewById(R.id.velocity);
		
		new Thread(new Runnable() {		
			@Override
			public void run() {
				for(int i=0;i<100;i++){
					String data = request("http://pubs.diabox.com/dataUpdate.php?dbx_id=16&dataNameList[]=st-mathieu_wind_rt");
					try {
						getWind(data, wind);
					} catch (JSONException e1) {}
					
					directionTextView.post(new Runnable() {
						@Override
						public void run() {
							directionTextView.setText(wind.direction);
							if ( Float.parseFloat(wind.velocity) > 20) {
								velocityTextView.setBackgroundColor(Color.RED);
								velocityTextView.setScaleX(1.2f);
							}
							if ( Float.parseFloat(wind.velocity) < 20){
								velocityTextView.setBackgroundColor(Color.BLUE);
							}
							velocityTextView.setText(wind.velocity);
							velocityTextView.setScaleX(1.0f);
						}
					});
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {}
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String request(String adress){
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
		
		wind.setDirection(getString("dir", windObject));
		Log.i("String Wind",wind.direction);
		
		wind.setVelocity(getString("force", windObject));
		Log.i("String Wind",wind.velocity);
	}
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

}
