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
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_commission extends AppCompatActivity {
    MyHelper myhelper;
    EditText edt_commission_title;
    EditText edt_commission_content;
    EditText edt_commission_position;
    EditText edt_comission_reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_commission);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.new_commission));
        }
        myhelper = new MyHelper(this);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_quit = findViewById(R.id.btn_quit);
        edt_commission_title = findViewById(R.id.edt_commission_title);
        edt_commission_content = findViewById(R.id.edt_commission_content);
        edt_commission_position = findViewById(R.id.edt_commission_position);
        edt_comission_reward = findViewById(R.id.edt_comission_reward);

        //确定
        btn_confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commission_title = edt_commission_title.getText().toString();
                String commission_content = edt_commission_content.getText().toString();
                String commission_position = edt_commission_position.getText().toString();
                String comission_reward = edt_comission_reward.getText().toString();

                if(!TextUtils.isEmpty(commission_title) && !TextUtils.isEmpty(commission_content) &&
                        !TextUtils.isEmpty(comission_reward)){
                    ContentValues values = new ContentValues();
                    values.put("commission_title",commission_title);
                    values.put("commission_content",commission_content);
                    values.put("commission_position",commission_position);
                    values.put("comission_reward",comission_reward);
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("create_time",String.valueOf(s_format.format(new Date())));//获取当前时间
                    values.put("creator_nickname",MainActivity.nickname);
                    values.put("creator_id",String.valueOf(MainActivity.user_id));
                    myhelper.insert("commission",values);
                    toastMessage(getString(R.string.publish_success));
                    Intent intent=new Intent(New_commission.this,Entrust.class);
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
                Intent intent=new Intent(New_commission.this,Entrust.class);
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
                Intent intent=new Intent(New_commission.this,Entrust.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
