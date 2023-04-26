package com.kongpf8848.dmail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kongpf8848.dmail.R;
import com.libmailcore.ConnectionType;
import com.kongpf8848.dmail.bean.OAuthToken;
import com.kongpf8848.dmail.mailcore.MailCore2Api;
import com.kongpf8848.dmail.mailcore.SessionManager;
import com.kongpf8848.dmail.oauth.OAuthConfig;

public class LoginActivity extends OAuthActivity{

    private Button btn_outlook;
    private Button btn_imap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_outlook=findViewById(R.id.btn_outlook);
        btn_imap=findViewById(R.id.btn_imap);
        btn_outlook.setOnClickListener(v -> {
            onClickOutlook();
        });
        btn_imap.setOnClickListener(v -> {
            onClickImap();
        });

    }

    @Override
    public void onOAuthTokenSuccess(String address, OAuthToken token) {
        String username=address;
        String host="imap-mail.outlook.com";
        int connectType= ConnectionType.ConnectionTypeTLS;
        int port=993;
        MailCore2Api.getInstance().setImapSession(SessionManager.buildImapSession(username,null,host,port,connectType,token));

        jumpToMessageList();
    }

    private void onClickOutlook(){
        doAuth(OAuthConfig.OUTLOOK,null);
    }

    private void onClickImap() {
        String username="xxxx@163.com";
        String password="xxxx";
        String host="imap.163.com";
        int connectType= ConnectionType.ConnectionTypeTLS;
        int port=993;
        MailCore2Api.getInstance().setImapSession(SessionManager.buildImapSession(username,password,host,port,connectType,null));

        jumpToMessageList();
    }

    private void jumpToMessageList(){
        Intent intent=new Intent(this,MessageViewListActivity.class);
        startActivity(intent);
    }
}
