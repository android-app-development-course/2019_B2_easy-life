package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modify_password extends AppCompatActivity {
    MyHelper myhelper;
    EditText edt_old_password;
    EditText edt_new_password1;
    EditText edt_new_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        myhelper = new MyHelper(this);
        edt_old_password = findViewById(R.id.edt_old_password);
        edt_new_password1 = findViewById(R.id.edt_new_password1);
        edt_new_password2 = findViewById(R.id.edt_new_password2);
        final Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                db = myhelper.getReadableDatabase();
                Cursor cursor = db.query("user_infomation",null,"user_id=?",
                        new String[] {String.valueOf(MainActivity.user_id)},null,null,null);
                cursor.moveToFirst();
                if(!edt_old_password.getText().toString().equals(cursor.getString(3))){
                    toastMessage(getString(R.string.old_password_wrong));
                }else if(edt_new_password1.getText().toString().equals("") || edt_new_password2.getText().toString().equals("")){
                    toastMessage(getString(R.string.password_no_empty));
                }else if(!edt_new_password1.getText().toString().equals(edt_new_password2.getText().toString())){
                    toastMessage(getString(R.string.passsword_different));
                }else{
                    db = myhelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("password",edt_new_password1.getText().toString());
                    db.update("user_infomation",values,"user_id=?",
                            new String[]{String.valueOf(MainActivity.user_id)});
                    MainActivity.password = edt_new_password1.getText().toString();
                    toastMessage(getString(R.string.save_success));
                    edt_old_password.setText("");
                    edt_new_password1.setText("");
                    edt_new_password2.setText("");
                }
                cursor.close();
                db.close();
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
                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
