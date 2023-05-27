package com.kongpf8848.dmail.messagelist;

import android.util.Log;

import com.kongpf8848.dmail.util.Constants;
import com.libmailcore.IMAPFetchMessagesOperation;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPMessagesRequestKind;
import com.libmailcore.IMAPSearchExpression;
import com.libmailcore.IMAPSearchOperation;
import com.libmailcore.IMAPSession;
import com.libmailcore.IndexSet;
import com.libmailcore.MailException;
import com.libmailcore.OperationCallback;
import com.libmailcore.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class MessageSyncManager {

    private static final String TAG = "MessageSyncManager";

    public static void syncUnReadMessages(IMAPSession session, String path) {
        IMAPSearchOperation searchOperation = session.searchOperation(path, IMAPSearchExpression.searchUnread());
        searchOperation.start(new OperationCallback() {
            @Override
            public void succeeded() {
                IndexSet indexSet = searchOperation.uids();
                IndexSet noHeaderIndexSet = new IndexSet();
                if (indexSet.count() > 0) {
                    Set<Long> set = new TreeSet<Long>();
                    indexSet.allRanges().forEach(range -> {
                        long left = range.leftBound();
                        long right = range.leftBound() + range.length + 1;
                        for (long l = left; l < right; l++) {
                            set.add(l);
                            noHeaderIndexSet.addIndex(l);
                        }
                    });
                    set.forEach(i -> {
                        Log.d(TAG, "searchOperation() called,uid:${it}");
                    });
                }
                if (noHeaderIndexSet.count() > 0) {
                    IMAPFetchMessagesOperation fetchMessagesOperation = session.fetchMessagesByUIDOperation(
                            path,
                            IMAPMessagesRequestKind.IMAPMessagesRequestKindFlags | IMAPMessagesRequestKind.IMAPMessagesRequestKindInternalDate | IMAPMessagesRequestKind.IMAPMessagesRequestKindFullHeaders | IMAPMessagesRequestKind.IMAPMessagesRequestKindExtraHeaders,
                            noHeaderIndexSet
                    );
                    fetchMessagesOperation.start(new OperationCallback() {
                        @Override
                        public void succeeded() {
                            List<IMAPMessage> messageList = fetchMessagesOperation.messages();
                            for (IMAPMessage message : messageList) {
                                Log.d(TAG, "syncUnReadMessages() message:" + message.uid() + "," + message.header().from().mailbox());
                            }
                        }

                        @Override
                        public void failed(MailException e) {

                        }
                    });
                }
            }

            @Override
            public void failed(MailException e) {
                Log.d(TAG, "failed() called with: e = [" + e + "]");
            }
        });
    }
}

