package com.libmailcore.androidexample;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.util.Log;


import com.libmailcore.IMAPFetchParsedContentOperation;
import com.libmailcore.MailException;
import com.libmailcore.MessageParser;
import com.libmailcore.OperationCallback;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPMessageRenderingOperation;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class MessageViewDetailFragment extends Fragment implements OperationCallback {

    public static final String ARG_ITEM_ID = "item_id";

    private static final String TAG = "MessageViewDetail";
    private IMAPMessage message;
    private IMAPFetchParsedContentOperation fetchParsedContentOperation;

    public MessageViewDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = MessagesSyncManager.singleton().currentMessage;
    }

    private IMAPFetchParsedContentOperation renderingOp;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messageview_detail, container, false);
        webView = rootView.findViewById(R.id.messageview_detail);

        if (message != null) {
            renderingOp =  MessagesSyncManager.singleton().imapSession.fetchParsedMessageByUIDOperation("INBOX",message.uid());
            renderingOp.start(this);
        }

        return rootView;
    }

    public void succeeded() {
        String html = renderingOp.parser().htmlRendering();
        Log.d(TAG, "body: " + html);
        webView.loadDataWithBaseURL(null,html, "text/html", "utf-8",null);
    }

    public void failed(MailException exception) {

    }
}
