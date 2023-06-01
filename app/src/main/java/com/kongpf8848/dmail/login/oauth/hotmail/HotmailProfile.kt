package com.kongpf8848.dmail.login.oauth.hotmail

/**
 * {
 * "id": "70dfe6f35a324887",
 * "name": "xxx",
 * "first_name": "xx",
 * "last_name": "x",
 * "link": "https://profile.live.com/",
 * "gender": null,
 * "emails": {
 * "preferred": "xxx@outlook.com",
 * "account": "xxx@outlook.com",
 * "personal": null,
 * "business": null
 * },
 * "locale": "zh_CN",
 * "updated_time": null
 * }
 */
class HotmailProfile {
    private val id: String? = null
    private val name: String? = null
    private val first_name: String? = null
    private val last_name: String? = null
    private val link: String? = null
    private val gender: Any? = null
    val emails: Emails?=null
    private val locale: String? = null
    private val updated_time: Any? = null

    inner class Emails {
        private val preferred: String? = null
        val account: String? = null
        private val personal: Any? = null
        private val business: Any? = null
    }
}