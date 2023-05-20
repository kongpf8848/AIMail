package com.kongpf8848.dmail.messagelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kongpf8848.dmail.R
import com.kongpf8848.dmail.bean.MailInfo
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.util.Constants
import com.kongpf8848.dmail.util.UCallback
import com.kongpf8848.dmail.util.Utils.writeFile
import com.libmailcore.IMAPMessage
import com.libmailcore.MailException
import kotlinx.android.synthetic.main.fragment_messageview_detail.*
import java.nio.charset.StandardCharsets

class MessageViewDetailFragment : Fragment(), View.OnClickListener {
    private var message: IMAPMessage? = null
    private var mailInfo: MailInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = MailCore2Api.instance.currentMessage
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_messageview_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_unread.setOnClickListener(this)
        btn_banner.setOnClickListener(this)
        btn_save_eml.setOnClickListener(this)
        btn_delete.setOnClickListener(this)
        if (message != null) {
            MailCore2Api.instance.getMessage(
                Constants.INBOX,
                message!!.uid(),
                object : UCallback<MailInfo, MailException> {
                    override fun succeeded(mailInfo: MailInfo) {
                        this@MessageViewDetailFragment.mailInfo = mailInfo
                        Log.d(TAG, "html: " + mailInfo.html)
                        Log.d(
                            TAG,
                            "origin: " + String(mailInfo.origin_data, StandardCharsets.UTF_8)
                        )
                        webView!!.loadDataWithBaseURL(
                            null,
                            mailInfo.html,
                            "text/html",
                            "utf-8",
                            null
                        )
                    }

                    override fun onFailed(e: MailException) {
                        Log.d(TAG, "failed() called with: exception = [$e]")
                    }
                })
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_unread -> action_read(false)
            R.id.btn_banner -> action_banner(true)
            R.id.btn_save_eml -> action_save_eml()
            R.id.btn_delete -> action_delete()
            else -> {}
        }
    }

    private fun action_read(b: Boolean) {
        MailCore2Api.instance.setRead(Constants.INBOX, message!!.uid(), b)
    }

    private fun action_banner(b: Boolean) {
        MailCore2Api.instance.setBanner(Constants.INBOX, message!!.uid(), b)
    }

    private fun action_save_eml() {
        if (mailInfo != null) {
            writeFile(requireActivity().cacheDir.absolutePath + "/" + message!!.uid() + ".eml",
                mailInfo!!.origin_data
            )
        }
    }

    private fun action_delete() {
        //MailCore2Api.getInstance().deleteMessage(Constants.INBOX,message.uid());
        MailCore2Api.instance.syncMessage()
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
        private const val TAG = "MessageViewDetail"
    }
}