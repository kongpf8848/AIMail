package com.kongpf8848.dmail.util;

public interface UCallback<K,T> {

    void succeeded(K k);

    void onFailed(T t);
}
