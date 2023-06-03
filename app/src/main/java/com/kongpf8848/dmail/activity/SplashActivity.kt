package com.kongpf8848.dmail.activity

import android.content.Intent
import android.os.Bundle
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.databinding.ActivitySplashBinding
import com.kongpf8848.dmail.library.base.BaseMvvmActivity
import com.kongpf8848.dmail.library.mvvm.EmptyViewModel
import com.kongpf8848.dmail.login.LoginActivity

class SplashActivity: BaseMvvmActivity<EmptyViewModel, ActivitySplashBinding>() {

    override fun getLayoutId(): Int {
       return R.layout.activity_splash
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        jump()
    }

    private fun jump(){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}