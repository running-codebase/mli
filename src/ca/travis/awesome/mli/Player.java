package ca.travis.awesome.mli;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class Player {

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
	private LocationAndOrientation locationAndOrientation;
	
	public Player (Context context) { //TODO - add the dictionary that contains the callback info when creating the user
		//alive = true;
		//inCombat = false;
		//weapon = new Weapon();
		this.context = context;
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
			
			locationAndOrientation = new LocationAndOrientation(context);
			
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

	public LocationAndOrientation getLocationAndOrientation() {
		return locationAndOrientation;
	}

	public int getCombatId() {
		return combatId;
	}

	public void setCombatId(int combatId) {
		this.combatId = combatId;
	}
	
}
