package ca.travis.awesome.mli;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener {

	public interface MainFragmentInterface {
		public void findDualBtnPressed();
		
	}

	 private MainFragmentInterface callback;
	// private Context context;

	private static TextView txtUser;
	private static TextView txtStatus;
	private static TextView txtWeapon;
	private static TextView txtCash;
	private static TextView txtWinLoss;
	private static TextView txtEnemyName;
	private static TextView txtTimeRemaining;
	private static Button btnFindCombat;

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
        
        btnFindCombat.setOnClickListener(this);
        
        //TODO - add click listener that gets that makes api call and populates data

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			callback = (MainFragmentInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnItemSelected");
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case (R.id.btn_find_combat):
			callback.findDualBtnPressed();
			break;
		}
	}
	
	public void populatePlayer(Player player) {
		txtUser.setText(player.getUserName());    		
		if (player.isAlive()){
			txtStatus.setText("Alive");
		} else {
			txtStatus.setText("Dead");
		}
		txtWeapon.setText("Wand");
		txtCash.setText("" + player.getCash());
		txtWinLoss.setText("" + player.getWins() + "/" + player.getLosses());
	}
	
	public void populateCombat(Combat combat) {
		
		
		
	}
	
	
}
