package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Display_cur_class extends AppCompatActivity {
    MyHelper myhelper;
    boolean issign = false;
    boolean ablesign = true;
    TextView dis_collage;
    TextView dis_grade;
    TextView dis_course;
    TextView dis_teacher;
    TextView dis_class_location;
    TextView dis_able_sign_time;
    TextView dis_created_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_class);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.cur_class));
        }
        myhelper = new MyHelper(this);
        final int course_id = getIntent().getIntExtra("course_id",0);
        dis_collage = findViewById(R.id.dis_college);
        dis_grade = findViewById(R.id.dis_grade);
        dis_course = findViewById(R.id.dis_course);
        dis_teacher = findViewById(R.id.dis_teacher);
        dis_class_location = findViewById(R.id.dis_class_location);
        dis_able_sign_time = findViewById(R.id.dis_able_sign_time);
        dis_created_time = findViewById(R.id.dis_created_time);

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("course",null,"course_id=?",
                new String[]{String.valueOf(course_id)},null,null,null);
        cursor.moveToFirst();
        int able_sign_time = cursor.getInt(6);
        Long created_time_ms = cursor.getLong(9);
        dis_collage.setText(cursor.getString(1));
        dis_grade.setText(cursor.getString(2));
        dis_course.setText(cursor.getString(3));
        dis_teacher.setText(cursor.getString(4));
        dis_class_location.setText(cursor.getString(5));
        dis_able_sign_time.setText(cursor.getString(6));
        dis_created_time.setText(cursor.getString(7));
        cursor.close();

        //判断是否已签到
        Cursor cursor1 = db.query("signed_record",null,"course_id=? and sign_user_id=?",
                new String[]{String.valueOf(course_id),String.valueOf(MainActivity.user_id)},
                null,null,null);
        if(cursor1.getCount()==1) issign = true;
        cursor1.close();

        //判断是否已超过可签到时间
        Long sign_time = System.currentTimeMillis();
        if(sign_time-created_time_ms > able_sign_time*60*1000) ablesign=false;

        db.close();

        final Button btn_sign = findViewById(R.id.btn_sign);
        if(issign){
            btn_sign.setText(getString(R.string.already_sign));
            btn_sign.setBackground(getDrawable(R.drawable.round1));
        }else if (!ablesign){
            btn_sign.setText(getString(R.string.beyond_time));
            btn_sign.setBackground(getDrawable(R.drawable.round1));
        }
        else{
            //签到
            btn_sign.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    values.put("course_id",String.valueOf(course_id));
                    values.put("sign_user_id",String.valueOf(MainActivity.user_id));
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("sign_time_ms",s_format.format(new Date()));//获取当前时间
                    myhelper.insert("signed_record",values);
                    toastMessage(getString(R.string.sign_success));
                    btn_sign.setText(getString(R.string.already_sign));
                    btn_sign.setBackground(getDrawable(R.drawable.round1));
                }
            });
        }
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Display_cur_class.this,Cur_class.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
