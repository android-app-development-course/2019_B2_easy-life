package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MList extends AppCompatActivity {
    MyHelper myhelper;
    private java.util.List<Msg> msgList = new ArrayList<>();
    private EditText input_text;
    private RecyclerView mRecyclerView;
    private MsgAdapter adapter;
    public static String chatter_id = "0";
    String cur_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        myhelper = new MyHelper(this);

        cur_id = String.valueOf(MainActivity.user_id);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            SQLiteDatabase db;
            db = myhelper.getReadableDatabase();
            Cursor cursor = db.query("user_infomation",new String[]{"nickname"},"user_id=?",
                    new String[]{String.valueOf(chatter_id)},null,null,null);
            cursor.moveToFirst();
            actionBar.setTitle(cursor.getString(0));
            cursor.close();
            db.close();
        }

        //初始化消息数据
        initMsg();
        input_text = findViewById(R.id.input);
        Button bt_send = findViewById(R.id.send);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgList);
        mRecyclerView.scrollToPosition(adapter.getItemCount()-1);
        mRecyclerView.setAdapter(adapter);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = input_text.getText().toString();
                if (!"".equals(content)){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    //当有新消息时，调用notifyItemInserted方法刷新listview中的显示
                    adapter.notifyItemInserted(msgList.size()-1);
                    //将listview定位到最后一行
                    mRecyclerView.scrollToPosition(msgList.size()-1);
                    //新消息显示在listview中 清空输入框中的内容
                    input_text.setText("");
                    //发送者将聊天记录存入数据库
                    ContentValues values = new ContentValues();
                    values.put("send_id",cur_id);
                    values.put("receive_id",chatter_id);
                    values.put("content",content);
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("send_time",String.valueOf(s_format.format(new Date())));//获取当前时间
                    myhelper.insert("chat_record",values);
                }
            }
        });
    }

    private void initMsg() {
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("chat_record",null,
                "(send_id=? and receive_id=?) or (send_id=? and receive_id=?)",
                new String[]{cur_id,chatter_id,chatter_id,cur_id},
                null,null,null);
        if(cursor.moveToFirst()){
            do{
                Msg msg = null;
                if(cursor.getString(0).equals(cur_id)){//当前用户为发送者
                    msg = new Msg(cursor.getString(2), Msg.TYPE_SEND);
                }else{//当前用户为接收者
                    msg = new Msg(cursor.getString(2), Msg.TYPE_RECEIVED);
                }
                msgList.add(msg);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(MList.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
