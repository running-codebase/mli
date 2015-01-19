package ca.travis.awesome.mli;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationWrapper implements SensorEventListener {

	private float[] mGravity;
	private float[] mGeomagnetic;
	private float RR[] = new float[9];
	private float I[] = new float[9];
	
	private float orientation[] = new float[3];
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mMageneticField;

	
	
	public OrientationWrapper(Context context) {
		
		//Orientation Stuff
		SensorManager mSensorManager = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);
		Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor mMageneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this,mMageneticField,SensorManager.SENSOR_DELAY_NORMAL);

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
