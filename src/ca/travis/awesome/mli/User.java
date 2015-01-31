package ca.travis.awesome.mli;

import android.content.Context;

public class User {

	private boolean isUser;
	private String username;
	private LocationWrapper locationWrapper;
	private OrientationWrapper orientation;


	public User() {}
	
	public User(Context context, String username, boolean isUser) {
	        this.username = username;
	        this.isUser = isUser;
	        
	        if (isUser) {
				locationWrapper = new LocationWrapper(context);
				orientation = new OrientationWrapper(context);
	        }
	    }

	public boolean isUser() {
		return isUser;
	}

	public String getUsername() {
		return username;
	}

	public LocationWrapper getLocationWrapper() {
		return locationWrapper;
	}

	public OrientationWrapper getOrientation() {
		return orientation;
	}
}
