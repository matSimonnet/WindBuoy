package orion.ms.windbuoy;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	static boolean realTime = true;
	private int run = 1;
	private TextView directionTextView;
	private TextView velocityTextView;
	private Wind wind;
	private ImageView imageArrow1;
	private ImageView imageArrow2;
	private ImageView imageArrow3;
	private ImageView imageArrow4;
	private ImageView imageArrow5;
	private ImageView imageArrow6;
	private ImageView imageArrow7;
	private ImageView imageArrow8;
	private ImageView imageArrow9;
	private ImageView imageArrow10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		imageArrow1 = (ImageView) findViewById(R.id.image1);
		imageArrow2 = (ImageView) findViewById(R.id.image2);
		imageArrow3 = (ImageView) findViewById(R.id.image3);
		imageArrow4 = (ImageView) findViewById(R.id.image4);
		imageArrow5 = (ImageView) findViewById(R.id.image5);
		imageArrow6 = (ImageView) findViewById(R.id.image6);
		imageArrow7 = (ImageView) findViewById(R.id.image7);
		imageArrow8 = (ImageView) findViewById(R.id.image8);
		imageArrow9 = (ImageView) findViewById(R.id.image9);
		imageArrow10 = (ImageView) findViewById(R.id.image10);
		
		directionTextView = (TextView) findViewById(R.id.direction);
		directionTextView.setBackgroundColor(Color.YELLOW);
		velocityTextView = (TextView) findViewById(R.id.velocity);
		
		wind = new Wind();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
									
				while (realTime){	
					//String data = Utils.request("http://windlive.biz/buoy/now.php?id=1");
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
								if (run == 1){
								imageArrow1.setImageResource(R.drawable.thin_arrow);
								imageArrow1.setRotation(wind.direction);
								run++;
								}
								else if (run == 2){
								imageArrow2.setImageResource(R.drawable.thin_arrow);
								imageArrow2.setRotation(wind.direction);
								imageArrow1.setAlpha(0.80f);
								run++;
								}
								else if (run == 3){
								imageArrow3.setImageResource(R.drawable.thin_arrow);
								imageArrow3.setRotation(wind.direction);
								imageArrow2.setAlpha(0.80f);
								imageArrow1.setAlpha(0.60f);
								run++;
								}
								else if (run == 4){
								imageArrow4.setImageResource(R.drawable.thin_arrow);
								imageArrow4.setRotation(wind.direction);
								imageArrow3.setAlpha(0.80f);
								imageArrow2.setAlpha(0.60f);
								imageArrow1.setAlpha(0.40f);
								run++;
								}
								else if (run == 5){
								imageArrow5.setImageResource(R.drawable.thin_arrow);
								imageArrow5.setRotation(wind.direction);
								imageArrow4.setAlpha(0.80f);
								imageArrow3.setAlpha(0.60f);
								imageArrow2.setAlpha(0.40f);
								imageArrow1.setAlpha(0.20f);
								run++;
								}
								else if (run == 6){
								imageArrow6.setImageResource(R.drawable.thin_arrow);
								imageArrow6.setRotation(wind.direction);
								imageArrow5.setAlpha(0.80f);
								imageArrow4.setAlpha(0.60f);
								imageArrow3.setAlpha(0.40f);
								imageArrow2.setAlpha(0.20f);
								imageArrow1.setAlpha(0.15f);
								run++;
								}
								else if (run == 7){
								imageArrow7.setImageResource(R.drawable.thin_arrow);
								imageArrow7.setRotation(wind.direction);
								imageArrow6.setAlpha(0.80f);
								imageArrow5.setAlpha(0.60f);
								imageArrow4.setAlpha(0.40f);
								imageArrow3.setAlpha(0.20f);
								imageArrow2.setAlpha(0.15f);
								imageArrow1.setAlpha(0.10f);
								run++;
								}
								else if (run == 8){
								imageArrow8.setImageResource(R.drawable.thin_arrow);
								imageArrow8.setRotation(wind.direction);
								imageArrow7.setAlpha(0.80f);
								imageArrow6.setAlpha(0.60f);
								imageArrow5.setAlpha(0.40f);
								imageArrow4.setAlpha(0.20f);
								imageArrow3.setAlpha(0.15f);
								imageArrow2.setAlpha(0.10f);
								imageArrow1.setAlpha(0.05f);
								run++;
								}
								else if (run == 9){
								imageArrow9.setImageResource(R.drawable.thin_arrow);
								imageArrow9.setRotation(wind.direction);
								imageArrow8.setAlpha(0.80f);
								imageArrow7.setAlpha(0.60f);
								imageArrow6.setAlpha(0.40f);
								imageArrow5.setAlpha(0.20f);
								imageArrow4.setAlpha(0.15f);
								imageArrow3.setAlpha(0.10f);
								imageArrow2.setAlpha(0.05f);
								imageArrow1.setAlpha(0.03f);
								run++;
								}
								else if (run == 10){
								imageArrow10.setImageResource(R.drawable.thin_arrow);
								imageArrow10.setRotation(wind.direction);
								imageArrow9.setAlpha(0.80f);
								imageArrow8.setAlpha(0.60f);
								imageArrow7.setAlpha(0.40f);
								imageArrow6.setAlpha(0.20f);
								imageArrow5.setAlpha(0.15f);
								imageArrow4.setAlpha(0.10f);
								imageArrow3.setAlpha(0.05f);
								imageArrow2.setAlpha(0.03f);
								imageArrow1.setAlpha(0.015f);
								run++;
								}
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
			realTime = true;
			Intent intentRealTime = new Intent(MainActivity.this,MainActivity.class);
			startActivity(intentRealTime);
			break;
		case R.id.item_ten_min:
			realTime = false;
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
	
	@Override
	protected void onResume() {
		realTime = true;
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		realTime = false;
	}	
}
