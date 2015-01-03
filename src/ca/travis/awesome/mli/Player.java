package ca.travis.awesome.mli;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private	String userName;
	private String userId;
	private int wins;
	private int losses;
	private boolean alive;
	private boolean inCombat;
	private Weapon weapon;
	private DualLive dual;
	private List<DualRecord> dualRecords = new ArrayList<DualRecord>();
	
	public Player () { //TODO - add the dictionary that contains the callback info when creating the user
		alive = true;
		inCombat = false;
		weapon = new Weapon();
	}

	public boolean canSendAttack() {
		if (alive && dual.canAttack() && weapon.isFireable()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void createDual(int id, int startTime) {
		dual = new DualLive(id, startTime);
	}
	
	public void resolveDual() { //TODO - Implementation - this happens when the user is killed
		
	}
	
}
