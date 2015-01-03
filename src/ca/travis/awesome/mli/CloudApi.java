package ca.travis.awesome.mli;

public class CloudApi {

	public String createAccount(String userName, String HashedPassword) {
		//Returns
		//1. Success user object
		//2. Fail (error)
		//3. Network Error
		return "";
	}

	public String login(String userName, String hashedPassword) {
		//Returns
		//1. Success user object
		//2. Fail (error)
		//3. Network Error
		return "";
	}
	
	
	public String findDual() { //try to get matched for duel
		
		//Returns 
		//1. Dual available
		//2. Dual not available. You are on a list
		//3. Network Error
		return "";
	}

	public String combatMode(/*coordinates, dual_id*/) {
		//Returns
		//1. Success you are in combat mode
		//2. You are not in combat mode (error)
		//3. Network Error
		return "";
	}
	
	public String currentState(/*coordinates, dual_id*/) {
		//TODO - figure out the implementation for this later. 
		return "";
	}
	
	
	public String attack(/*coordinates, dual_id*/) {
		//Returns
		//1. Success combat over
		//2. Failure (error)
		//3. Network Error 
		return "";
	}
	
}
