package orion.ms.windbuoy;

import org.json.JSONException;


//Not use
public class DataFisher extends Thread implements Runnable{

	Wind wind = null;
	
	public DataFisher(Wind wind) {
		this.wind = wind;
	}
	
	@Override
	public void run() {

		//for(int i=0;i<10;i++){
		while (MainActivity.realTime)
		{
			String data = Utils.request("http://pubs.diabox.com/dataUpdate.php?dbx_id=16&dataNameList[]=st-mathieu_wind_rt");
			try {
				wind.getWind(data, wind);
			} catch (JSONException e1) {}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}			
		} 
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}
}
