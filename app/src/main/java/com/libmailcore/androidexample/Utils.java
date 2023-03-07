package com.libmailcore.androidexample;

import android.content.Context;
import android.os.Build;
import android.os.LocaleList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Utils {


    public static File extractAsset(Context context, int assetResId, String folderName, String fileName) {
        File cacheFolder = new File(context.getCacheDir(), folderName);
        if(!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }
        File outputFile = new File(cacheFolder, fileName);
        if (!outputFile.exists() || outputFile.length() <= 0) {
            byte[] buffer = new byte[4096];
            int byteCount = 0;
            try {
                InputStream is = context.getResources().openRawResource(assetResId);
                FileOutputStream fos = new FileOutputStream(outputFile);
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            } catch (IOException e) {
                outputFile.delete();
                e.printStackTrace();
            }
        }
        return outputFile;
    }
    public static String getDefaultLocale() {
        Locale locale = getLocale();
        String localeDef = locale.getLanguage() + "_" + locale.getCountry();
        return localeDef;
    }

    public static Locale getLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
