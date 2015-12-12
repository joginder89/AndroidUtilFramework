package in.jogindersharma.jsutilsframework.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by joginder on 10/9/15.
 */
public class MyLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    // I use four separate variables here. You can, of course, just use two and
    // increment/decrement them instead of using four and incrementing them all.
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    String TAG = "MyLifecycleHandler";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated : " + activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e(TAG, "onActivityDestroyed : " + activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e(TAG, "onActivityResumed : " + activity);
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        Log.e("test", "application is in foreground: " + (resumed > paused));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e(TAG, "onActivitySaveInstanceState : " + activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e(TAG, "onActivityStarted : " + activity);
        ++started;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        Log.e("test", "application is visible: " + (started > stopped));
    }

    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }
}