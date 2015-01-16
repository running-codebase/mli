package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationAndOrientation implements SensorEventListener {

	
	public interface LocationUpdate {
		public void locationUpdated();
	}
	
	private Location location;
	
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float RR[] = new float[9];
	private float I[] = new float[9];
	
	private float orientation[] = new float[3];
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mMageneticField;
	
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

		
		//Orientation Stuff
		SensorManager mSensorManager = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);
		Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor mMageneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this,mMageneticField,SensorManager.SENSOR_DELAY_NORMAL);
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
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			mGravity = event.values;
		}
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
		      mGeomagnetic = event.values;
		}
		if (mGravity != null && mGeomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
			if (success) {
		        SensorManager.getOrientation(R, orientation);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {	}

	public float[] getOrientation() {
		return orientation;
	}
}
