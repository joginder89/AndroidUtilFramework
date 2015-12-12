package in.jogindersharma.jsutilsframework.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class RunTimePermissionUtility {

    static final String TAG = "RunTimePermissionUtilit";

    public static boolean doWeHaveReadExternalStoragePermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestReadExternalStoragePermission(Activity activity,int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
    }

    public static void showReasonBoxForReadExternalStoragePermission(final Activity activity, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note this permission is required")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            requestReadExternalStoragePermission(activity, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note previously you do not granted this permission. Please go to setting and enable this permission for this application.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public static boolean doWeHaveWriteExternalStoragePermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestWriteExternalStoragePermission(Activity activity,int requestCode) {
        Log.e(TAG, "Going to have have Storage Permission");
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    public static void showReasonBoxForWriteExternalStoragePermission(final Activity activity, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note this permission is required to save photo in external storage.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            requestWriteExternalStoragePermission(activity, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note previously you do not granted this permission. Please go to setting and enable this permission for this application.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public static boolean doWeHaveContactPermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED);
    }

    public static void requestContactPermission(Activity activity,int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, requestCode);
    }

    public static void showReasonBoxForContactPermission(final Activity activity, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note this permission is required")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            requestContactPermission(activity, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            new AlertDialog.Builder(activity)
                    .setTitle("Required Attention")
                    .setMessage("Please Note previously we not granted this permission. Please go to setting and enable this permission for this application.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}