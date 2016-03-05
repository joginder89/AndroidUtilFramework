package in.jogindersharma.jsutilsframework.utils.social;
import android.content.Intent;

public class SocialUtil {

    /*

    use getResources().getString(R.string.app_name) to get appName
    use return Intent with startActivity();
    like startActivity(Intent.createChooser(emailIntent, "Send mail"));

    */

    public static Intent feedback(String appName) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"sharma.joginder89@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application Feedback - " + appName);
        //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My email content");
        //startActivity(Intent.createChooser(emailIntent, "Send mail"));
        //return Intent.createChooser(emailIntent, "Send mail");
        return emailIntent;
    }

    /*

    use getPackageName() inside Activity
    and use return Intent with startActivity();
    like startActivity(sendIntent);

    */

    public static Intent shareWith(String packageName) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this App : " + "http://play.google.com/store/apps/details?id=" + packageName);
        sendIntent.setType("text/plain");
        //startActivity(sendIntent);
        return sendIntent;
    }
}
