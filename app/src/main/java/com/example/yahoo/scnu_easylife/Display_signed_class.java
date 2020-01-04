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

public class Display_signed_class extends AppCompatActivity {
    MyHelper myhelper;
    TextView dis_collage;
    TextView dis_grade;
    TextView dis_course;
    TextView dis_teacher;
    TextView dis_class_location;
    TextView dis_able_sign_time;
    TextView dis_created_time;
    TextView tv_sign_time;
    TextView dis_signed_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_class);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.signed_class));
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
        tv_sign_time = findViewById(R.id.tv_sign_time);
        dis_signed_time = findViewById(R.id.dis_signed_time);

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("course",null,"course_id=?",
                new String[]{String.valueOf(course_id)},null,null,null);
        cursor.moveToFirst();
        dis_collage.setText(cursor.getString(1));
        dis_grade.setText(cursor.getString(2));
        dis_course.setText(cursor.getString(3));
        dis_teacher.setText(cursor.getString(4));
        dis_class_location.setText(cursor.getString(5));
        dis_able_sign_time.setText(cursor.getString(6)+getString(R.string.minute));
        dis_created_time.setText(cursor.getString(7));
        tv_sign_time.setText(getString(R.string.signed_time));
        dis_signed_time.setText(getIntent().getStringExtra("signed_time"));
        cursor.close();
        db.close();
        Button btn_sign = findViewById(R.id.btn_sign);
        btn_sign.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Display_signed_class.this,Signed_class.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
