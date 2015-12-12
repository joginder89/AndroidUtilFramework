package in.jogindersharma.jsframeworkdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import in.jogindersharma.jsutilsframework.ui.activities.SelectSingleImageWithDefaultIntent;
import in.jogindersharma.jsutilsframework.utils.RunTimePermissionUtility;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener {

    Button bSelectImage;
    ImageView ivSelectedImage;
    private static final int WRITE_EXTERNAL_REQUEST_CODE = 101;
    private static final int PICK_PHOTO_REQUEST_CODE = 102;
    String TAG = "SelectImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        initLayouts();
    }

    private void initLayouts() {

        bSelectImage = (Button) findViewById(R.id.bSelectImage);
        ivSelectedImage = (ImageView) findViewById(R.id.ivSelectedImage);

        bSelectImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSelectImage:
                if(RunTimePermissionUtility.doWeHaveWriteExternalStoragePermission(SelectImageActivity.this)) {
                    Log.e(TAG, "Already have Storage Permission");
                    selectImageFromSdCard();
                } else {
                    Log.e(TAG, "Going to have have Storage Permission");
                    RunTimePermissionUtility.requestWriteExternalStoragePermission(SelectImageActivity.this, WRITE_EXTERNAL_REQUEST_CODE);
                }
                break;
        }
    }

    private void selectImageFromSdCard() {
        Intent intent = new Intent(SelectImageActivity.this, SelectSingleImageWithDefaultIntent.class);
        startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("--", "requestCode : " + requestCode + ", permissions :" + permissions + ",grantResults : " + grantResults);
        switch (requestCode){
            case WRITE_EXTERNAL_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "WRITE_EXTERNAL permission has now been granted. Showing result.");
                    selectImageFromSdCard();
                } else {
                    Log.e("Permission", "WRITE_EXTERNAL permission was NOT granted.");
                    RunTimePermissionUtility.showReasonBoxForWriteExternalStoragePermission(this, WRITE_EXTERNAL_REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String selectedImage = data.getStringExtra("image_path");
                    ivSelectedImage.setImageBitmap(BitmapFactory.decodeFile(selectedImage));
                }
        }
    }

}
