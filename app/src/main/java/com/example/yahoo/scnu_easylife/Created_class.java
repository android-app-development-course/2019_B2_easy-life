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

public class Created_class extends AppCompatActivity {
    MyHelper myhelper;
    List<Integer> course_id_category = new ArrayList<Integer>();
    List<String> course_category = new ArrayList<String>();
    List<String> created_time_category = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.created_class));
        }

        //读取课程表中的自己创建的课程信息
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("course",null,"creator_id=?",
                new String[]{String.valueOf(MainActivity.user_id)},null,null,null);
        if(cursor.moveToLast()){
            do{
                course_id_category.add(cursor.getInt(0));
                course_category.add(cursor.getString(3));
                created_time_category.add(cursor.getString(7));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        db.close();

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<course_id_category.size();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("course", "课程名："+course_category.get(i));
            hashMap.put("created_time", "创建时间："+created_time_category.get(i));
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
                new String[]{"course", "created_time"},
                new int[]{R.id.tv_1, R.id.tv_2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Created_class.this,Display_created_class.class);
                intent.putExtra("course_id",course_id_category.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Created_class.this,Sign_in.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
