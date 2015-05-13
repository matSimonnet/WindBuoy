package orion.ms.windbuoy;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//to access context in the  thread loop
	public Context context = this;
	
	//to stop http requests when leaving MainActivity
	static boolean realTime = true;
	
	//to set reference wind
	static boolean refWind = true;
	int offset = 0;
	
	//to populate page
	private TextView directionTextView;
	private TextView relativeWindTextView;
	private TextView referenceWindTextView;
	
	//to dynamically 
	RelativeLayout imageLayout;
	RelativeLayout.LayoutParams imageLayoutParameter;
	
	//to use JSON for wind values gathering
	private Wind wind;
	private int referenceWind = 999;
	private int relativeWind = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//to populate page
		imageLayout = (RelativeLayout) findViewById(R.id.relativelayout);
		imageLayoutParameter = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageLayoutParameter.addRule(RelativeLayout.CENTER_HORIZONTAL);
		imageLayoutParameter.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		directionTextView = (TextView) findViewById(R.id.direction);
		directionTextView.setBackgroundColor(Color.YELLOW);
		relativeWindTextView = (TextView) findViewById(R.id.relative);
		relativeWindTextView.setBackgroundColor(Color.parseColor("#05A811"));
		referenceWindTextView = (TextView) findViewById(R.id.reference);
		referenceWindTextView.setBackgroundColor(Color.LTGRAY);
		
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
					

						if (wind.direction>0){
						directionTextView.post(new Runnable() {
								@Override
								public void run() {
									directionTextView.setText(""+ wind.direction);
									ImageView imageArrow1 = new ImageView(context);									
									
									if ( refWind || referenceWind == 999 ){
											offset = wind.direction;
											referenceWind = wind.direction;
											imageArrow1.setImageResource(R.drawable.thin_arrow);
											imageLayout.addView(imageArrow1, imageLayoutParameter);
											imageArrow1.setRotation(wind.direction - offset);
											if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
												imageArrow1.setScaleX(0.9f);
												imageArrow1.setScaleY(0.9f);
												}
											refWind= false;
									}
									else {
									
									relativeWind = wind.direction - offset;
									
									if ( Math.abs(relativeWind) < 7 ){
										imageArrow1.setImageResource(R.drawable.thin_arrow_green);
										relativeWindTextView.setBackgroundColor(Color.parseColor("#05A811"));	
									}
									if ( Math.abs(relativeWind) >= 7 && Math.abs(relativeWind) < 14){
										imageArrow1.setImageResource(R.drawable.thin_arrow_orange);
										relativeWindTextView.setBackgroundColor(Color.parseColor("#FFC001"));	
									}
									if ( Math.abs(relativeWind) >= 15){
										imageArrow1.setImageResource(R.drawable.thin_arrow_red);
										relativeWindTextView.setBackgroundColor(Color.parseColor("#FFC001"));
									}
									imageLayout.addView(imageArrow1, imageLayoutParameter);
									imageArrow1.setRotation(relativeWind);
									imageArrow1.setAlpha(0.05f);
									if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
										imageArrow1.setScaleX(0.8f);
										imageArrow1.setScaleY(0.8f);
										}
									else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
										imageArrow1.setScaleX(0.9f);
										imageArrow1.setScaleY(0.9f);
									}
									}
									
									
									//table for transparency using timestamp

								}
							});
						}	
						
						//if (referenceWind!=999){
							referenceWindTextView.post(new Runnable() {
									@Override
									public void run() {
										referenceWindTextView.setText("" + referenceWind);
									}
								});
							//}
							
							relativeWindTextView.post(new Runnable() {
								@Override
								public void run() {
									
									relativeWindTextView.setText("" + relativeWind);
									
									if ( relativeWind > 0){
										relativeWindTextView.setText("+" + "" + relativeWind);
									}
									if (  Math.abs(relativeWind) > 15){
										relativeWindTextView.setBackgroundColor(Color.RED);
									}
								}
							});
						
						try {
							Thread.sleep(1000);
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
		case R.id.item_refwind:
			realTime = true;
			refWind = true;
			Intent intentRealTime = new Intent(MainActivity.this,MainActivity.class);
			startActivity(intentRealTime);
			
			
			break;
		default:
			break;
		}
		return false;
	}
	
	
	/*
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
	*/
	
	@Override
	protected void onResume() {
		realTime = true;
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		realTime = false;
		System.exit(0);
	}	
}
