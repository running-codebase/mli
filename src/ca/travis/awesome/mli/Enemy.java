package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Enemy {
	
	private int enemyId;
	private String enemyName;
	private LocationWrapper location;
	
	public Enemy(JSONObject enemy) {
		Log.d("mli", "Parsing enemy object");

		try {
			enemyId = enemy.getInt("enemy_id");
			enemyName = enemy.getString("enemy_name");
			location = new LocationWrapper(enemy.getJSONObject("location_and_orientation"));
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
	
	public LocationWrapper getLocationWrapper() {
		return location;
	}

	public void setLocation(JSONObject data) {
		this.location.updateValues(data);
	}
}
