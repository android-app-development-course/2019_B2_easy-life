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

public class Created_commission extends AppCompatActivity {
    MyHelper myhelper;
    List<Integer> commission_id_category = new ArrayList<Integer>();
    List<String> commission_title_category = new ArrayList<String>();
    List<String> create_time_category = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.created_commission));
        }

        //读取委托内容表中的我创建的委托信息
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("commission",null,"creator_id=?",
                new String[]{String.valueOf(MainActivity.user_id)},null,null,null);
        if(cursor.moveToLast()){
            do{
                commission_id_category.add(cursor.getInt(0));
                commission_title_category.add(cursor.getString(1));
                create_time_category.add(cursor.getString(5));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        db.close();

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<commission_id_category.size();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("commission_title", "标题："+commission_title_category.get(i));
            hashMap.put("create_time", "发布时间："+create_time_category.get(i));
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
                new String[]{"commission_title", "create_time"},
                new int[]{R.id.tv_1, R.id.tv_2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Created_commission.this,Display_created_commission.class);
                intent.putExtra("commission_id",commission_id_category.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Created_commission.this,Entrust.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}