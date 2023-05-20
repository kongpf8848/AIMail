package com.kongpf8848.dmail.util;

public class DomainUtils {
    public static String getDomain(String username){
        int index=username.indexOf("@");
        if(index>0){
            return username.substring(index+1);
        }
        return "";
    }
}
