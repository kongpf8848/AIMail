package com.kongpf8848.dmail;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.libmailcore.IMAPMessage;
import com.libmailcore.MailException;
import com.kongpf8848.dmail.mailcore.MailCore2Api;

public class MessageViewListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    private static final String TAG = "MessageViewListFragment";

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(IMAPMessage msg);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(IMAPMessage msg) {
        }
    };

    public MessageViewListFragment() {
    }

    private ArrayAdapter<MessageAdapter> adapter;
    private java.util.List<IMAPMessage> messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MailCore2Api.getInstance().fetchMessages(Constants.INBOX, new UCallback<List<IMAPMessage>, MailException>() {
            @Override
            public void succeeded(List<IMAPMessage> imapMessages) {
                messages = imapMessages;
                updateResult();
            }

            @Override
            public void onFailed(MailException e) {

            }
        });

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Log.d(TAG, "onListItemClick() called with: listView = [" + listView + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
        mCallbacks.onItemSelected(messages.get((int) id));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }


    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private void updateResult() {

        Log.d(TAG, "update result");
        ArrayList<MessageAdapter> array = new ArrayList();
        for(IMAPMessage msg : messages) {
            MessageAdapter msgAdapter = new MessageAdapter(msg);
            array.add(msgAdapter);
        }
        adapter = new ArrayAdapter<MessageAdapter>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                array);

        setListAdapter(adapter);

    }
}
