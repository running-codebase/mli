package ca.travis.awesome.mli;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Player {

	private boolean initilized = false;
	private	String userName;
	private int userId;
	private int sessionId;
	private int wins;
	private int losses;
	private int cash;
	private boolean alive;
	private boolean inCombat;
	private Weapon weapon;
	private DualLive dual;
	private List<DualRecord> dualRecords = new ArrayList<DualRecord>();
	
	public Player () { //TODO - add the dictionary that contains the callback info when creating the user
		//alive = true;
		//inCombat = false;
		//weapon = new Weapon();
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

			
			if (reader.has("dual")) {
				Log.d("mli", "Parsing dual object");
				JSONObject dualObject = reader.getJSONObject("dual");
				dual = new DualLive(dualObject.getInt("dual_id"), dualObject.getInt("start_time"));
			}
			
			initilized = true;
    	} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();
		}

		
	}

	public boolean canSendAttack() {
		if (alive && dual.canAttack() && weapon.isFireable()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void createDual(int id, int startTime) { //TODO - maybe remove this
		dual = new DualLive(id, startTime);
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

	public DualLive getDual() {
		return dual;
	}

	public List<DualRecord> getDualRecords() {
		return dualRecords;
	}

	
}
