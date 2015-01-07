package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Combat {

	private int combatId;
	private int combatStartTime;
	private int duration; //seconds
	private Enemy enemy;
	
	public Combat(String data) {
		Log.d("mli", "Parsing combat object");

		try {
			JSONObject reader = new JSONObject(data);
			JSONObject dual = reader.getJSONObject("dual");
			combatId = dual.getInt("dual_id");
			combatStartTime = dual.getInt("start_time");
			duration = dual.getInt("duration");
			
			JSONObject enemyJson = reader.getJSONObject("enemy");
			enemy = new Enemy(enemyJson);
			
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();

		}
	}

	public int getCombatId() {
		return combatId;
	}

	public int getCombatStartTime() {
		return combatStartTime;
	}

	public int getDuration() {
		return duration;
	}

	public Enemy getEnemy() {
		return enemy;
	}
}
