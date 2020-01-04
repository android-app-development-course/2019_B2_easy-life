package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class My_profile extends AppCompatActivity {
    MyHelper myhelper;
    EditText edt_user_id;
    EditText edt_nickname;
    EditText edt_realname;
    int state = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        myhelper = new MyHelper(this);
        edt_user_id = findViewById(R.id.edt_user_id);
        edt_nickname = findViewById(R.id.edt_nickname);
        edt_realname = findViewById(R.id.edt_realname);
        final Button btn_modify_profile = findViewById(R.id.btn_modify_profile);

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("user_infomation",null,"user_id=?",
                new String[] {String.valueOf(MainActivity.user_id)},null,null,null);
        if(cursor.moveToFirst()){
            edt_user_id.setText(cursor.getString(0));
            edt_nickname.setText(cursor.getString(1));
            edt_realname.setText(cursor.getString(2));
        }
        cursor.close();
        db.close();

        btn_modify_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1){//编辑资料
                    edt_nickname.setFocusable(true);
                    edt_nickname.setFocusableInTouchMode(true);
                    edt_realname.setFocusable(true);
                    edt_realname.setFocusableInTouchMode(true);
                    btn_modify_profile.setText(getString(R.string.save));
                    state = 2;
                }else{//保存
                    SQLiteDatabase db;
                    db = myhelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("nickname",edt_nickname.getText().toString());
                    values.put("realname",edt_realname.getText().toString());
                    db.update("user_infomation",values,"user_id=?",
                            new String[]{String.valueOf(MainActivity.user_id)});
                    MainActivity.nickname = edt_nickname.getText().toString();
                    MainActivity.realname = edt_realname.getText().toString();
                    db.close();
                    edt_nickname.setFocusable(false);
                    edt_nickname.setFocusableInTouchMode(false);
                    edt_realname.setFocusable(false);
                    edt_realname.setFocusableInTouchMode(false);
                    toastMessage(getString(R.string.save_success));
                    btn_modify_profile.setText(getString(R.string.modify_profile));
                    state = 1;
                }
            }
        });
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(My_profile.this).sendBroadcast(intent);
                sendBroadcast(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
