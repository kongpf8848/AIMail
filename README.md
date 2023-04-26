# MailCore2Example
演示使用[MailCore2](https://github.com/MailCore/mailcore2)类库拉取邮件和查看邮件详情的Demo。
# 说明
mailcore2-android-4.aar非官方版本，经过修改和重新打包。

# 使用

## Outlook邮箱
替换以下代码中的address信息：
```java
private void onClickOutlook(){
    String address="xxx@outlook.com";
    doAuth(OAuthConfig.OUTLOOK,address);
}
```
## 163/QQ等需要使用密码/授权码登录的邮箱
替换以下代码中的username,password等信息
```java
private void onClickImap() {
    String username="xxxx@163.com";
    String password="xxxx";
    String host="imap.163.com";
    int connectType= ConnectionType.ConnectionTypeTLS;
    int port=993;
    MailCore2Api.getInstance().setImapSession(SessionManager.buildImapSession(username,password,host,port,connectType,null));

    jumpToMessageList();
}
```


    
