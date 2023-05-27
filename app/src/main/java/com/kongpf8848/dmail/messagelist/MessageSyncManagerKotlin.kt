package com.kongpf8848.dmail.messagelist

import android.annotation.SuppressLint
import android.util.Log
import com.libmailcore.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

object MessageSyncManagerKotlin {
    private const val TAG = "MessageSyncManager"
    private const val messageRequestKind =
        IMAPMessagesRequestKind.IMAPMessagesRequestKindFlags or IMAPMessagesRequestKind.IMAPMessagesRequestKindInternalDate or IMAPMessagesRequestKind.IMAPMessagesRequestKindFullHeaders or IMAPMessagesRequestKind.IMAPMessagesRequestKindExtraHeaders

    private fun getObservable(operation: IMAPSearchOperation) =
        Observable.create { emitter: ObservableEmitter<IndexSet> ->
            operation.start(object : OperationCallback {
                override fun succeeded() {
                    emitter.onNext(operation.uids())
                    emitter.onComplete()
                }

                override fun failed(e: MailException) {
                    emitter.onError(e)
                }
            })
        }.subscribeOn(AndroidSchedulers.mainThread())

    private fun getObservable(operation: IMAPFetchMessagesOperation) =
        Observable.create { emitter: ObservableEmitter<List<IMAPMessage>> ->
            Log.d(TAG, "getObservable()22 called with: thread = ${Thread.currentThread().name}")
            operation.start(object : OperationCallback {
                override fun succeeded() {
                    emitter.onNext(operation.messages())
                    emitter.onComplete()
                }

                override fun failed(e: MailException) {
                    emitter.onError(e)
                }
            })
        }.subscribeOn(AndroidSchedulers.mainThread())

    /**
     * 只同步收件箱未读邮件
     */
    @SuppressLint("CheckResult")
    fun syncUnReadMessages(imapSession: IMAPSession, path: String) {
        Log.d(TAG, "syncUnReadMessages() called with: imapSession = $imapSession, path = $path")
        val searchOperation = imapSession.searchOperation(path, IMAPSearchExpression.searchUnread())
        getObservable(searchOperation).flatMap { indexSet ->
            Log.d(TAG, "syncUnReadMessages()11 called with: thread:"+Thread.currentThread().name)
            if (indexSet.count() > 0) {
                val set = TreeSet<Long>()
                var newSet=IndexSet()
                indexSet.allRanges().forEach {
                    for (i in it.leftBound() until it.leftBound() + it.length + 1) {
                        set.add(i)
                        newSet.addIndex(i)
                    }
                }
                set.forEach {
                    Log.d(TAG, "syncUnReadMessages() called,uid:${it}")
                }
                getObservable(
                    imapSession.fetchMessagesByUIDOperation(
                        path,
                        messageRequestKind,
                        indexSet
                    ).apply {
                        //setExtraHeaders(listOf(Constants.MESSAGE_HEADER_STYLE))
                    })
            } else {
                Observable.just(emptyList())
            }
        }.subscribeOn(Schedulers.io()).map{
            Log.d(TAG, "syncUnReadMessages()22 map:"+Thread.currentThread().name)
            if (it.isNotEmpty()) {
                for(message in it){
                    Log.d(TAG, "syncUnReadMessages() message:${message.uid()},${message.flags()},${message.header().from()}")
                }
                //MailHeadersDaoUtil.getInstance().insertMailHeaders(it, folder)
            }
            ""
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG, "syncUnReadMessages() subscribe:"+Thread.currentThread().name)
                },
                {
                    Log.d(TAG, "syncUnReadMessages() called:"+Thread.currentThread().name)
                })
    }
}