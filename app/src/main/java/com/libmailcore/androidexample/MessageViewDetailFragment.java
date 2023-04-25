package com.libmailcore.androidexample;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.libmailcore.IMAPMessage;
import com.libmailcore.MailException;
import com.libmailcore.androidexample.api.MailCore2Api;
import com.libmailcore.androidexample.bean.MailInfo;

import java.nio.charset.StandardCharsets;


public class MessageViewDetailFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_ITEM_ID = "item_id";

    private static final String TAG = "MessageViewDetail";
    private IMAPMessage message;
    private MailInfo mailInfo;
    private WebView webView;
    private Button btn_unread;
    private Button btn_banner;
    private Button btn_save_eml;
    private Button btn_delete;

    public MessageViewDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = MailCore2Api.getInstance().currentMessage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messageview_detail, container, false);
        webView = rootView.findViewById(R.id.messageview_detail);
        btn_unread=rootView.findViewById(R.id.btn_unread);
        btn_banner=rootView.findViewById(R.id.btn_banner);
        btn_save_eml=rootView.findViewById(R.id.btn_save_eml);
        btn_delete=rootView.findViewById(R.id.btn_delete);

        btn_unread.setOnClickListener(this);
        btn_banner.setOnClickListener(this);
        btn_save_eml.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (message != null) {
             MailCore2Api.getInstance().getMessage(Constants.INBOX, message.uid(), new UCallback<MailInfo, MailException>() {
                @Override
                public void succeeded(MailInfo mailInfo) {
                    MessageViewDetailFragment.this.mailInfo=mailInfo;
                    Log.d(TAG, "html: " + mailInfo.html);
                    Log.d(TAG, "origin: " + new String(mailInfo.origin_data, StandardCharsets.UTF_8));
                    webView.loadDataWithBaseURL(null,mailInfo.html, "text/html", "utf-8",null);
                }

                @Override
                public void onFailed(MailException e) {
                    Log.d(TAG, "failed() called with: exception = [" + e + "]");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_unread:
                action_read(false);
                break;
            case R.id.btn_banner:
                action_banner(true);
                break;
            case R.id.btn_save_eml:
                action_save_eml();
                break;
            case R.id.btn_delete:
                action_delete();
                break;
            default:
                break;
        }
    }

    private void action_read(boolean b){
        MailCore2Api.getInstance().setRead(Constants.INBOX,message.uid(),b);
    }

    private void action_banner(boolean b){
        MailCore2Api.getInstance().setBanner(Constants.INBOX,message.uid(),b);
    }

    private void action_save_eml(){
      if(mailInfo!=null){
         Utils.writeFile(getActivity().getCacheDir().getAbsolutePath()+"/"+message.uid()+".eml",mailInfo.origin_data);
      }

    }

    private void action_delete(){
        //MailCore2Api.getInstance().deleteMessage(Constants.INBOX,message.uid());
        MailCore2Api.getInstance().syncMessage();
    }


}
