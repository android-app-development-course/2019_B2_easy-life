package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cur_commission extends AppCompatActivity {
    private SwipeRefreshLayout refresh;
    SimpleAdapter listAdpter;
    MyHelper myhelper;
    EditText edt_search;
    List<Integer> commission_id_category = new ArrayList<Integer>();
    List<String> commission_title_category = new ArrayList<String>();
    List<String> commission_position_category = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.cur_commission));
        }

        edt_search = (EditText)findViewById(R.id.edt_search);
        edt_search.setFocusable(false);
        edt_search.setFocusableInTouchMode(false);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) edt_search.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = 0;
        edt_search.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件

        Readdata();
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<commission_id_category.size();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put("commission_title", "标题："+commission_title_category.get(i));
            hashMap.put("commission_position", "地点："+commission_position_category.get(i));
            list.add(hashMap);
        }

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
                new String[]{"commission_title", "commission_position"},
                new int[]{R.id.tv_1, R.id.tv_2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Cur_commission.this,Display_cur_commission.class);
                intent.putExtra("commission_id",commission_id_category.get(position));
                startActivity(intent);
            }
        });

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        commission_id_category.clear();
                        commission_title_category.clear();
                        commission_position_category.clear();
                        Readdata();
                        listAdpter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                    }
                }, 2000);
                toastMessage(getString(R.string.refresh_success));
            }
        });
    }

    private void Readdata(){
        //读取委托内容表中的委托信息
        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("commission",null,null,null,
                null,null,null);
        if(cursor.moveToLast()){
            do{
                commission_id_category.add(cursor.getInt(0));
                commission_title_category.add(cursor.getString(1));
                commission_position_category.add(cursor.getString(3));
            }while (cursor.moveToPrevious());
        }
        cursor.close();
        db.close();
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Cur_commission.this,Entrust.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}