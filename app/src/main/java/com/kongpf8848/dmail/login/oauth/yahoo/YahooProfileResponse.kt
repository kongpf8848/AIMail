package com.kongpf8848.dmail.login.oauth.yahoo

/**
 * {
 * "sub": "AN4FL4S53NXH6QJHJRVMAM2PLE",
 * "name": "xxx",
 * "given_name": "xxx",
 * "family_name": "xxx",
 * "locale": "zh-CN",
 * "email": "xxx@yahoo.com",
 * "email_verified": true,
 * "profile_images": {
 * "image32": "xxx.jpg",
 * "image64": "xxx.jpg",
 * "image128": "xxx.jpg",
 * "image192": "xxx.jpg"
 * },
 * "nickname": "xxx",
 * "picture": "xxx.jpg"
 * }
 */
class YahooProfileResponse {
    var sub: String? = null
    var name: String? = null
    var given_name: String? = null
    var family_name: String? = null
    var locale: String? = null
    var email: String? = null
    var email_verified = false
    var nickname: String? = null
    var picture: String? = null
}