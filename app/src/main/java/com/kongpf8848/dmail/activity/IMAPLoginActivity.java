package com.kongpf8848.dmail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kongpf8848.dmail.DomainUtils;
import com.kongpf8848.dmail.R;
import com.kongpf8848.dmail.mailcore.MailCore2Api;
import com.kongpf8848.dmail.mailcore.SessionManager;
import com.libmailcore.ConnectionType;

public class IMAPLoginActivity extends BaseActivity{

    private EditText et_username;
    private EditText et_password;
    private TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_imap);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        tv_login=findViewById(R.id.tv_login);
        tv_login.setOnClickListener(v -> {
            onClickLogin();
        });
    }

    private void onClickLogin(){
        String username=et_username.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            return;
        }

        String domain= DomainUtils.getDomain(username);
        if(TextUtils.isEmpty(domain)){
            return;
        }
        String host="imap."+domain;
        int connectType= ConnectionType.ConnectionTypeTLS;
        int port=993;

        MailCore2Api.getInstance().setImapSession(SessionManager.buildImapSession(username,password,host,port,connectType,null));

        Intent intent=new Intent(this,MessageViewListActivity.class);
        startActivity(intent);
        finish();
    }
}
