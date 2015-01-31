package ca.travis.awesome.mli;

import android.util.Log;

import com.firebase.client.Firebase;

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
		
		Firebase ref = new Firebase("https://mli-tester.firebaseio.com/");
		
		Firebase usersRef = ref.child("users");
		
		usersRef.child(userName).child("long").setValue(-79.396914);
		usersRef.child(userName).child("lat").setValue(43.658054);
		usersRef.child(userName).child("timestamp").setValue(100000);
		
		
		
		
		//-------------------------------
		
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
		    	"\"in_combat\": false , " +
		    	"\"combat_id\" : 0 }}";
		//Returns
		//1. Success user object
		//2. Fail (error)
		//3. Network Error
		return results;
	}
	
	
	public static String findDual(int userId, int sessionId, LocationWrapper locationWrapper, OrientationWrapper orientation) { //try to get matched for duel
		
		String results = "{ \"dual\" : { \"dual_id\" : 111111, \"start_time\" : 111111, \"duration\" : 1111111 }, " +
				" \"enemy\" : { \"enemy_id\" : 1111 , \"enemy_name\" : \"Grendar\", \"location_and_orientation\" : { \"long\" : -79.396914, \"lat\" :43.658054 , \"bearing\" : 90.0 , \"time_stamp\" : 1111 }}}";
		
		return results;
		//Returns 
		//1. Dual available
		//2. Dual not available. You are on a list
		//3. Network Error
	}

	public static String combatUpdate(int userId, int sessionId, LocationWrapper locationWrapper, OrientationWrapper orientation, int combatId) {
		//Returns
		//1. Success you are in combat mode
		//2. You are not in combat mode (error)
		//3. Network Error
		return "";
	}
	
//	public static String currentState(/*coordinates, dual_id*/) {
//		//TODO - figure out the implementation for this later. 
//		return "";
//	}
	
	
	public static String attack(Player player, Combat combat ) {
		
		String results = "{ \"attack_success\" : true , \"message\" : \"\" }";
		//Returns
		//1. Success combat over
		//2. Failure (error)
		//3. Network Error 
		return results;
	}
	
}
