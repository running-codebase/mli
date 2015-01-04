package ca.travis.awesome.mli;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialogCreateUserOrLoginFragment extends DialogFragment implements OnClickListener {

	private Button loginButton;
	private Button createUserButton;
	
	public interface CreateUserOrLoginInterface {
	    void onLoginButton();
	    void onCreateUserButton();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.fragment_create_user_or_login, null);

	    loginButton = (Button) view.findViewById(R.id.btn_login);
	    createUserButton = (Button) view.findViewById(R.id.btn_create_user);
	    loginButton.setOnClickListener(this);
	    createUserButton.setOnClickListener(this);
	    
	    builder.setView(view);      
	    
	    return builder.create();
	}

	@Override
	public void onClick(View v) {

		CreateUserOrLoginInterface activity = (CreateUserOrLoginInterface) getActivity();
		switch(v.getId()) {
		case (R.id.btn_create_user):
			DialogCreateUserOrLoginFragment.this.getDialog().cancel();
			activity.onCreateUserButton();	 
			break;
		case (R.id.btn_login):
			DialogCreateUserOrLoginFragment.this.getDialog().cancel();
			activity.onLoginButton();
			break;
		}
	}
}
