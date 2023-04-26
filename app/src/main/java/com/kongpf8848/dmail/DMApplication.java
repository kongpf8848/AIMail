package com.kongpf8848.dmail;

import android.app.Application;

import com.libmailcore.MailCoreInit;

import java.io.File;

public class DMApplication extends Application {

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
