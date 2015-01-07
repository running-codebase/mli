package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ca.travis.awesome.mli.DialogCreateUserFragment.CreateUserInterface;
import ca.travis.awesome.mli.DialogCreateUserOrLoginFragment.CreateUserOrLoginInterface;
import ca.travis.awesome.mli.DialogLoginFragment.LoginInterface;
import ca.travis.awesome.mli.MainFragment.MainFragmentInterface;

public class MainActivity extends Activity implements CreateUserOrLoginInterface, CreateUserInterface, LoginInterface, MainFragmentInterface {

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	private MainFragment startScreenFragment;
	private Player player; //The user class
	private Combat combat; 
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showMainFragment();
        showLoginDialog();
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
    	
    	String loginResults = CloudApi.login(userName, password);
    	if (loginResults != null) {
    		player = new Player();
    		player.populatePlayerFromJson(loginResults);
        	populatePlayerInfoUI();
    	}
    	//TODO - save the boolean so we auto login in the future
    	return true;
    }
    
    private void populatePlayerInfoUI() {
    	if (player.isInitilized()) {
    		startScreenFragment.populatePlayer(player);
    	}
    }
    
    
    private void findDual() {

    	String findDualResults = CloudApi.findDual(player.getUserId(), player.getSessionId(), player.getLocationAndOrientation());
    	//TODO - handle a case where there is no dual
    	combat = new Combat(findDualResults);
    	poplateCombatInfoUI();
    	
    }

    private void poplateCombatInfoUI() {
    	startScreenFragment.populateCombat(combat);
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

}
