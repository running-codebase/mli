package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Combat extends Thread {

	private Thread t;
	private String threadName = "combat";
	
	boolean runloop = true;
	
	
	private int combatId;
	private int combatStartTime;
	private int duration; //seconds
	private Enemy enemy;
	private Player player;
	
	public Combat(String data, Player player) {
		Log.d("mli", "Parsing combat object");
		this.player = player;
		
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

	public void run () {

		try {
			int count = 1;
			while (true) {
				while(runloop) {
					count ++;

					
					
					Log.e("mli", "Bearing: " + player.getLocationAndOrientation().getLocation().getBearing());
	
		    		//Calculate the new angles and data for the drawing
		    		//Maybe update the cloud with an api call about current state
		    		
		    		Thread.sleep(1000);
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
    		Log.e("mli", "Threading problem: " + e.toString());
			e.printStackTrace();
		}
	}
	
	 public void start ()
	   {
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	
	
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
