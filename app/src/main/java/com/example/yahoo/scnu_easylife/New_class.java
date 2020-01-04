package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_class extends AppCompatActivity {
    MyHelper myhelper;
    Spinner cho_college;
    Spinner cho_grade;
    Spinner cho_course_name;
    EditText edt_teacher;
    EditText edt_class_location;
    Spinner cho_able_sign_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.new_class));
        }
        myhelper = new MyHelper(this);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_quit = findViewById(R.id.btn_quit);
        cho_college = findViewById(R.id.cho_college);
        cho_grade = findViewById(R.id.cho_grade);
        cho_course_name = findViewById(R.id.cho_course_name);
        edt_teacher = findViewById(R.id.edt_teacher);
        edt_class_location = findViewById(R.id.edt_class_location);
        cho_able_sign_time = findViewById(R.id.cho_able_sign_time);
        //确定
        btn_confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String college = cho_college.getSelectedItem().toString();
                String grade = cho_grade.getSelectedItem().toString();
                String course = cho_course_name.getSelectedItem().toString();
                String teacher = edt_teacher.getText().toString();
                String class_location = edt_class_location.getText().toString();
                String able_sign_time = cho_able_sign_time.getSelectedItem().toString();
                if(!TextUtils.isEmpty(college) && !TextUtils.isEmpty(grade) &&
                        !TextUtils.isEmpty(course) && !TextUtils.isEmpty(teacher) &&
                        !TextUtils.isEmpty(class_location) && !TextUtils.isEmpty(able_sign_time)){
                    ContentValues values = new ContentValues();
                    values.put("collage",college);
                    values.put("grade",grade);
                    values.put("course_name",course);
                    values.put("teacher",teacher);
                    values.put("class_location",class_location);
                    values.put("able_sign_time",able_sign_time);
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("create_time",String.valueOf(s_format.format(new Date())));//获取当前时间
                    values.put("creator_id",String.valueOf(MainActivity.user_id));
                    values.put("create_time_ms",String.valueOf(System.currentTimeMillis()));
                    myhelper.insert("course",values);
                    toastMessage(getString(R.string.create_success));
                    Intent intent=new Intent(New_class.this,Sign_in.class);
                    startActivity(intent);
                }else{
                    toastMessage(getString(R.string.no_empty));
                }
            }
        });
        //取消
        btn_quit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(New_class.this,Sign_in.class);
                startActivity(intent);
            }
        });
    }
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(New_class.this,Sign_in.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
