package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Combat extends Thread {

	private Thread t;
	private String threadName = "combat";
	
	boolean inCombat = false;
	boolean runloop = true;
	boolean inRange = false;
	
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
			inCombat = true;
			
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

					
					
	
		    		//Calculate the new angles and data for the drawing
		    		//Maybe update the cloud with an api call about current state

					
					if (Math.abs(adjustedBearingToEnemy()) <= 10 ) {
						Log.e("mli", "Bearing to enemy: " + Math.abs(adjustedBearingToEnemy()));
						inRange = true;
					} else {
						inRange = false;
					}
					
					
		    		Thread.sleep(330);
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
	
	 
	 public double distanceToEnemy() {
		 return player.getLocationWrapper().getLocation().distanceTo(enemy.getLocationWrapper().getLocation());
	 }
	 
	 
	 public double bearingToEnemy() { //angle position from north to enemy
		 return player.getLocationWrapper().getLocation().bearingTo(enemy.getLocationWrapper().getLocation());
	 }
	 
	 public double adjustedBearingToEnemy() {
		 return player.getLocationWrapper().getLocation().bearingTo(enemy.getLocationWrapper().getLocation()) - player.getOrientationWrapper().getOrientation()[0]*360/(2*3.14159f);
	 }

	 public void resolveCombat(boolean inCombat) {
		 this.inCombat = inCombat;
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
