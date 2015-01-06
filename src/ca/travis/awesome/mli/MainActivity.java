package ca.travis.awesome.mli;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ca.travis.awesome.mli.DialogCreateUserFragment.CreateUserInterface;
import ca.travis.awesome.mli.DialogCreateUserOrLoginFragment.CreateUserOrLoginInterface;
import ca.travis.awesome.mli.DialogLoginFragment.LoginInterface;

public class MainActivity extends Activity implements CreateUserOrLoginInterface, CreateUserInterface, LoginInterface {

	private Player player; //The user class
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            
        }

        showLoginDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    	public static TextView txtUser;
    	public static TextView txtStatus;
    	public static TextView txtWeapon;
    	public static TextView txtCash;
    	public static TextView txtWinLoss;
    	public static TextView txtEnemyName;
    	public static TextView txtTimeRemaining;
    	public static Button btnFindCombat;
    	
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            txtUser = (TextView) rootView.findViewById(R.id.txtView_user);
            txtStatus = (TextView) rootView.findViewById(R.id.txtView_status);
            txtWeapon = (TextView) rootView.findViewById(R.id.txtView_weapon);
            txtCash = (TextView) rootView.findViewById(R.id.txtView_cash);
            txtWinLoss = (TextView) rootView.findViewById(R.id.txtView_winloss);
            txtEnemyName = (TextView) rootView.findViewById(R.id.txtView_enemy_name);
            txtTimeRemaining = (TextView) rootView.findViewById(R.id.txtView_remaining_time);
            btnFindCombat = (Button) rootView.findViewById(R.id.btn_find_combat);
            
            
            //TODO - add click listener that gets that makes api call and populates data
            
            
            return rootView;
        }
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
    		PlaceholderFragment.txtUser.setText(player.getUserName());    		
    		if (player.isAlive()){
    			PlaceholderFragment.txtStatus.setText("Alive");
    		} else {
    			PlaceholderFragment.txtStatus.setText("Dead");
    		}
    		PlaceholderFragment.txtWeapon.setText("Wand");
    		PlaceholderFragment.txtCash.setText("" + player.getCash());
    		PlaceholderFragment.txtWinLoss.setText("" + player.getWins() + "/" + player.getLosses());
    	}
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

}
