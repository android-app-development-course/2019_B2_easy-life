package com.example.yahoo.scnu_easylife;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;
import  android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends Fragment {
    MyHelper myhelper;
    List<Integer> chatter_id_category = new ArrayList<Integer>();
    List<String> chatter_nickname_category = new ArrayList<String>();
    List<String> detail_category = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notifications,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myhelper = new MyHelper(this.getActivity());

        initial();

        //返回页面时刷新
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    initial();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    private void initial() {
        chatter_id_category.clear();
        chatter_nickname_category.clear();
        detail_category.clear();

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor1 = db.query("chat_record",null,"send_id=? or receive_id=?",
                new String[]{String.valueOf(MainActivity.user_id),String.valueOf(MainActivity.user_id)},
                null,null,null);
        if(cursor1.moveToLast()){
            do{
                int chatter = 0;
                if(cursor1.getInt(0)==MainActivity.user_id){//当前用户为发送者
                    chatter = cursor1.getInt(1);
                }else{//当前用户为接收者
                    chatter = cursor1.getInt(0);
                }
                if (chatter_id_category.contains(chatter)){//聊天者已存入消息列表中
                    cursor1.moveToPrevious();
                    continue;
                }
                chatter_id_category.add(chatter);
                Cursor cursor2 = db.query("user_infomation",new String[]{"nickname"},"user_id=?",
                        new String[]{String.valueOf(chatter)},null,null,null);
                cursor2.moveToFirst();
                chatter_nickname_category.add(cursor2.getString(0));
                cursor2.close();
                detail_category.add(cursor1.getString(2));
            }while (cursor1.moveToPrevious());
        }
        cursor1.close();
        db.close();

        ListView mListView = (ListView) getActivity().findViewById(R.id.msg_list);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),MList.class);
                MList.chatter_id = String.valueOf(chatter_id_category.get(position));
                startActivityForResult(intent, 2);
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return chatter_id_category.size();
        }

        @Override
        public Object getItem(int position) {
            return chatter_id_category.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.item, null);
            TextView mTextView = (TextView) view.findViewById(R.id.notes_title);
            mTextView.setText(chatter_nickname_category.get(position));
            TextView nTextView = (TextView) view.findViewById(R.id.notes_details);
            nTextView.setText(detail_category.get(position));
            return view;
        }
    }
}
