package in.jogindersharma.jsutilsframework.utils.files;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.jogindersharma.jsutilsframework.utils.Constants;
import in.jogindersharma.jsutilsframework.utils.images.BitmapUtil;
import rx.Observable;
import rx.Subscriber;

public class FileUtil {
    private static final String TAG = Constants.TAG + FileUtil.class.getSimpleName();
    private static final int SELECTED_IMAGE_SHRINK_SIZE = 720 ;
    private static FileUtil instance = null;
    private static Context mContext;
    private static final String APP_DIR = "Abner";

    public static String saveImageToExternalStorage(String appName, Bitmap finalBitmap) {
        boolean isFolderCreated = true;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File appImagesDirPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), appName);

        if (!appImagesDirPath.exists()) {
            isFolderCreated = appImagesDirPath.mkdirs();
        }

        if (isFolderCreated) {
            File imageFile = new File(appImagesDirPath.getPath() + File.separator + "IMG_" + timeStamp + ".png");
            try {
                imageFile.createNewFile();
                FileOutputStream out = new FileOutputStream(imageFile);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

                return imageFile.getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Not able to create Image on SD Card");
                return null;
            }
        } else {
            Log.e(TAG, "Not able to create Folder on SD Card");
            return null;
        }
    }

    public static FileUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (FileUtil.class) {
                if (instance == null) {
                    mContext = context.getApplicationContext();
                    instance = new FileUtil();
                }
            }
        }
        return instance;
    }

    public static String saveBitmapToLocal(Bitmap bm, Context context) {
        String path = null;
        try {
            File file = FileUtil.getInstance(context).createTempFile("IMG_", ".png");
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
            Log.e("saveBitmapToLocal => ", path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    public File createTempFile(String prefix, String extension) throws IOException {
        File file = new File(getAppDirPath() + ".TEMP/" + prefix
                + System.currentTimeMillis() + extension);
        Log.e(TAG, "file : " + file);

        Log.e(TAG, "file.getParentFile().mkdirs() : " + file.getParentFile().mkdirs());
        Log.e(TAG, "createNewFile : " + file.createNewFile());
        return file;
    }

    public String getAppDirPath() {
        String path = null;
        if (getLocalPath() != null) {
            path = getLocalPath() + APP_DIR + "/";
            Log.e(TAG, "path : " + path);
        }
        return path;
    }

    private static String getLocalPath() {
        String sdPath = null;
        sdPath = mContext.getFilesDir().getAbsolutePath() + "/";
        Log.e(TAG, "sdPath : " + sdPath);
        return sdPath;
    }

    //RxJava

    public static Observable<String> saveImageToExternalStorageObservable(final String saveImagePath, final String appName) {
        return Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Bitmap bitmap = BitmapFactory.decodeFile(saveImagePath);
                        String savedImageName = FileUtil.saveImageToExternalStorage(appName, bitmap);
                        subscriber.onNext(savedImageName);
                        subscriber.onCompleted();
                        //hideProgressDialog();
                    }
                }
        );
    }

    public static Observable<String> saveBitmapToExternalStorageObservable(final Bitmap bitmap, final String appName) {
        return Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String savedImageName = FileUtil.saveImageToExternalStorage(appName, bitmap);
                        subscriber.onNext(savedImageName);
                        subscriber.onCompleted();
                        //hideProgressDialog();
                    }
                }
        );
    }

    public static Observable<ArrayList<String>> saveMultipleFilesToExternalStorageObservable(final ArrayList<String> list , final String appName) {
        return Observable.create(
                new Observable.OnSubscribe<ArrayList<String>>() {
                    @Override
                    public void call(Subscriber<? super ArrayList<String>> subscriber) {

                        ArrayList<String> filesPath = new ArrayList ( list.size() );
                        for ( String path : list ) {
                            Bitmap bitmap = BitmapUtil.shrinkBitmap(path, FileUtil.SELECTED_IMAGE_SHRINK_SIZE, FileUtil.SELECTED_IMAGE_SHRINK_SIZE);
                            filesPath.add(FileUtil.saveImageToExternalStorage(appName, bitmap));
                        }
                        subscriber.onNext( filesPath );
                        subscriber.onCompleted();

                    }
                }
        );
    }
}
