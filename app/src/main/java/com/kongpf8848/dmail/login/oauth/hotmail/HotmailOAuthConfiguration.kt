package com.kongpf8848.dmail.login.oauth.hotmail

import com.kongpf8848.dmail.login.DMAccountType
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration

class HotmailOAuthConfiguration : OAuthConfiguration {

    override val accountType = DMAccountType.TYPE_HOTMAIL
    override val clientId = Companion.clientId
    override val clientSecret: String? = null
    override val authorizationURL = Companion.authorizationURL
    override val tokenURL = Companion.tokenURL
    override val redirectURL = Companion.redirectURL
    override val scopes = Companion.scopes

    companion object {
        private val clientId = "e647013a-ada4-4114-b419-e43d250f99c5"
        private val clientSecret: String? = null
        private const val authorizationURL = "https://login.live.com/oauth20_authorize.srf"
        private const val tokenURL = "https://login.live.com/oauth20_token.srf"
        private const val redirectURL = "msauth://com.fsck.k9.debug/VZF2DYuLYAu4TurFd6usQB2JPts%3D"
        private val scopes = listOf("wl.basic", "wl.offline_access", "wl.emails", "wl.imap")
    }
}