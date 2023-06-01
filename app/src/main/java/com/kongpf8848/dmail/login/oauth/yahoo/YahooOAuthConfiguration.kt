package com.kongpf8848.dmail.login.oauth.yahoo

import com.kongpf8848.dmail.login.DMAccountType
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration

class YahooOAuthConfiguration : OAuthConfiguration {
    override val accountType= DMAccountType.TYPE_YAHOO
    override val clientId=Companion.clientSecret
    override val clientSecret= Companion.clientSecret
    override val authorizationURL= Companion.authorizationURL
    override val tokenURL = Companion.tokenURL
    override val redirectURL = Companion.redirectURL
    override val scopes= Companion.scopes

    companion object {
        private val clientId ="dj0yJmk9ZVN2ZWQwSGxhNVhhJmQ9WVdrOU9VNXJlVmRoY1hJbWNHbzlNQT09JnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTAz"
        private const val clientSecret = "01484dfed7cece1de1d15308dccc35d2cf140083"
        private const val authorizationURL = "https://api.login.yahoo.com/oauth2/request_auth"
        private const val tokenURL = "https://api.login.yahoo.com/oauth2/get_token"
        private const val redirectURL = "com.kongpf8848.dmail://oauth2redirect"
        private val scopes = listOf("openid")
    }
}