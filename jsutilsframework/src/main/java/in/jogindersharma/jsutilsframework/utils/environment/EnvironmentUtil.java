/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2013 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.jogindersharma.jsutilsframework.utils.environment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class EnvironmentUtil {
    /**
     * Check if the external storage is mounted 'read/write'.
     * 
     * @return {@code true} if it is mounted 'read/write', {@code false} otherwise.
     */
    public static boolean isExternalStorageMountedReadWrite() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(externalStorageState);
    }

    /**
     * Returns the cache directory, using {@link Context#getExternalCacheDir()} on level 8+ or an equivalent call for level < 8.
     */
    public static File getExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return getExternalCacheDirFroyo(context);
        }
        // API Level <8 Equivalent of context.getExternalCacheDir()
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return new File(externalStorageDirectory, "Android/data/" + context.getPackageName() + "/cache");
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private static File getExternalCacheDirFroyo(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * Returns the files directory, using {@link Context#getExternalFilesDir(String)} on level 8+ or an equivalent call for level < 8.
     */
    public static File getExternalFilesDir(Context context, String type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return getExternalFilesDirFroyo(context, type);
        }
        // API Level <8 Equivalent of context.getExternalFilesDir()
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return new File(externalStorageDirectory, "Android/data/" + context.getPackageName() + "/files");
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private static File getExternalFilesDirFroyo(Context context, String type) {
        return context.getExternalFilesDir(type);
    }
}
