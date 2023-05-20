package com.kongpf8848.dmail.mailcore

import com.kongpf8848.dmail.bean.OAuthToken
import com.libmailcore.IMAPSession

class IMAPSessionWithToken(val token: OAuthToken?) : IMAPSession()