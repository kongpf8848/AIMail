package com.kongpf8848.dmail.mailcore

import com.kongpf8848.dmail.bean.OAuthToken
import com.libmailcore.SMTPSession

class SMTPSessionWithToken(val token: OAuthToken?) : SMTPSession()