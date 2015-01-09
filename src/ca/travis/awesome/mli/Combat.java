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

	/*
	 
	 Needs a run loop that triggers the Api call to update things
	 
	 Locks on the location values
	 When we get a return from the api call it assigns values
	 Creates all the values that are needed for the draw representation
	 
	
	 */
	
	
	
	public int getSecondRemaining() {
		
		//TODO - add this implemention
		return 300;
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
