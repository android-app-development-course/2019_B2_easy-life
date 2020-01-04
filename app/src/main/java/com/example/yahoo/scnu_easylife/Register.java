package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity{
    MyHelper myhelper;
    private EditText edt_nickname;
    private EditText edt_realname;
    private EditText edt_password1;
    private EditText edt_password2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        myhelper = new MyHelper(this);
        edt_nickname = (EditText)findViewById(R.id.edt_nickname);
        edt_realname = (EditText)findViewById(R.id.edt_realname);
        edt_password1 = (EditText)findViewById(R.id.edt_password1);
        edt_password2 = (EditText)findViewById(R.id.edt_password2);
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_password1.getText().toString().equals(edt_password2.getText().toString())){
                    ContentValues values = new ContentValues();
                    values.put("nickname",edt_nickname.getText().toString());
                    values.put("realname",edt_realname.getText().toString());
                    values.put("password",edt_password1.getText().toString());
                    myhelper.insert("user_infomation",values);
                    SQLiteDatabase db;
                    db = myhelper.getReadableDatabase();
                    Cursor cursor = db.query("user_infomation",null,null,
                            null,null,null,null);
                    MainActivity.user_id = cursor.getCount();
                    cursor.close();
                    db.close();
                    //弹出对话框
                    showdialog(v);
                }else{
                    toastMessage(getString(R.string.passsword_different));
                }
            }
        });
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showdialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.register_success)).
                setMessage(getString(R.string.your_id_is) + MainActivity.user_id).
                setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
