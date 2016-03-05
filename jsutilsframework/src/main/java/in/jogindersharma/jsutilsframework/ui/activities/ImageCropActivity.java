package in.jogindersharma.jsutilsframework.ui.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import in.jogindersharma.jsutilsframework.R;
import in.jogindersharma.jsutilsframework.utils.files.FileUtil;
import in.jogindersharma.jsutilsframework.view.imagecrop.CropImageView;

public class ImageCropActivity extends JSBaseActivity {

    private CropImageView mCropView;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        imagePath = getIntent().getStringExtra("image_path");
        initLayouts();
    }

    private void initLayouts() {
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        findViewById(R.id.buttonFitImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_FIT_IMAGE);
            }
        });
        findViewById(R.id.button1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
            }
        });
        findViewById(R.id.button3_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
            }
        });
        findViewById(R.id.button4_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
            }
        });
        findViewById(R.id.button9_16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
            }
        });
        findViewById(R.id.button16_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
            }
        });
        findViewById(R.id.buttonFree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCropView.setCropMode(CropImageView.CropMode.RATIO_FREE);
            }
        });
        findViewById(R.id.buttonRotateImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
        findViewById(R.id.buttonCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCustomRatio(7, 5);
            }
        });
        findViewById(R.id.buttonCircle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
            }
        });

        findViewById(R.id.buttonCropImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivityWithResult();
            }
        });
    }

    private void finishActivityWithResult() {

        String imagePath = FileUtil.saveBitmapToLocal(mCropView.getCroppedBitmap(), this);

        Bundle conData = new Bundle();
        conData.putString("image_path", imagePath);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        Log.e(TAG, "imagePath : " + imagePath);
        finish();
    }
}