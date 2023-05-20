package com.kongpf8848.dmail.messagelist

import com.libmailcore.IMAPMessage
import android.widget.ArrayAdapter
import android.os.Bundle
import com.kongpf8848.dmail.mailcore.MailCore2Api
import com.kongpf8848.dmail.util.UCallback
import com.libmailcore.MailException
import android.app.Activity
import android.app.ListFragment
import android.util.Log
import android.view.View
import android.widget.ListView
import com.kongpf8848.dmail.util.Constants
import java.util.ArrayList

class MessageViewListFragment : ListFragment() {
    private var mCallbacks = sDummyCallbacks
    private var mActivatedPosition = ListView.INVALID_POSITION

    interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        fun onItemSelected(msg: IMAPMessage)
    }

    private var adapter: ArrayAdapter<MessageAdapter?>? = null
    private var messages: List<IMAPMessage>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MailCore2Api.instance.fetchMessages(
            Constants.INBOX,
            object : UCallback<List<IMAPMessage>, MailException> {
                override fun succeeded(imapMessages: List<IMAPMessage>) {
                    messages = imapMessages
                    updateResult()
                }

                override fun onFailed(e: MailException) {}
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION))
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        check(activity is Callbacks) { "Activity must implement fragment's callbacks." }
        mCallbacks = activity
    }

    override fun onDetach() {
        super.onDetach()
        mCallbacks = sDummyCallbacks
    }

    override fun onListItemClick(listView: ListView, view: View, position: Int, id: Long) {
        super.onListItemClick(listView, view, position, id)
        Log.d(
            TAG,
            "onListItemClick() called with: listView = [$listView], view = [$view], position = [$position], id = [$id]"
        )
        mCallbacks.onItemSelected(messages!![id.toInt()])
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition)
        }
    }

    private fun setActivatedPosition(position: Int) {
        if (position == ListView.INVALID_POSITION) {
            listView.setItemChecked(mActivatedPosition, false)
        } else {
            listView.setItemChecked(position, true)
        }
        mActivatedPosition = position
    }

    private fun updateResult() {
        Log.d(TAG, "update result")
        val array: ArrayList<MessageAdapter?> = ArrayList()
        for (msg in messages!!) {
            val msgAdapter = MessageAdapter(msg)
            array.add(msgAdapter)
        }
        adapter = ArrayAdapter(
            activity,
            android.R.layout.simple_list_item_activated_1,
            android.R.id.text1,
            array
        )
        listAdapter = adapter
    }

    companion object {
        private const val STATE_ACTIVATED_POSITION = "activated_position"
        private const val TAG = "MessageViewListFragment"
        private val sDummyCallbacks: Callbacks = object : Callbacks {
            override fun onItemSelected(msg: IMAPMessage) {}
        }
    }
}