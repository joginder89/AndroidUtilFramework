package in.jogindersharma.jsframeworkdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import in.jogindersharma.jsutilsframework.ui.activities.JSBaseActivity;
import in.jogindersharma.jsutilsframework.utils.RunTimePermissionUtility;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.jogindersharma.jsutilsframework.utils.files.FileUtil.saveImageToExternalStorageObservable;

public class SelectAndCropImage extends JSBaseActivity implements View.OnClickListener {

    Button bSelectImage;
    ImageView ivSelectedImage;
    private static final int WRITE_EXTERNAL_REQUEST_CODE = 101;
    private static final int PICK_PHOTO_REQUEST_CODE = 102;
    String TAG = "SelectImageActivity";
    String selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_and_crop_image);

        initLayouts();
    }

    private void initLayouts() {

        ivSelectedImage = (ImageView) findViewById(R.id.ivSelectedImage);

        findViewById(R.id.bSelectImage).setOnClickListener(this);
        findViewById(R.id.bSaveImage).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSelectImage:
                if(RunTimePermissionUtility.doWeHaveWriteExternalStoragePermission(SelectAndCropImage.this)) {
                    Log.e(TAG, "Already have Storage Permission");
                    selectAndCropImage();
                } else {
                    Log.e(TAG, "Going to have have Storage Permission");
                    RunTimePermissionUtility.requestWriteExternalStoragePermission(SelectAndCropImage.this, WRITE_EXTERNAL_REQUEST_CODE);
                }
                break;
            case R.id.bSaveImage:
                saveImage();
                break;
        }
    }

    private void selectAndCropImage() {
        Intent intent = new Intent(SelectAndCropImage.this, in.jogindersharma.jsutilsframework.ui.activities.SelectAndCropImageActivity.class);
        startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("--", "requestCode : " + requestCode + ", permissions :" + permissions + ",grantResults : " + grantResults);
        switch (requestCode){
            case WRITE_EXTERNAL_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission", "WRITE_EXTERNAL permission has now been granted. Showing result.");
                    selectAndCropImage();
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
                    selectedImage = data.getStringExtra("image_path");
                    ivSelectedImage.setImageBitmap(BitmapFactory.decodeFile(selectedImage));
                }
        }
    }

    private void saveImage() {
        if(selectedImage != null) {
            saveImageToExternalStorage(selectedImage, getString(R.string.app_name));
        } else {
            Log.e(TAG, "image Path is null. So can not save to SD Card");
        }
    }

    public void showAlertDialog(String message) {
        showAlertDialog(null, message);
    }

    private void saveImageToExternalStorage(String imagePath, String appName) {
        showProgressDialog("Saving Photo...");
        saveImageToExternalStorageObservable(imagePath, appName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        hideProgressDialog();
                        Log.e(TAG, "onNext : " + s);
                        if(s != null) {
                            showAlertDialog("Photo saved to " + s);
                        } else {
                            showAlertDialog(getString(R.string.error_in_saving_image_to_sd_card));
                        }
                    }
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                        Log.e(TAG, "onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        e.printStackTrace();
                        showAlertDialog(e.getMessage());
                    }
                });
    }

}
