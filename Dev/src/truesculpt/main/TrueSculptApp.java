package truesculpt.main;

import android.app.Application;

public class TrueSculptApp extends Application {

	private Managers mManagers = new Managers();
		
	public Managers getManagers() {
		return mManagers;
	}

	@Override
	public void onCreate() {		
		super.onCreate();
		

	}

	@Override
	public void onTerminate() {		
		super.onTerminate();
		
		
	}
}
