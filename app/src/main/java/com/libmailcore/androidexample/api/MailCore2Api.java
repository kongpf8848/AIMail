package com.libmailcore.androidexample.api;

import android.util.Log;

import com.libmailcore.ConnectionType;
import com.libmailcore.IMAPFetchMessagesOperation;
import com.libmailcore.IMAPFetchParsedContentOperation;
import com.libmailcore.IMAPIdentity;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPMessagesRequestKind;
import com.libmailcore.IMAPOperation;
import com.libmailcore.IMAPSession;
import com.libmailcore.IMAPStoreFlagsRequestKind;
import com.libmailcore.IndexSet;
import com.libmailcore.MailException;
import com.libmailcore.MessageFlag;
import com.libmailcore.MessageParser;
import com.libmailcore.OperationCallback;
import com.libmailcore.Range;
import com.libmailcore.SMTPSession;
import com.libmailcore.androidexample.UCallback;
import com.libmailcore.androidexample.bean.MailInfo;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.BiFunction;

public class MailCore2Api {

    private static final String TAG = "MailCore2Api";

    private IMAPSession imapSession;
    public IMAPMessage currentMessage;

    private static MailCore2Api theSingleton;

    public static MailCore2Api singleton() {
        if (theSingleton == null) {
            synchronized (MailCore2Api.class) {
                if (theSingleton == null) {
                    theSingleton = new MailCore2Api();
                }
            }
        }
        return theSingleton;
    }

    private MailCore2Api() {
        //username，password替换成自己的信息
        String username = "xxxx@tom.com";
        String password = "xxxx";
        String hostname = "imap.tom.com";
        imapSession = new IMAPSession();
        imapSession.setUsername(username);
        imapSession.setPassword(password);
        imapSession.setHostname(hostname);
        imapSession.setPort(143);
        imapSession.setConnectionType(ConnectionType.ConnectionTypeClear);
        imapSession.setTimeout(15);
        imapSession.setCheckCertificateEnabled(false);
        IMAPIdentity imapIdentity = imapSession.clientIdentity();
        imapIdentity.setName("jack");
        imapIdentity.setVendor("jack");
        imapIdentity.setVersion("0.0.1");

    }

    /**
     * 标记已读/未读
     *
     * @param path
     * @param uid
     * @param b
     */
    public void setRead(String path, long uid, boolean b) {
        markMail(path, uid, b, MessageFlag.MessageFlagSeen);
    }

    /**
     * 设置红旗🚩邮件/非红旗邮件
     *
     * @param path
     * @param uid
     * @param b
     */
    public void setBanner(String path, long uid, boolean b) {
        markMail(path, uid, b, MessageFlag.MessageFlagFlagged);
    }

    private void markMail(String path, long uid, boolean b, int flag) {
        int kind = b ? IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindAdd : IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindRemove;
        IndexSet indexSet = new IndexSet();
        indexSet.addIndex(uid);
        IMAPOperation imapOperation = imapSession.storeFlagsByUIDOperation(path, indexSet, kind, flag);
        imapOperation.start(new OperationCallback() {
            @Override
            public void succeeded() {
                Log.d(TAG, "succeeded() called");
            }

            @Override
            public void failed(MailException e) {
                Log.d(TAG, "failed() called with: e = [" + e + "]");
            }
        });
    }

    /**
     * 获取邮件内容
     *
     * @param path
     * @param uid
     * @param callback
     */
    public void getMail(String path, long uid, UCallback<MailInfo, MailException> callback) {
        IMAPFetchParsedContentOperation operation = imapSession.fetchParsedMessageByUIDOperation(path, uid);
        operation.start(new OperationCallback() {
            @Override
            public void succeeded() {
                MessageParser parser = operation.parser();
                callback.succeeded(new MailInfo(parser.htmlRendering(), parser.htmlBodyRendering(), parser.data()));
            }

            @Override
            public void failed(MailException e) {
                callback.onFailed(e);
            }
        });
    }

    /**
     * 拉取邮件信息
     *
     * @param path
     * @param callback
     */
    public void fetchMessages(String path, UCallback<List<IMAPMessage>, MailException> callback) {
        IMAPFetchMessagesOperation op = imapSession.fetchMessagesByNumberOperation(path, IMAPMessagesRequestKind.IMAPMessagesRequestKindHeaders | IMAPMessagesRequestKind.IMAPMessagesRequestKindStructure, IndexSet.indexSetWithRange(new Range(1, Range.RangeMax)));
        op.start(new OperationCallback() {
            @Override
            public void succeeded() {
                callback.succeeded(op.messages());
            }

            @Override
            public void failed(MailException e) {
                callback.onFailed(e);
            }
        });
    }


}
