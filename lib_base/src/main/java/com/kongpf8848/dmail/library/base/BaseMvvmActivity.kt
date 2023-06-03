package com.kongpf8848.dmail.library.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseMvvmActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity() {

    lateinit var viewModel: VM
    lateinit var binding: VDB

    protected abstract fun getLayoutId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateStart(savedInstanceState)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        createViewModel()
        onCreateEnd(savedInstanceState)
    }

    protected open fun onCreateStart(savedInstanceState: Bundle?) {}
    protected open fun onCreateEnd(savedInstanceState: Bundle?) {}

    /**
     * 创建ViewModel
     */
    private fun createViewModel() {
        val type = findType(javaClass.genericSuperclass)
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            BaseViewModel::class.java as Class<VM>
        }
        viewModel = ViewModelProvider(this).get(modelClass)
    }

    private fun findType(type: Type):Type?{
        return when(type){
            is ParameterizedType -> type
            is Class<*> ->{
                findType(type.genericSuperclass)
            }
            else ->{
                null
            }
        }
    }
}