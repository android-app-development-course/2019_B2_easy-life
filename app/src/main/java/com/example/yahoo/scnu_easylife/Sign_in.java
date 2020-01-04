package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Sign_in extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.sign_in));
        }

        Button btn_new_class = findViewById(R.id.btn_new_class);
        Button btn_cur_class = findViewById(R.id.btn_cur_class);
        Button btn_created_class = findViewById(R.id.btn_created_class);
        Button btn_signed_class = findViewById(R.id.btn_signed_class);
        //创建课程
        btn_new_class.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this,New_class.class);
                startActivity(intent);
            }
        });
        //签到
        btn_cur_class.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this,Cur_class.class);
                startActivity(intent);
            }
        });
        //已创建的课程
        btn_created_class.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this,Created_class.class);
                startActivity(intent);
            }
        });
        //已签到的课程
        btn_signed_class.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this,Signed_class.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //this.finish(); // back button
                Intent intent=new Intent(Sign_in.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}