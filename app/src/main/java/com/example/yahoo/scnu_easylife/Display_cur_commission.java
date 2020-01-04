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

public class Display_cur_commission extends AppCompatActivity {
    MyHelper myhelper;
    TextView dis_commission_title;
    TextView dis_commission_content;
    TextView dis_commission_position;
    TextView dis_comission_reward;
    TextView dis_publish_time;
    TextView dis_creator_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_commission);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.cur_commission));
        }
        final Integer commission_id = getIntent().getIntExtra("commission_id",0);
        dis_commission_title = findViewById(R.id.dis_commission_title);
        dis_commission_content = findViewById(R.id.dis_commission_content);
        dis_commission_position = findViewById(R.id.dis_commission_position);
        dis_comission_reward = findViewById(R.id.dis_comission_reward);
        dis_publish_time = findViewById(R.id.dis_publish_time);
        dis_creator_nickname = findViewById(R.id.dis_creator_nickname);

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
        dis_creator_nickname.setText(cursor.getString(6));
        final int creator_id = cursor.getInt(7);
        cursor.close();
        db.close();

        //联系委托者
        Button btn_contact_consignor = (Button)findViewById(R.id.btn_contact_consignor);
        if(creator_id==MainActivity.user_id) btn_contact_consignor.setVisibility(View.INVISIBLE);
        btn_contact_consignor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(Display_cur_commission.this,MList.class);
                MList.chatter_id = String.valueOf(creator_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Display_cur_commission.this,Cur_commission.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
