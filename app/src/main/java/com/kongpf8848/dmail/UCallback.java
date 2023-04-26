package com.kongpf8848.dmail;

public interface UCallback<K,T> {

    void succeeded(K k);

    void onFailed(T t);
}
