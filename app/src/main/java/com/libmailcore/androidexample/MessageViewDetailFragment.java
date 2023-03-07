package com.libmailcore.androidexample;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.util.Log;


import com.libmailcore.MailException;
import com.libmailcore.OperationCallback;
import com.libmailcore.IMAPMessage;
import com.libmailcore.IMAPMessageRenderingOperation;


public class MessageViewDetailFragment extends Fragment implements OperationCallback {

    public static final String ARG_ITEM_ID = "item_id";

    private static final String TAG = "MessageViewDetail";
    private IMAPMessage message;

    public MessageViewDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        message = MessagesSyncManager.singleton().currentMessage;
    }

    private IMAPMessageRenderingOperation renderingOp;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messageview_detail, container, false);
        webView = rootView.findViewById(R.id.messageview_detail);

        if (message != null) {
            Log.d(TAG, "message: " + message);
            renderingOp = MessagesSyncManager.singleton().imapSession.htmlRenderingOperation(message, "INBOX");
            renderingOp.start(this);
        }

        return rootView;
    }

    public void succeeded() {
        String html = renderingOp.result();
        Log.d(TAG, "body: " + html);
        webView.loadDataWithBaseURL(null,html, "text/html", "utf-8",null);
    }

    public void failed(MailException exception) {

    }
}
