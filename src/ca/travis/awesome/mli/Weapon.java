package ca.travis.awesome.mli;

public class Weapon {
	
	private boolean fireable;

	public Weapon(int id) {
		fireable = true;
	}
	
	public boolean isFireable () {
		return fireable;
	}
}
