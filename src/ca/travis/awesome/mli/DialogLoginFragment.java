package ca.travis.awesome.mli;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DialogLoginFragment extends DialogFragment {
	
	private EditText userName;
	private EditText password;
	private CheckBox chkbxStayLoggedIn;
	
	public interface LoginInterface {
	    boolean onLogin(String userName, String password, Boolean stayLoggedIn); 
	    void loginCancelled();
	}

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.fragment_login, null);
	    
	    userName = (EditText) view.findViewById(R.id.user_name);
	    password = (EditText) view.findViewById(R.id.password);
	    chkbxStayLoggedIn = (CheckBox) view.findViewById(R.id.chkbx_stay_logged_in);
	    
	    builder.setView(view)
	           .setPositiveButton(R.string.ok, null)
	           .setNegativeButton(R.string.cancel, null)
	           .setCancelable(false);      
	    
	    final AlertDialog d = builder.create();
	    d.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
		    	Button btnPositive = d.getButton(AlertDialog.BUTTON_POSITIVE);
		    	btnPositive.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
		            	   if (userName.equals(null)) {
		            		   Toast.makeText(getActivity(), getResources().getString(R.string.invalid_user_name), Toast.LENGTH_LONG).show();
		            		   return;
		            	   } 
		            	   if (password.equals(null) || password.length()<6) {
		            		   Toast.makeText(getActivity(), getResources().getString(R.string.invalid_password), Toast.LENGTH_LONG).show();
		            		   return;
		            	   }
		            	   
		            	   LoginInterface activity = (LoginInterface) getActivity();
		            	   boolean results = activity.onLogin(userName.getText().toString(), password.getText().toString(), chkbxStayLoggedIn.isChecked());	 
		            	   
		            	   if (results) {
								d.dismiss();
		            	   } else {
		            		   Toast.makeText(getActivity(), getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
		            	   }
					}
				});
		    	
		    	Button btnNegative = d.getButton(AlertDialog.BUTTON_NEGATIVE);
		    	btnNegative.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
		            	   LoginInterface activity = (LoginInterface) getActivity();
		            	   activity.loginCancelled();
		            	   d.dismiss();
					}
				});
			}
		});
	    return d;
	}
}
