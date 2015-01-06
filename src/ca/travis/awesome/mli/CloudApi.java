package ca.travis.awesome.mli;

import android.util.Log;

public class CloudApi {

	public static String createAccount(String userName, String HashedPassword) {
		Log.d("mli", "Cloud Call: Create User");
		String results = "{ \"created_user\" : true , \"message\" : \"\" } ";

		
		
		//Returns
		//1. Success user object
		//2. Fail (error)
		//3. Network Error
		return results;
	}

	public static String login(String userName, String hashedPassword) {
		Log.d("mli", "Cloud Call: Login");
		
		String results = "{ \"user\": {" +
		        "\"user_name\" : \"name\"," +
		    	"\"user_id\" : 11111," +
		    	"\"session_id\" : 22222," +
		    	"\"user_alive\" : true," + 
		    	"\"wins\": 0," +
		    	"\"losses\": 0, " +
		    	" \"weapon\": 1, " +
		    	"\"cash\": 0, " +
		    	"\"in_combat\": false} , " +
		    	"\"dual\" : { \"dual_id\" : 123 , " +
		    	" \"start_time\" : 1420519775157 }}";
		//Returns
		//1. Success user object
		//2. Fail (error)
		//3. Network Error
		return results;
	}
	
	
	public static String findDual() { //try to get matched for duel
		
		//Returns 
		//1. Dual available
		//2. Dual not available. You are on a list
		//3. Network Error
		return "";
	}

	public static String combatMode(/*coordinates, dual_id*/) {
		//Returns
		//1. Success you are in combat mode
		//2. You are not in combat mode (error)
		//3. Network Error
		return "";
	}
	
	public static String currentState(/*coordinates, dual_id*/) {
		//TODO - figure out the implementation for this later. 
		return "";
	}
	
	
	public static String attack(/*coordinates, dual_id*/) {
		//Returns
		//1. Success combat over
		//2. Failure (error)
		//3. Network Error 
		return "";
	}
	
}
