package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Signed_class extends AppCompatActivity {
    MyHelper myhelper;
    List<Integer> course_id_category = new ArrayList<Integer>();
    List<String> course_category = new ArrayList<String>();
    List<String> signed_time_category = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.signed_class));
        }

        //读取课程表中的自己签到的课程信息
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor1 = db.query("signed_record",null,"sign_user_id=?",
                new String[]{String.valueOf(MainActivity.user_id)},null,null,null);
        if(cursor1.moveToLast()){
            do{
                String course_id = cursor1.getString(0);
                Cursor cursor2 = db.query("course",null,"course_id=?",
                        new String[]{course_id},null,null,null);
                cursor2.moveToFirst();
                course_id_category.add(cursor2.getInt(0));
                course_category.add(cursor2.getString(3));
                signed_time_category.add(cursor1.getString(2));
                cursor2.close();
            }while (cursor1.moveToPrevious());
        }
        cursor1.close();
        db.close();

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<course_category.size();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("course", "课程名："+course_category.get(i));
            hashMap.put("signed_time", "签到时间："+signed_time_category.get(i));
            list.add(hashMap);
        }

        //创建适配器
        // 第一个参数是上下文对象
        // 第二个是listitem
        // 第三个是指定每个列表项的布局文件
        // 第四个是指定Map对象中定义的两个键（这里通过字符串数组来指定）
        // 第五个是用于指定在布局文件中定义的id（也是用数组来指定）
        SimpleAdapter listAdpter = new SimpleAdapter(
                this,
                list,
                R.layout.fragment_list,
                new String[]{"course", "signed_time"},
                new int[]{R.id.tv_1, R.id.tv_2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Signed_class.this,Display_signed_class.class);
                intent.putExtra("course_id",course_id_category.get(position));
                intent.putExtra("signed_time",signed_time_category.get(position).trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Signed_class.this,Sign_in.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
