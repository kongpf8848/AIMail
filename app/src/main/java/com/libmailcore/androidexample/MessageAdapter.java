package com.libmailcore.androidexample;

import com.libmailcore.IMAPMessage;

public class MessageAdapter {
    IMAPMessage message;

    public MessageAdapter(IMAPMessage msg) {
        message = msg;
    }

    public String toString() {
        return message.header().subject();
    }
}
