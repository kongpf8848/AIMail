package com.libmailcore.androidexample;

import android.app.Application;
import android.os.LocaleList;

import com.libmailcore.MailCoreInit;

import java.io.File;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initMailCore();
    }

    private void initMailCore() {
        File icuDataFile = Utils.extractAsset(this,R.raw.icudt62l, "icud", "icudt62l.dat");
        String icuDataPath = icuDataFile.getParentFile().getAbsolutePath();
        MailCoreInit mailCoreInit = new MailCoreInit();
        mailCoreInit.setLogEnabled(BuildConfig.DEBUG ? 1 : 0);
        mailCoreInit.setLocaleData(Utils.getDefaultLocale(), icuDataPath);
    }
}
