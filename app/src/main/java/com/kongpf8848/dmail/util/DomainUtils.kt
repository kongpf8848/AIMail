package com.kongpf8848.dmail.util

object DomainUtils {
    @JvmStatic
    fun getDomain(username: String): String {
        val index = username.indexOf("@")
        return if (index > 0) {
            username.substring(index + 1)
        } else ""
    }
}