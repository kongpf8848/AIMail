package com.kongpf8848.dmail.library.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected var context: Context

    init {
        context = application.applicationContext
    }


}