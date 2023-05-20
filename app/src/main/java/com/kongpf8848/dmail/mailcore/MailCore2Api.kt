package com.kongpf8848.dmail.mailcore

import android.util.Log
import com.kongpf8848.dmail.bean.MailInfo
import com.kongpf8848.dmail.util.Constants
import com.kongpf8848.dmail.util.UCallback
import com.libmailcore.*
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

class MailCore2Api private constructor() {
    private var imapSession: IMAPSession? = null
    var currentMessage: IMAPMessage? = null
    fun setImapSession(imapSession: IMAPSession?) {
        this.imapSession = imapSession
    }

    /**
     * 标记已读/未读
     *
     * @param path
     * @param uid
     * @param b
     */
    fun setRead(path: String, uid: Long, b: Boolean) {
        markMail(path, uid, b, MessageFlag.MessageFlagSeen)
    }

    /**
     * 设置红旗🚩邮件/非红旗邮件
     *
     * @param path
     * @param uid
     * @param b
     */
    fun setBanner(path: String, uid: Long, b: Boolean) {
        markMail(path, uid, b, MessageFlag.MessageFlagFlagged)
    }

    private fun markMail(path: String, uid: Long, b: Boolean, flag: Int) {
        val kind =
            if (b) IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindAdd else IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindRemove
        val indexSet = IndexSet()
        indexSet.addIndex(uid)
        val imapOperation = imapSession!!.storeFlagsByUIDOperation(path, indexSet, kind, flag)
        imapOperation.start(object : OperationCallback {
            override fun succeeded() {
                Log.d(TAG, "succeeded() called")
            }

            override fun failed(e: MailException) {
                Log.d(TAG, "failed() called with: e = [$e]")
            }
        })
    }

    /**
     * 获取邮件内容
     *
     * @param path
     * @param uid
     * @param callback
     */
    fun getMessage(path: String?, uid: Long, callback: UCallback<MailInfo, MailException>) {
        val operation = imapSession!!.fetchParsedMessageByUIDOperation(path, uid)
        operation.start(object : OperationCallback {
            override fun succeeded() {
                val parser = operation.parser()
                callback.succeeded(
                    MailInfo(
                        parser.htmlRendering(),
                        parser.htmlBodyRendering(),
                        parser.data()
                    )
                )
            }

            override fun failed(e: MailException) {
                callback.onFailed(e)
            }
        })
    }

    /**
     * 拉取邮件信息
     *
     * @param path
     * @param callback
     */
    fun fetchMessages(path: String?, callback: UCallback<List<IMAPMessage>, MailException>) {
        val op = imapSession!!.fetchMessagesByNumberOperation(
            path,
            IMAPMessagesRequestKind.IMAPMessagesRequestKindHeaders or IMAPMessagesRequestKind.IMAPMessagesRequestKindStructure,
            IndexSet.indexSetWithRange(
                Range(1, Range.RangeMax)
            )
        )
        op.start(object : OperationCallback {
            override fun succeeded() {
                Log.d(TAG, "succeeded() called")
                callback.succeeded(op.messages())
            }

            override fun failed(e: MailException) {
                Log.d(TAG, "failed() called with: e = [$e]")
                callback.onFailed(e)
            }
        })
    }

    private fun getObservable(operation: Operation): Observable<String?> {
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<String?> ->
            operation.start(object : OperationCallback {
                override fun succeeded() {
                    Log.d(
                        TAG,
                        "succeeded() called:" + operation.javaClass.name + "," + Thread.currentThread().name
                    )
                    emitter.onNext("")
                    emitter.onComplete()
                }

                override fun failed(e: MailException) {
                    Log.d(TAG, "failed() called with: e = [$e]")
                    emitter.onError(e)
                }
            })
        })
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
    fun deleteMessage(path: String?, uid: Long) {
        val indexSet = IndexSet()
        indexSet.addIndex(uid)
        val copyOperation: Operation = imapSession!!.copyMessagesOperation(path, indexSet, "Trash")
        val storeOperation: Operation = imapSession!!.storeFlagsByUIDOperation(
            path,
            indexSet,
            IMAPStoreFlagsRequestKind.IMAPStoreFlagsRequestKindSet,
            MessageFlag.MessageFlagDeleted
        )
        val expungeOperation: Operation = imapSession!!.expungeOperation(path)
        Observable.fromArray(copyOperation, storeOperation, expungeOperation)
            .concatMap(Function<Operation, ObservableSource<*>> { operation: Operation ->
                getObservable(
                    operation
                )
            })
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe() called with: d = [$d]")
                }

                override fun onNext(o: Any) {
                    Log.d(TAG, "onNext() called with: o = [$o]")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError() called with: e = [$e]")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete() called")
                }
            })
    }

    fun syncMessage() {
        val requestKind =
            IMAPMessagesRequestKind.IMAPMessagesRequestKindFlags or IMAPMessagesRequestKind.IMAPMessagesRequestKindInternalDate or IMAPMessagesRequestKind.IMAPMessagesRequestKindFullHeaders or IMAPMessagesRequestKind.IMAPMessagesRequestKindExtraHeaders
        val indexSet = IndexSet()
        indexSet.addRange(Range(1, Int.MAX_VALUE.toLong()))
        val op = imapSession!!.syncMessagesByUIDOperation(Constants.INBOX, requestKind, indexSet, 0)
        op.start(object : OperationCallback {
            override fun succeeded() {
                val messages = op.messages()
                for (message in messages) {
                    Log.d(TAG, "succeeded() uid:" + message.uid())
                    Log.d(TAG, "succeeded() flags:" + message.flags())
                    Log.d(TAG, "succeeded() sequenceNumber:" + message.sequenceNumber())
                    Log.d(TAG, "succeeded() size:" + message.size())
                }
            }

            override fun failed(e: MailException) {
                Log.d(TAG, "failed() called with: e = [$e]")
            }
        })
    }

    companion object {
        private const val TAG = "MailCore2Api"
        val instance:MailCore2Api by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MailCore2Api()
        }
    }
}