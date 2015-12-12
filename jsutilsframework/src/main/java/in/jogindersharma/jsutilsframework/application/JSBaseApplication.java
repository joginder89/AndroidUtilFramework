package in.jogindersharma.jsutilsframework.application;

import android.app.Application;
import android.content.Context;

public abstract class JSBaseApplication extends Application {

    String TAG = "JSBaseApplication";
    Context context = getApplicationContext();

    public boolean mIsAppInForeground;


    @Override
    public void onCreate() {
        super.onCreate();

        //registerActivityLifecycleCallbacks(new MyLifecycleHandler());
    }

}