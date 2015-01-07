package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Enemy {
	
	private int enemyId;
	private String enemyName;
	private LocationAndOrientation locationAndOrientation;
	
	public Enemy(JSONObject enemy) {
		Log.d("mli", "Parsing enemy object");

		try {
			enemyId = enemy.getInt("enemy_id");
			enemyName = enemy.getString("enemy_name");
			locationAndOrientation = new LocationAndOrientation(enemy.getJSONObject("location_and_orientation"));
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}
	}

	
	public int getEnemyId() {
		return enemyId;
	}

	public String getEnemyName() {
		return enemyName;
	}
	
	public LocationAndOrientation getLocationAndOrientation() {
		return locationAndOrientation;
	}

	public void setLocationAndOrientation(JSONObject data) {
		this.locationAndOrientation.updateValues(data);
	}
}
