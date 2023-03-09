package com.libmailcore.androidexample.bean;

import java.io.Serializable;

public class MailInfo implements Serializable {
    public String html;
    public String html_body;
    public byte[]origin_data;

    public MailInfo(String html, String html_body, byte[] origin_data) {
        this.html = html;
        this.html_body = html_body;
        this.origin_data = origin_data;
    }
}
