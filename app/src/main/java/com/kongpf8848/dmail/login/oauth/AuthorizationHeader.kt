package com.kongpf8848.dmail.login.oauth

class AuthorizationHeader(private val accessToken: String) {
    override fun toString()="Bearer $accessToken"
}