package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Lost_found extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.lost_found));
        }

        Button btn_new_lost = findViewById(R.id.btn_new_lost);
        Button btn_new_search_notice = findViewById(R.id.btn_new_search_notice);
        Button btn_cur_lost_found = findViewById(R.id.btn_cur_lost_found);
        Button btn_created_lost_found = findViewById(R.id.btn_created_lost_found);
        //发布失物招领
        btn_new_lost.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lost_found.this,New_lost.class);
                startActivity(intent);
            }
        });
        //发布寻物启示
        btn_new_search_notice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lost_found.this,New_search_notice.class);
                startActivity(intent);
            }
        });
        //寻找失主/失物
        btn_cur_lost_found.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lost_found.this,Cur_lost_found.class);
                startActivity(intent);
            }
        });
        //我的发布
        btn_created_lost_found.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lost_found.this,Created_lost_found.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Lost_found.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
