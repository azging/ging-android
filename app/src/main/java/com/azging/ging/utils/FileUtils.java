package com.azging.ging.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 */

public class FileUtils {

    public static final String ROOT_DIR = "1601";
    public static final String SYS_DIR_CACHE = "cache";
    private static final String JSON_CACHE = "json";

    public static File getRootFile(Context context) {
        String externalCacheDir;
        //判断外置sd卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //当前手机运行的版本号
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                //sd卡/Android/data/包名/cache
                externalCacheDir = context.getExternalCacheDir().getAbsolutePath();
            } else {
                //sd卡/1601
                externalCacheDir = Environment.getExternalStorageDirectory() + ROOT_DIR;
            }

        } else {
            //表示系统目录
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                //sys/data/data/包名/cache
                externalCacheDir = context.getCacheDir().getAbsolutePath();
            } else {
                //sys/data/data/包名/cache
                externalCacheDir = Environment.getDataDirectory() + File.separator + context.getPackageName() + File.separator + SYS_DIR_CACHE;
            }

        }
        File file = new File(externalCacheDir);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;

    }

    public static File getJsonCache(Context context) {

        String jsonPath = getRootFile(context) + JSON_CACHE;
        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            jsonFile.mkdirs();
        }
        return jsonFile;

    }


}
