# MailCore2Example
演示使用[MailCore2](https://github.com/MailCore/mailcore2)类库拉取邮件和查看邮件详情的Demo。
# 说明
- **此Demo仅适用于QQ邮箱，163邮箱等使用密码/授权码可以直接登录的IMAP/SMTP邮箱，而需要走OAuth2认证的邮箱则不适用，如GMail，Outlook等邮箱。**
- 运行之前需要替换[app/src/main/java/com/libmailcore/androidexample/MessagesSyncManager.java](https://github.com/kongpf8848/MailCore2Example/blob/master/app/src/main/java/com/libmailcore/androidexample/MessagesSyncManager.java)文件里的username，password信息。
```java
private MessagesSyncManager() {
    //username，password替换成自己的信息
    String username="xxx@163.com";
    String password="xxxxxx";
    String hostname="imap.163.com";
    session = new IMAPSession();
    session.setUsername(username);
    session.setPassword(password);
    session.setHostname(hostname);
    session.setPort(993);
    session.setConnectionType(ConnectionType.ConnectionTypeTLS);
    IMAPIdentity imapIdentity = session.clientIdentity();
    imapIdentity.setName("jack");
    imapIdentity.setVendor("jack");
    imapIdentity.setVersion("0.0.1");
}
```    
    
