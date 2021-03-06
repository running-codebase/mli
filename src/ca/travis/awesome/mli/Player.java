package ca.travis.awesome.mli;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ca.travis.awesome.mli.LocationWrapper.LocationUpdate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class Player implements LocationUpdate {

	private MainActivity activity;
	private Context context;
	private boolean initilized = false;
	private	String userName;
	private int userId;
	private int sessionId;
	private int wins;
	private int losses;
	private int cash;
	private boolean alive;
	private boolean inCombat;
	private int combatId;
	private Weapon weapon;
	private List<DualRecord> dualRecords = new ArrayList<DualRecord>();
	private LocationWrapper locationWrapper;
	private OrientationWrapper orientation;

	
	
	public Player (MainActivity activity) { //TODO - add the dictionary that contains the callback info when creating the user
		//alive = true;
		//inCombat = false;
		//weapon = new Weapon();
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void populatePlayerFromJson(String data) {
		Log.d("mli", "Parsing player object");
    	try {
			JSONObject reader = new JSONObject(data);
			JSONObject user = reader.getJSONObject("user");
			
			userName = user.getString("user_name");
			userId = user.getInt("user_id");
			sessionId = user.getInt("session_id");
			alive = user.getBoolean("user_alive");
			wins = user.getInt("wins");
			losses = user.getInt("losses");
			cash = user.getInt("cash");
			weapon = new Weapon (user.getInt("weapon"));
			inCombat = user.getBoolean("in_combat");
			setCombatId(user.getInt("combat_id"));
			
			locationWrapper = new LocationWrapper(context);
			orientation = new OrientationWrapper(context);
			
			initilized = true;
    	} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}

		
	}

	public boolean canSendAttack() {
		if (alive && weapon.isFireable() && inCombat) {
			return true;
		} else {
			return false;
		}
	}
	

	public void resolveDual() { //TODO - Implementation - this happens when the user is killed
		
	}
	
	public boolean isInitilized() {
		return initilized;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserId() {
		return userId;
	}

	public int getSessionId() {
		return sessionId;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getCash() {
		return cash;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isInCombat() {
		return inCombat;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public List<DualRecord> getDualRecords() {
		return dualRecords;
	}

	public LocationWrapper getLocationWrapper() {
		return locationWrapper;
	}
	
	public OrientationWrapper getOrientationWrapper() {
		return orientation;
	}

	public int getCombatId() {
		return combatId;
	}

	public void setCombatId(int combatId) {
		this.combatId = combatId;
	}

	@Override
	public void locationUpdated() {
		if (inCombat) {
			activity.updateCombat(); 
		}
	}
	
}
