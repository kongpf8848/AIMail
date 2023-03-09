# MailCore2Example
演示使用[MailCore2](https://github.com/MailCore/mailcore2)类库拉取邮件和查看邮件详情的Demo。
# 说明
- **此Demo仅适用于QQ邮箱，163邮箱等使用密码/授权码可以直接登录的IMAP/SMTP邮箱，而需要走OAuth2认证的邮箱则不适用，如GMail，Outlook等邮箱。本Demo中使用的mailcore2-android-4.aar非官方版本，经过修改和重新打包。**
- 运行之前需要替换[app/src/main/java/com/libmailcore/androidexample/api/SessionManager.java](https://github.com/kongpf8848/MailCore2Example/blob/master/app/src/main/java/com/libmailcore/androidexample/api/SessionManager.java)文件里的username，password信息。
```java
public static IMAPSession getIMAPSession(){
    //username，password替换成自己的信息
    String username = "xxxxx@163.com";
    String password = "xxxx";
    String hostname = "imap.163.com";
    IMAPSession imapSession = new IMAPSession();
    imapSession.setUsername(username);
    imapSession.setPassword(password);
    imapSession.setHostname(hostname);
    imapSession.setPort(993);
    imapSession.setConnectionType(ConnectionType.ConnectionTypeTLS);
    imapSession.setTimeout(15);
    imapSession.setCheckCertificateEnabled(false);
    IMAPIdentity imapIdentity = imapSession.clientIdentity();
    imapIdentity.setName("jack");
    imapIdentity.setVendor("jack");
    imapIdentity.setVersion("0.0.1");
    return imapSession;
}
```  
    
