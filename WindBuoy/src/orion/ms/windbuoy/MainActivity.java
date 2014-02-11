package orion.ms.windbuoy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView text = null; 
	String str = "", velocity = ""; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.velocity);
				
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<10;i++){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						Log.e("sleep", "PB");
					}
					str = request("http://pubs.diabox.com/dataUpdate.php?dbx_id=16&dataNameList[]=st-mathieu_wind_rt") + "" + i;
					//velocity = str.split(""force":.*")
					
					text.post(new Runnable() {
						
						@Override
						public void run() {
							text.setText(str);
							
						}
					});
						
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}


}
