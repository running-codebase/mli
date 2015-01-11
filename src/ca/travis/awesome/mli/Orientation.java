package ca.travis.awesome.mli;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class Orientation {

	float[] mGravity;
	float[] mGeomagnetic;
	float RR[] = new float[9];
	float I[] = new float[9];
	float orientation[] = new float[3];

	SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	Sensor mMageneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

	mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
	mSensorManager.registerListener(this,mMageneticField,SensorManager.SENSOR_DELAY_NORMAL);
	

	
	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	{   
	mGravity = event.values.clone();    
	}
	if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
	{
	    mGeomagnetic = event.values.clone();
	}

	SensorManager.getRotationMatrix(RR, I, mGravity, mGeomagnetic);
	SensorManager.getOrientation(RR, orientation);
	azimuth = orientation[0];
	pitch = orientation[1];
	roll = orientation[2];
	System.out.println("Pitch " + pitch);
	


}
