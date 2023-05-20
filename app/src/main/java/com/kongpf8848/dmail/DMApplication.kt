package com.kongpf8848.dmail

import android.app.Application
import com.kongpf8848.dmail.util.Utils
import com.libmailcore.MailCoreInit

class DMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initMailCore2()
    }

    private fun initMailCore2() {
        val icuDataFile = Utils.extractAsset(this, R.raw.icudt62l, "icud", "icudt62l.dat")
        val icuDataPath = icuDataFile.parentFile.absolutePath
        val mailCoreInit = MailCoreInit()
        mailCoreInit.setLogEnabled(if (BuildConfig.DEBUG) 1 else 0)
        mailCoreInit.setLocaleData(Utils.defaultLocale, icuDataPath)
    }
}