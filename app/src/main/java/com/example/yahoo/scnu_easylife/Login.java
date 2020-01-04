package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    MyHelper myhelper;
    private EditText edt_user_id;
    private EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myhelper = new MyHelper(this);
        edt_user_id = (EditText)findViewById(R.id.edt_user_id);
        edt_password = (EditText)findViewById(R.id.edt_password);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        //登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                db = myhelper.getReadableDatabase();
                Cursor cursor = db.query("user_infomation",null,"user_id=?",
                        new String[] {edt_user_id.getText().toString()},null,null,null);
                if(cursor.getCount()==0){
                    toastMessage(getString(R.string.no_user));
                }else if(cursor.moveToFirst() && edt_password.getText().toString().equals(cursor.getString(3))) {
                    toastMessage(getString(R.string.login_success));
                    MainActivity.user_id = Integer.parseInt(cursor.getString(0));
                    MainActivity.nickname = cursor.getString(1);
                    MainActivity.realname = cursor.getString(2);
                    MainActivity.password = cursor.getString(3);
                    MainActivity.islogin = true;
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    toastMessage(getString(R.string.wrong_account_password));
                }
                cursor.close();
                db.close();
            }
        });
        //注册
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
