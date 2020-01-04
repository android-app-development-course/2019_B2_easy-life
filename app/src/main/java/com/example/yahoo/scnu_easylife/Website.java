package com.example.yahoo.scnu_easylife;

import android.content.Intent;
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

public class Website extends AppCompatActivity {
    String[] webname_category = new String[] {
            "教学管理信息服务平台",
            "正方教务管理系统",
            "学者网",
            "华南师范大学图书馆",
            "华南师范大学统一身份认证平台",
            "Eswis 高校学生综合服务平台 学生工作管理系统 首页",
            "华南师范大学教务处",
            "砺儒云课堂",
            "华南师范大学校园统一支付平台"};
    String[] website_category = new String[] {
            "https://jwxt.scnu.edu.cn/xtgl/login_slogin.html?language=zh_CN&_t=1576682018186",
            "https://jwc.scnu.edu.cn/cloudwaf/auth/index",
            "http://www.scholat.com/",
            "http://lib.scnu.edu.cn/",
            "https://sso.scnu.edu.cn/AccountService/user/login.html;jsessionid=B2AF23F82E486938B976CF668BDEA8CE",
            "https://ssp.scnu.edu.cn/",
            "http://jw.scnu.edu.cn/",
            "https://moodle.scnu.edu.cn/",
            "http://hscwxf.scnu.edu.cn/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.website));
        }

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<webname_category.length;i++){
            HashMap<String,String> hashMap = new HashMap<String, String>();
            hashMap.put("webname", webname_category[i]);
            hashMap.put("website", website_category[i]);
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
                new String[]{"webname", "website"},
                new int[]{R.id.tv_1, R.id.tv_2});
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Website.this,Brower_web.class);
                intent.putExtra("address",website_category[position].trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Website.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}