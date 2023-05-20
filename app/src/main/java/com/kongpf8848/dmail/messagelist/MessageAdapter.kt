package com.kongpf8848.dmail.messagelist

import com.libmailcore.IMAPMessage

class MessageAdapter(var message: IMAPMessage) {
    override fun toString(): String {
        return message.header().subject()
    }
}