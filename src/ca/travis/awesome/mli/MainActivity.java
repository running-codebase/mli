package ca.travis.awesome.mli;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import ca.travis.awesome.mli.DialogCreateUserFragment.CreateUserInterface;
import ca.travis.awesome.mli.DialogCreateUserOrLoginFragment.CreateUserOrLoginInterface;
import ca.travis.awesome.mli.DialogLoginFragment.LoginInterface;

public class MainActivity extends Activity implements CreateUserOrLoginInterface, CreateUserInterface, LoginInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        DialogCreateUserOrLoginFragment dialog = new DialogCreateUserOrLoginFragment();
		dialog.setCancelable(false);
		dialog.show(getFragmentManager(), "CREATE_USER_OR_LOGIN"); 
		
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
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
		// TODO -- perform the create user 
		return false;
	}
	@Override
	public void createUserSucceded() {
		// TODO - log the user in
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
		// TODO - log the user in
		return false;
	}
	@Override
	public void loginCancelled() {
		  DialogCreateUserOrLoginFragment dialog = new DialogCreateUserOrLoginFragment();
		  dialog.setCancelable(false);
		  dialog.show(getFragmentManager(), "CREATE_USER_OR_LOGIN"); 
	}

}
