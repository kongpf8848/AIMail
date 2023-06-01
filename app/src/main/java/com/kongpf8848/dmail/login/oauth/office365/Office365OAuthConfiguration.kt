package com.kongpf8848.dmail.login.oauth.office365

import com.kongpf8848.dmail.login.DMAccountType
import com.kongpf8848.dmail.login.oauth.OAuthConfiguration

class Office365OAuthConfiguration : OAuthConfiguration {
    override val accountType = DMAccountType.TYPE_OFFICE365
    override val clientId = Companion.clientId
    override val clientSecret: String? = null
    override val authorizationURL = Companion.authorizationURL
    override val tokenURL = Companion.tokenURL
    override val redirectURL = Companion.redirectURL
    override val scopes = Companion.scopes

    companion object {
        private val clientId = "e647013a-ada4-4114-b419-e43d250f99c5"
        private val clientSecret: String? = null
        private const val authorizationURL = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize"
        private const val tokenURL = "https://login.microsoftonline.com/common/oauth2/v2.0/token"
        private const val redirectURL = "msauth://com.fsck.k9.debug/VZF2DYuLYAu4TurFd6usQB2JPts%3D"
        private val scopes = listOf(
            "https://outlook.office.com/IMAP.AccessAsUser.All",
            "https://outlook.office.com/SMTP.Send",
            "openid",
            "email",
            "profile",
            "offline_access"
        )
    }
}