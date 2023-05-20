package com.kongpf8848.dmail.util

object DomainUtils {

    fun getUsername(address: String): String {
        val index = address.indexOf("@")
        return if (index > 0) {
            address.substring(0,index)
        } else ""
    }

    fun getDomain(address: String): String {
        val index = address.indexOf("@")
        return if (index > 0) {
            address.substring(index + 1)
        } else ""
    }

}