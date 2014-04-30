package orion.ms.windbuoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Activity10 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_10);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem Item){
		switch (Item.getItemId()) {
		case R.id.item_realtime:
			MainActivity.realTime = true;
			Intent intentRealTime = new Intent(Activity10.this,MainActivity.class);
			startActivity(intentRealTime);
			break;
		case R.id.item_ten_min:
			MainActivity.realTime = false;
			Intent intent10min = new Intent(Activity10.this,Activity10.class);
			startActivity(intent10min);
			break;
		case R.id.item_sixty_min:
			Intent intent60min = new Intent(Activity10.this,Activity10.class);
			startActivity(intent60min);
			break;
		case R.id.item_map:
			Intent intentMap = new Intent(Activity10.this,Activity10.class);
			startActivity(intentMap);
			break;

		default:
			break;
		}
		
		return false;
	}
	*/
}
