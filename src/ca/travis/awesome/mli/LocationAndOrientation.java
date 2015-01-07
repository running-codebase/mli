package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LocationAndOrientation {

	private double longitude;
	private double latitude;
	private double bearing;
	private int timeStamp;
	
	public LocationAndOrientation() {
		//TODO initialize
		updateValues();
	}

	public LocationAndOrientation(JSONObject data) {
		Log.d("mli", "Parsing location object");

		try {
			longitude = data.getDouble("long");
			latitude = data.getDouble("lat");
			bearing = data.getDouble("bearing");
			timeStamp =  data.getInt("time_stamp");
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateValues(){
		
	}
	
	public void updateValues(JSONObject data){
		Log.d("mli", "Parsing and updating location object");

		try {
			longitude = data.getDouble("long");
			latitude = data.getDouble("lat");
			bearing = data.getDouble("bearing");
			timeStamp = data.getInt("time_stamp");
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getBearing() {
		return bearing;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

}
