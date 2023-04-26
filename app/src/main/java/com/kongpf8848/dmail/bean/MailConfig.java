package com.kongpf8848.dmail.bean;

import java.io.Serializable;

public class MailConfig implements Serializable {
   public MspService imap;
   public MspService smtp;
   public String token;
}
