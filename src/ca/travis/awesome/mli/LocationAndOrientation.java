package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationAndOrientation {

	private Location location;
	
	public LocationAndOrientation(Context context) {

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		     // makeUseOfNewLocation(location);
	    		Log.d("mli", "newLocation received: ");
	    		updateValues(location);
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

	}

	public LocationAndOrientation(JSONObject data) {
		Log.d("mli", "Parsing location object");

		try {
			double longitude = data.getDouble("long");
			double latitude = data.getDouble("lat");
			float bearing = (float) data.getDouble("bearing");
			int timeStamp =  data.getInt("time_stamp");
			location = new Location("");
			location.setLongitude(longitude);
			location.setLatitude(latitude);
			location.setBearing(bearing);
			location.setTime(timeStamp);
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateValues(Location location){
		this.location = location;
	}
	
	public void updateValues(JSONObject data){
		Log.d("mli", "Parsing and updating location object");

		try {
			double longitude = data.getDouble("long");
			double latitude = data.getDouble("lat");
			float bearing = (float) data.getDouble("bearing");
			int timeStamp =  data.getInt("time_stamp");
			location.setLongitude(longitude);
			location.setLatitude(latitude);
			location.setBearing(bearing);
			location.setTime(timeStamp);
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public Location getLocation() {
		return location;
	}
}
