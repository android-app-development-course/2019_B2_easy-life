package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cur_class extends AppCompatActivity {
    private SwipeRefreshLayout refresh;
    MyHelper myhelper;
    EditText edt_search;
    List<Integer> course_id_category = new ArrayList<Integer>();
    List<String> course_category = new ArrayList<String>();
    List<String> teacher_category = new ArrayList<String>();
    ArrayList<HashMap<String,String>> list;
    SimpleAdapter listAdpter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        myhelper = new MyHelper(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.cur_class));
        }

        edt_search = (EditText)findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                //当输入框的内容不为空时，显示过滤后的数据列表，否则显示全部列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Readdata();
        list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<course_id_category.size();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("course", "课程名："+course_category.get(i));
            hashMap.put("teacher", "教师："+teacher_category.get(i));
            list.add(hashMap);
        }
        Setdata();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        course_id_category.clear();
                        course_category.clear();
                        teacher_category.clear();
                        Readdata();
                        listAdpter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                    }
                }, 2000);
                toastMessage(getString(R.string.refresh_success));
            }
        });
    }

    private void filterData(String filterStr) {
        //当输入框的内容为空时显示全部列表
        if (TextUtils.isEmpty(filterStr)) {
            list.clear();
            for(int i=0;i<course_id_category.size();i++){
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("course", "课程名："+course_category.get(i));
                hashMap.put("teacher", "教师："+teacher_category.get(i));
                list.add(hashMap);
            }
        } else {
            //清空
            list.clear();
            //遍历列表
            for(int i=0;i<course_id_category.size();i++){
                if(course_category.get(i).trim().contains(edt_search.getText().toString()) ||
                        teacher_category.get(i).trim().contains(edt_search.getText().toString())){
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("course", "课程名："+course_category.get(i));
                    hashMap.put("teacher", "教师："+teacher_category.get(i));
                    list.add(hashMap);
                }
            }
        }
        Setdata();
    }

    private void Readdata(){
        //读取课程表中的课程信息
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("course",null,null,null,
                null,null,null);
        if(cursor.moveToLast()){
            do{
                course_id_category.add(cursor.getInt(0));
                course_category.add(cursor.getString(3));
                teacher_category.add(cursor.getString(4));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        db.close();
    }

    private void Setdata(){
        //创建适配器
        // 第一个参数是上下文对象
        // 第二个是listitem
        // 第三个是指定每个列表项的布局文件
        // 第四个是指定Map对象中定义的两个键（这里通过字符串数组来指定）
        // 第五个是用于指定在布局文件中定义的id（也是用数组来指定）
        listAdpter = new SimpleAdapter(
                this,
                list,
                R.layout.fragment_list,
                new String[]{"course", "teacher"},
                new int[]{R.id.tv_1, R.id.tv_2});
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Cur_class.this,Display_cur_class.class);
                intent.putExtra("course_id",course_id_category.get(position));
                startActivity(intent);
            }
        });
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Cur_class.this,Sign_in.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}