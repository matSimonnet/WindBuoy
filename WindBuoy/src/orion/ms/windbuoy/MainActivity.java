package orion.ms.windbuoy;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
		
	private TextView directionTextView;
	private TextView velocityTextView;
	
	Wind wind = new Wind();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		directionTextView = (TextView) findViewById(R.id.direction);
		directionTextView.setBackgroundColor(Color.YELLOW);
		velocityTextView = (TextView) findViewById(R.id.velocity);
		
		new Thread(new Runnable() {		
			
			@Override
			public void run() {
				
				for(int i=0;i<5;i++){
					String data = Utils.request("http://pubs.diabox.com/dataUpdate.php?dbx_id=16&dataNameList[]=st-mathieu_wind_rt");
					try {
						wind.getWind(data, wind);
					} catch (JSONException e1) {}
					
					if (wind.velocity>0){
					velocityTextView.post(new Runnable() {
							@Override
							public void run() {
								if ( wind.velocity > 20) {
									velocityTextView.setBackgroundColor(Color.RED);
									velocityTextView.setScaleX(1.2f);
								}
								if ( wind.velocity < 20){
									velocityTextView.setBackgroundColor(Color.BLUE);
								}
								velocityTextView.setText("" + wind.velocity);
								velocityTextView.setScaleX(1.0f);
							}
						});
					}
					
					if (wind.direction>0){
					directionTextView.post(new Runnable() {
							@Override
							public void run() {
								directionTextView.setText(""+ wind.direction);
							}
						});
					}
					
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
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem Item){
		switch (Item.getItemId()) {
		case R.id.item_realtime:
			Intent intentRealTime = new Intent(MainActivity.this,MainActivity.class);
			startActivity(intentRealTime);
			break;
		case R.id.item_ten_min:
			Intent intent10min = new Intent(MainActivity.this,Activity10.class);
			startActivity(intent10min);
			break;
		case R.id.item_sixty_min:
			Intent intent60min = new Intent(MainActivity.this,Activity10.class);
			startActivity(intent60min);
			break;
		case R.id.item_map:
			Intent intentMap = new Intent(MainActivity.this,Activity10.class);
			startActivity(intentMap);
			break;

		default:
			break;
		}
		
		return false;
	}
}
