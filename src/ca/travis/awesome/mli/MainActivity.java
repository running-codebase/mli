package ca.travis.awesome.mli;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import ca.travis.awesome.mli.DialogCreateUserFragment.CreateUserInterface;
import ca.travis.awesome.mli.DialogCreateUserOrLoginFragment.CreateUserOrLoginInterface;
import ca.travis.awesome.mli.DialogLoginFragment.LoginInterface;
import ca.travis.awesome.mli.MainFragment.MainFragmentInterface;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends Activity implements CreateUserOrLoginInterface, CreateUserInterface, LoginInterface, MainFragmentInterface {

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	private MainFragment startScreenFragment;
	private Player player; //The user class
	private Combat combat; 
	private User user;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showMainFragment();
        showLoginDialog();
        
        Firebase.setAndroidContext(this);
    }
    
    private void showMainFragment() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        startScreenFragment = new MainFragment();
        fragmentTransaction.add(android.R.id.content, startScreenFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showLoginDialog() {
        DialogCreateUserOrLoginFragment dialog = new DialogCreateUserOrLoginFragment();
		dialog.setCancelable(false);
		dialog.show(getFragmentManager(), "CREATE_USER_OR_LOGIN"); 
    }
    
    private boolean logUserIn(String userName, String password) {

    	user = new User(this, userName, true);
    	
//    	String loginResults = CloudApi.login(userName, password);
//    	if (loginResults != null) {
//    		player = new Player(this);
//    		player.populatePlayerFromJson(loginResults);
//        	populatePlayerInfoUI();
//    	}
    	//TODO - save the boolean so we auto login in the future
    	return true;
    }
    
    private void populatePlayerInfoUI() {
    	if (player.isInitilized()) {
    		startScreenFragment.populatePlayer(player);
    	}
    }
    
    
    private void findDual() {

    	String findDualResults = CloudApi.findDual(player.getUserId(), player.getSessionId(), player.getLocationWrapper(), player.getOrientationWrapper());
    	//TODO - handle a case where there is no dual
    	combat = new Combat(findDualResults, player);
    	combat.start();

    	poplateCombatInfoUI();
    	
    }

    private void poplateCombatInfoUI() {
    	startScreenFragment.populateCombat(combat);
    }
    
    
    public void updateCombat() {
    	CloudApi.combatUpdate(player.getUserId(), player.getSessionId(), player.getLocationWrapper(), player.getOrientationWrapper(), player.getCombatId());
    }
    
    private void combatCompletedSequence() {
    	Toast.makeText(this, "You killed your enemy", Toast.LENGTH_LONG).show();
    	combat.resolveCombat(false);
    }
    
    

    //CreateUserOrLoginInterface
	@Override
	public void onLoginButton() {
		DialogLoginFragment dialog = new DialogLoginFragment();
		dialog.setCancelable(false);
		dialog.show(getFragmentManager(), "LOGIN"); 
	}
	@Override
	public void onCreateUserButton() {
		DialogCreateUserFragment dialog = new DialogCreateUserFragment();
		dialog.setCancelable(false);
		dialog.show(getFragmentManager(), "CREATE_USER"); 
	}

	
    //CreateUserInterface
	@Override
	public boolean onCreateUser(String userName, String password,
			Boolean stayLoggedIn) {
		
		String createUserResults = CloudApi.createAccount(userName, password);

		try {
			JSONObject reader = new JSONObject(createUserResults);
			if (reader.getBoolean("created_user")) {
				return logUserIn(userName, password);
			}
			
		} catch (JSONException e) {
    		Log.e("mli", "jsonexception: " + e.toString());
			e.printStackTrace();

		}
    	//TODO - save the boolean so we auto login in the future
		return false;
	}
	@Override
	public void createUserWasCancelled() {
		  DialogCreateUserOrLoginFragment dialog = new DialogCreateUserOrLoginFragment();
		  dialog.setCancelable(false);
		  dialog.show(getFragmentManager(), "CREATE_USER_OR_LOGIN"); 
	}


	//LoginInterface
	@Override
	public boolean onLogin(String userName, String password,
			Boolean stayLoggedIn) {
		
		
		return logUserIn(userName, password);
	}
	@Override
	public void loginCancelled() {
		  DialogCreateUserOrLoginFragment dialog = new DialogCreateUserOrLoginFragment();
		  dialog.setCancelable(false);
		  dialog.show(getFragmentManager(), "CREATE_USER_OR_LOGIN"); 
	}

	//MainFragmentInterface
	@Override
	public void findDualBtnPressed() {
		findDual();
	}
	
	@Override
	public void attack() {
		if (combat.inRange) {
    		Log.e("mli", "attacking ");

			String attackResults = CloudApi.attack(player, combat);
			
			try {
				JSONObject reader = new JSONObject(attackResults);
	    		Log.e("mli", "reading");
				if (reader.getBoolean("attack_success")) {
		    		Log.e("mli", "success ");
					combatCompletedSequence();
				} else {
					//TODO - handle the combat not over thing
				}
				
			} catch (JSONException e) {
	    		Log.e("mli", "jsonexception: " + e.toString());
				e.printStackTrace();

			}
		}
	}

}
