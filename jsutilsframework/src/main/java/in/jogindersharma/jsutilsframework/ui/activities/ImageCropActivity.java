package in.jogindersharma.jsutilsframework.ui.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import in.jogindersharma.jsutilsframework.R;
import in.jogindersharma.jsutilsframework.utils.files.FileUtil;
import in.jogindersharma.jsutilsframework.view.imagecrop.CropImageView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    /*private void finishActivityWithResult() {
        showProgressDialog(null);
        String imagePath = FileUtil.saveBitmapToLocal(mCropView.getCroppedBitmap(), this);

        Bundle conData = new Bundle();
        conData.putString("image_path", imagePath);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        Log.e(TAG, "imagePath : " + imagePath);
        hideProgressDialog();
        finish();
    }*/

    private void finishActivityWithResult() {
        showProgressDialog(null);
        saveImagePathObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mySubscriber);
    }

    private Observable<String> saveImagePathObservable() {
        return Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String imagePath = FileUtil.saveBitmapToLocal(mCropView.getCroppedBitmap(), ImageCropActivity.this);
                        subscriber.onNext(imagePath);
                        subscriber.onCompleted();
                    }
                }
        );
    }

    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.e(TAG, "onNext : " + s);

            Bundle conData = new Bundle();
            conData.putString("image_path", s);
            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_OK, intent);
            hideProgressDialog();
            finish();
        }

        @Override
        public void onCompleted() {
            Log.e(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }
    };

}