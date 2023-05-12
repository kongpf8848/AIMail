package com.kongpf8848.dmail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kongpf8848.dmail.R;
import com.kongpf8848.dmail.bean.MailConfig;
import com.libmailcore.ConnectionType;
import com.kongpf8848.dmail.bean.OAuthToken;
import com.kongpf8848.dmail.mailcore.MailCore2Api;
import com.kongpf8848.dmail.mailcore.SessionManager;
import com.kongpf8848.dmail.oauth.OAuthConfig;

public class LoginActivity extends OAuthActivity{

    private Button btn_gmail;
    private Button btn_outlook;
    private Button btn_yahoo;
    private Button btn_imap;
    private MailConfig imapConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_gmail=findViewById(R.id.btn_gmail);
        btn_outlook=findViewById(R.id.btn_outlook);
        btn_yahoo=findViewById(R.id.btn_yahoo);
        btn_imap=findViewById(R.id.btn_imap);
        btn_gmail.setOnClickListener(v -> {
            onClickGmail();
        });
        btn_outlook.setOnClickListener(v -> {
            onClickOutlook();
        });
        btn_yahoo.setOnClickListener(v -> {
            onClickYahoo();
        });
        btn_imap.setOnClickListener(v -> {
            onClickImap();
        });

    }

    @Override
    public void onOAuthTokenSuccess(String address, OAuthToken token) {
        MailCore2Api.getInstance().setImapSession(SessionManager.buildImapSession(address,null,imapConfig.host,imapConfig.port,imapConfig.connection_type,token));

        jumpToMessageList();
    }

    private void onClickGmail(){
        imapConfig=new MailConfig();
        imapConfig.host="imap.gmail.com";
        imapConfig.connection_type= ConnectionType.ConnectionTypeTLS;
        imapConfig.port=993;
        doAuth(OAuthConfig.GMAIL,null);
    }

    private void onClickOutlook(){
        imapConfig=new MailConfig();
        imapConfig.host="imap-mail.outlook.com";
        imapConfig.connection_type= ConnectionType.ConnectionTypeTLS;
        imapConfig.port=993;
        doAuth(OAuthConfig.OUTLOOK,null);
    }

    private void onClickYahoo(){
        imapConfig=new MailConfig();
        imapConfig.host="imap.mail.yahoo.com";
        imapConfig.connection_type= ConnectionType.ConnectionTypeTLS;
        imapConfig.port=993;
        doAuth(OAuthConfig.YAHOO,null);
    }

    private void onClickImap() {
        Intent intent=new Intent(this,IMAPLoginActivity.class);
        startActivity(intent);
    }

    private void jumpToMessageList(){
        Intent intent=new Intent(this,MessageViewListActivity.class);
        startActivity(intent);
    }
}
