package in.jogindersharma.jsutilsframework.utils.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils {

    public static int MEDIA_TYPE_IMAGE = 1;
    static int MEDIA_TYPE_VIDEO = 2;

    public static Uri getOutputMediaFileUri(int type)
    {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type)
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "mSehat");

        createMediaStorageDir(mediaStorageDir);

        return createFile(type, mediaStorageDir);
    }

    private static File getOutputInternalMediaFile(Context context, int type)
    {
        File mediaStorageDir = new File(context.getFilesDir(), "myInternalPicturesDir");

        createMediaStorageDir(mediaStorageDir);

        return createFile(type, mediaStorageDir);
    }

    private static void createMediaStorageDir(File mediaStorageDir) // Used to be 'private void ...'
    {
        if (!mediaStorageDir.exists())
        {
            mediaStorageDir.mkdirs(); // Used to be 'mediaStorage.mkdirs();'
        }
    } // Was flipped the other way

    private static File createFile(int type, File mediaStorageDir ) // Used to be 'private File ...'
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir .getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        }
        else if(type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir .getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        }
        return mediaFile;
    }

    public static String saveToInternalStorage(Context context, Uri tempUri)
    {
        InputStream in = null;
        OutputStream out = null;

        File sourceExternalImageFile = new File(tempUri.getPath());
        File destinationInternalImageFile = new File(getOutputInternalMediaFile(context, 1).getPath());

        try
        {
            destinationInternalImageFile.createNewFile();

            in = new FileInputStream(sourceExternalImageFile);
            out = new FileOutputStream(destinationInternalImageFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //Handle error
        }
        finally
        {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    in.close();
                }
            } catch (IOException e) {
                // Eh
            }
        }
        return destinationInternalImageFile.getPath();
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
