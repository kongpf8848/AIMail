package com.libmailcore.androidexample.mailcore;

import android.util.Log;

import com.libmailcore.IMAPFetchMessagesOperation;
import com.libmailcore.IMAPFetchParsedContentOperation;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPMessagesRequestKind;
import com.libmailcore.IMAPOperation;
import com.libmailcore.IMAPSession;
import com.libmailcore.IMAPStoreFlagsRequestKind;
import com.libmailcore.IndexSet;
import com.libmailcore.MailException;
import com.libmailcore.MessageFlag;
import com.libmailcore.MessageParser;
import com.libmailcore.Operation;
import com.libmailcore.OperationCallback;
import com.libmailcore.Range;
import com.libmailcore.androidexample.Constants;
import com.libmailcore.androidexample.UCallback;
import com.libmailcore.androidexample.bean.MailInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MailCore2Api {

    private static final String TAG = "MailCore2Api";

    private IMAPSession imapSession;
    public IMAPMessage currentMessage;

    private static MailCore2Api instance;

    public static MailCore2Api getInstance() {
        if (instance == null) {
            synchronized (MailCore2Api.class) {
                if (instance == null) {
                    instance = new MailCore2Api();
                }
            }
        }
        return instance;
    }

    private MailCore2Api() {
    }

    public void setImapSession(IMAPSession imapSession){
        this.imapSession=imapSession;
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
    public void getMessage(String path, long uid, UCallback<MailInfo, MailException> callback) {
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
                Log.d(TAG, "succeeded() called");
                callback.succeeded(op.messages());
            }

            @Override
            public void failed(MailException e) {
                Log.d(TAG, "failed() called with: e = [" + e + "]");
                callback.onFailed(e);
            }
        });
    }


    private Observable<String> getObservable(Operation operation) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            operation.start(new OperationCallback() {
                @Override
                public void succeeded() {
                    Log.d(TAG, "succeeded() called:" + operation.getClass().getName() + "," + Thread.currentThread().getName());
                    emitter.onNext("");
                    emitter.onComplete();
                }

                @Override
                public void failed(MailException e) {
                    Log.d(TAG, "failed() called with: e = [" + e + "]");
                    emitter.onError(e);
                }
            });
        });
    }

    /**
     * 删除邮件
     * 1.复制邮件到已删除邮件
     * 2.将邮件标记为已删除
     * 3.清空文件夹中标记为已删除的邮件
     *
     * @param path
     * @param uid
     */
    public void deleteMessage(String path, long uid) {

        IndexSet indexSet = new IndexSet();
        indexSet.addIndex(uid);

        Operation copyOperation = imapSession.copyMessagesOperation(path, indexSet, "Trash");
        Operation storeOperation = imapSession.storeFlagsByUIDOperation(path, indexSet, IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindSet, MessageFlag.MessageFlagDeleted);
        Operation expungeOperation = imapSession.expungeOperation(path);

        Observable.fromArray(copyOperation, storeOperation, expungeOperation)
                .concatMap((Function<Operation, ObservableSource<?>>) this::getObservable)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "onNext() called with: o = [" + o + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete() called");
                    }
                });
    }

    public void syncMessage(){
        int requestKind = IMAPMessagesRequestKind.IMAPMessagesRequestKindFlags | IMAPMessagesRequestKind.IMAPMessagesRequestKindInternalDate | IMAPMessagesRequestKind.IMAPMessagesRequestKindFullHeaders|IMAPMessagesRequestKind.IMAPMessagesRequestKindExtraHeaders;
        IndexSet indexSet=new IndexSet();
        indexSet.addRange(new Range(1,Integer.MAX_VALUE));
        IMAPFetchMessagesOperation op = imapSession.syncMessagesByUIDOperation(Constants.INBOX, requestKind, indexSet, 0);
        op.start(new OperationCallback() {
            @Override
            public void succeeded() {
                List<IMAPMessage> messages = op.messages();
                for(IMAPMessage message:messages){
                    Log.d(TAG, "succeeded() uid:"+message.uid());
                    Log.d(TAG, "succeeded() flags:"+message.flags());
                    Log.d(TAG, "succeeded() sequenceNumber:"+message.sequenceNumber());
                    Log.d(TAG, "succeeded() size:"+message.size());
                }
            }

            @Override
            public void failed(MailException e) {
                Log.d(TAG, "failed() called with: e = [" + e + "]");
            }
        });
    }

}
