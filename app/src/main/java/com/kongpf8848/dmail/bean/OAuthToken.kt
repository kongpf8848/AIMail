package com.kongpf8848.dmail.bean

import com.kongpf8848.dmail.login.DMAccountType
import java.io.Serializable

class OAuthToken : Serializable {
    @JvmField
    var accountType: DMAccountType? = null
    @JvmField
    var access_token: String? = null
    @JvmField
    var refresh_token: String? = null
    @JvmField
    var expire_time: Long? = null
}