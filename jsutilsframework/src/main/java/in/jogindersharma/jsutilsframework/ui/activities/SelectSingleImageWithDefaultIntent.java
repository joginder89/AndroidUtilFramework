package in.jogindersharma.jsutilsframework.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import in.jogindersharma.jsutilsframework.R;
import in.jogindersharma.jsutilsframework.utils.images.CameraUtils;

public class SelectSingleImageWithDefaultIntent extends Activity {

    private Uri mCapturedImageURI = null;
    String TAG = "SelectSingleImageWithDefaultIntent";
    TextView tvChoosePhoto, tvTakePhoto;
    int CHOOSE_PHOTO = 11;
    int TAKE_PHOTO = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_single_image_with_default_intent);
        initLayout();

    }

    private void initLayout() {
        tvChoosePhoto = (TextView) findViewById(R.id.tvChoosePhoto);
        tvTakePhoto = (TextView) findViewById(R.id.tvTakePhoto);

        tvChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(CHOOSE_PHOTO);
            }
        });

        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(TAKE_PHOTO);
            }
        });
    }

    private void selectImage(int actionId) {

        if (actionId == TAKE_PHOTO) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mCapturedImageURI = CameraUtils.getOutputMediaFileUri(1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(intent, actionId);
        } else if (actionId == CHOOSE_PHOTO) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, actionId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "requestCode : " + requestCode + " , resultCode : " + resultCode + " , data : " + data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                try {
                    String pathToInternallyStoredImage = CameraUtils.saveToInternalStorage(this, mCapturedImageURI);
                    finishActivityWithResult(pathToInternallyStoredImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CHOOSE_PHOTO) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Log.d(TAG, "picturePath : " + picturePath);
                finishActivityWithResult(picturePath);
            }
        }
    }

    private void finishActivityWithResult(String imagePath) {
        Bundle conData = new Bundle();
        conData.putString("image_path", imagePath);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        Log.e(TAG, "imagePath : " + imagePath);
        finish();
    }

    /**
     * Use these function to use this class in your project
     *
     */

    /*private void showGallery() {
        Intent getIntent = new Intent(ImageCropActivity.this, SelectImage.class);
        startActivityForResult(getIntent, 7);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "requestCode : " + requestCode + " , resultCode : " + resultCode + " , data : " + data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 7:
                    String resulPath = data.getExtras().getString("image_path");
                    if (resulPath != null) {
                        Intent intent = new Intent(this, StretchActivity.class);
                        intent.putExtra(AppConstants.EXTRAS.IMAGE_PATH, resulPath);
                        startActivity(intent);
                    }
                    Log.e(TAG, "resulPath : "+resulPath);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

}