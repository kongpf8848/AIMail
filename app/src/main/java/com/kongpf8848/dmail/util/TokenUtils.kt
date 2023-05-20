package com.kongpf8848.dmail.util

import android.util.Base64
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

object TokenUtils {
    fun getEmailFromIdToken(idToken: String): String {
        try {
            val split = idToken.split("\\.").toTypedArray()
            val jwtBody = split[1]
            val decodedBodyBytes = Base64.decode(jwtBody, Base64.URL_SAFE)
            val body = String(decodedBodyBytes, StandardCharsets.UTF_8)
            val jsonObject = JSONObject(body)
            return jsonObject.optString("email")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}