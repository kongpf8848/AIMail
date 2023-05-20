package com.kongpf8848.dmail.util

interface UCallback<K, T> {
    fun succeeded(k: K)
    fun onFailed(t: T)
}