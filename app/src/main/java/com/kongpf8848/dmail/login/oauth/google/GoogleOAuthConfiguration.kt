package com.kongpf8848.dmail.login.oauth.google

import com.kongpf8848.dmail.login.DMAccountType
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration

class GoogleOAuthConfiguration : OAuthConfiguration {

    override val accountType = DMAccountType.TYPE_GOOGLE
    override val clientId= Companion.clientId
    override val clientSecret: String? = null
    override val authorizationURL= Companion.authorizationURL
    override val tokenURL= Companion.tokenURL
    override val redirectURL= Companion.redirectURL
    override val scopes=Companion.scopes

    companion object {
        private const val clientId = "262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3.apps.googleusercontent.com"
        private const val authorizationURL = "https://accounts.google.com/o/oauth2/v2/auth"
        private const val tokenURL = "https://oauth2.googleapis.com/token"
        private const val redirectURL = "com.googleusercontent.apps.262622259280-5qb3vtj68d5dtudmaif4g9vd3cpar8r3:/oauth2redirect"
        private val scopes = listOf("https://mail.google.com/","openid","email")
    }
}