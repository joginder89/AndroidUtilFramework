package in.jogindersharma.jsutilsframework.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SelectAndCropImageActivity extends JSBaseActivity {

    final int REQUEST_CODE_FOR_SELECT_IMAGE = 7;
    final int REQUEST_CODE_FOR_CROP_IMAGE = 8;
    Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntent = new Intent(SelectAndCropImageActivity.this, SelectSingleImageWithDefaultIntent.class);
        startActivityForResult(getIntent, REQUEST_CODE_FOR_SELECT_IMAGE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "requestCode : " + requestCode + " , resultCode : " + resultCode + " , data : " + data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FOR_SELECT_IMAGE:
                    String imagePath = data.getExtras().getString("image_path");
                    Log.e(TAG, "resultPathForBackground : "+imagePath);
                    if (imagePath != null) {
                        getIntent = new Intent(SelectAndCropImageActivity.this, ImageCropActivity.class);
                        getIntent.putExtra("image_path", imagePath);
                        startActivityForResult(getIntent, REQUEST_CODE_FOR_CROP_IMAGE);
                    }
                    break;
                case REQUEST_CODE_FOR_CROP_IMAGE:
                    String resultPathForClayPart = data.getExtras().getString("image_path");
                    Log.e(TAG, "resultPathForClayPart : "+ resultPathForClayPart);
                    if (resultPathForClayPart != null) {
                        finishActivityWithResult(resultPathForClayPart);
                    }
                    break;
            }
        } else {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}