package orion.ms.windbuoy;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Context mcontext = this;
	static boolean realTime = true;
	private int run = 1;
	private TextView directionTextView;
	private TextView velocityTextView;
	private Wind wind;
	private ImageView imageArrow;
	RelativeLayout ArrowLayout;
	RelativeLayout.LayoutParams lp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		
		ArrowLayout = (RelativeLayout) findViewById(R.id.relativelayout);
		lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		imageArrow = (ImageView) findViewById(R.id.image);
		directionTextView = (TextView) findViewById(R.id.direction);
		directionTextView.setBackgroundColor(Color.YELLOW);
		velocityTextView = (TextView) findViewById(R.id.velocity);
		
		wind = new Wind();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
									
				while (realTime){	
					//String data = Utils.request("http://windlive.biz/buoy/now.php?id=1");
					//String data = Utils.request("http://pubs.diabox.com/dataUpdate.php?dbx_id=16&dataNameList[]=st-mathieu_wind_rt");
					String data = Utils.request("http://pubs.diabox.com/dataUpdate.php?dbx_id=10&dataNameList[]=Renard_wind_rt");
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
				
				//for (int i = 0; i<10 ;i++){	
						if (wind.direction>0){
						directionTextView.post(new Runnable() {
								@Override
								public void run() {
									directionTextView.setText(""+ wind.direction);
									
									
									imageArrow.setImageResource(R.drawable.thin_arrow);
									imageArrow.setRotation(wind.direction);
									
									ImageView imageArrow1 = new ImageView(mcontext);
									imageArrow1.setImageResource(R.drawable.thin_arrow);
									
									ArrowLayout.addView(imageArrow1, lp);
									imageArrow1.setRotation(wind.direction);
									imageArrow1.setScaleX(wind.velocity/2);
									imageArrow1.setScaleY(wind.velocity/2);
									imageArrow1.setAlpha(0.40f);
									
									
									
									//table for transparency using timestamp

								}
							});
						}	
						
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {}
					//}
				}//end of infinite loop
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
