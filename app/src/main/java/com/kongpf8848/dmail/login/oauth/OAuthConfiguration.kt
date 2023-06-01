package com.kongpf8848.dmail.login.oauth

import com.kongpf8848.dmail.login.DMAccountType

interface OAuthConfiguration {
    val accountType: DMAccountType
    val clientId: String
    val clientSecret: String?
    val authorizationURL: String
    val tokenURL: String
    val redirectURL: String
    val scopes: List<String>
}