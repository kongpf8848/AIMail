package com.libmailcore.androidexample;

public interface UCallback<K,T> {

    void succeeded(K k);

    void onFailed(T t);
}
