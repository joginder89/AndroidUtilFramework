package in.jogindersharma.jsutilsframework.ui.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import in.jogindersharma.jsutilsframework.R;

public abstract class JSBaseActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    String TAG = "JSBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsutils_main);
    }

    public void showProgressDialog(String message) {
        hideProgressDialog();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(JSBaseActivity.this);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            if(message != null) {
                mProgressDialog.setMessage(message);
            } else {
                mProgressDialog.setMessage("Loading...");
            }
            mProgressDialog.show();
        } else {
            Log.e(TAG, "mProgressDialog is not Null. So Not able to show Progress Dialog.");
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showAlertDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Title");
        //alertDialogBuilder.setIcon(R.drawable.notification_template_icon_bg);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });

        /*alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });*/

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
