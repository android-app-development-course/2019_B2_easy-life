package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Display_created_commission extends AppCompatActivity {
    MyHelper myhelper;
    TextView dis_commission_title;
    TextView dis_commission_content;
    TextView dis_commission_position;
    TextView dis_comission_reward;
    TextView dis_publish_time;
    TextView tv_creator_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_commission);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.created_commission));
        }
        Integer commission_id = getIntent().getIntExtra("commission_id",0);
        dis_commission_title = findViewById(R.id.dis_commission_title);
        dis_commission_content = findViewById(R.id.dis_commission_content);
        dis_commission_position = findViewById(R.id.dis_commission_position);
        dis_comission_reward = findViewById(R.id.dis_comission_reward);
        dis_publish_time = findViewById(R.id.dis_publish_time);
        tv_creator_nickname = findViewById(R.id.tv_creator_nickname);
        Button btn_contact_consignor = findViewById(R.id.btn_contact_consignor);
        tv_creator_nickname.setText("");
        btn_contact_consignor.setVisibility(View.INVISIBLE);

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("commission",null,"commission_id=?",
                new String[]{String.valueOf(commission_id)},null,null,null);
        cursor.moveToFirst();
        dis_commission_title.setText(cursor.getString(1));
        dis_commission_content.setText(cursor.getString(2));
        dis_commission_position.setText(cursor.getString(3));
        dis_comission_reward.setText(cursor.getString(4));
        dis_publish_time.setText(cursor.getString(5));
        cursor.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Display_created_commission.this,Created_commission.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
