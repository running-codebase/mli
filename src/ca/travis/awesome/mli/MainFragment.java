package ca.travis.awesome.mli;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener {

	public interface MainFragmentInterface {
		public void findDualBtnPressed();
		public void attack();
	}

	private MainFragmentInterface callback;
	private Activity activity;
	
	private RelativeLayout compassLayout;
	private TextView txtUser;
	private TextView txtStatus;
	private TextView txtWeapon;
	private TextView txtCash;
	private TextView txtWinLoss;
	private TextView txtEnemyName;
	private TextView txtTimeRemaining;
	private Button btnFindCombat;
	private Button btnAttack;
	
	private View compassView;
	
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
        btnAttack = (Button) rootView.findViewById(R.id.btn_attack);
        btnFindCombat.setOnClickListener(this);
        btnAttack.setOnClickListener(this);
        //TODO - add click listener that gets that makes api call and populates data
        
        compassLayout = (RelativeLayout) rootView.findViewById(R.id.compass_layout);
	    compassView = new CompassView(activity); 
	    compassLayout.addView(compassView, new LayoutParams(  
        		RelativeLayout.LayoutParams.MATCH_PARENT,  
          RelativeLayout.LayoutParams.MATCH_PARENT));  
        
   		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
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
		case (R.id.btn_attack):
			callback.attack();
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
		
		((CompassView)compassView).setPlayer(player);
	}
	
	public void populateCombat(Combat combat) {
		
		txtEnemyName.setText(combat.getEnemy().getEnemyName());
		txtTimeRemaining.setText("" + combat.getSecondRemaining());

		((CompassView)compassView).setCombat(combat);
	}
	
}
