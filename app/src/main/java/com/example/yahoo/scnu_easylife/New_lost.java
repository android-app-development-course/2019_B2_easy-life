package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_lost extends AppCompatActivity {
    private final int IMAGE_RESULT_CODE = 2;
    MyHelper myhelper;
    EditText edt_item_name;
    EditText edt_found_place;
    EditText edt_found_time;
    EditText edt_cur_place;
    byte[] img = new byte[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lost_found);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.new_lost));
        }
        myhelper = new MyHelper(this);
        Button btn_upload_image = findViewById(R.id.btn_upload_image);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_quit = findViewById(R.id.btn_quit);
        edt_item_name = findViewById(R.id.edt_item_name);
        edt_found_place = findViewById(R.id.edt_place);
        edt_found_time = findViewById(R.id.edt_time);
        edt_cur_place = findViewById(R.id.edt_description);

        //上传图片
        btn_upload_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调取相册
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_RESULT_CODE);
            }
        });

        //确定
        btn_confirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name = edt_item_name.getText().toString();
                String found_place = edt_found_place.getText().toString();
                String found_time = edt_found_time.getText().toString();
                String cur_place = edt_cur_place.getText().toString();

                if(!TextUtils.isEmpty(item_name) && !TextUtils.isEmpty(found_place)){
                    ContentValues values = new ContentValues();
                    values.put("item_name",item_name);
                    values.put("place",found_place);
                    values.put("time",found_time);
                    values.put("description",cur_place);//物品所在地
                    values.put("image",img);
                    values.put("kind","1");//失物招领
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("create_time",String.valueOf(s_format.format(new Date())));//获取当前时间
                    values.put("creator_nickname",MainActivity.nickname);
                    values.put("creator_id",String.valueOf(MainActivity.user_id));
                    myhelper.insert("lost_found",values);
                    toastMessage(getString(R.string.publish_success));
                    Intent intent=new Intent(New_lost.this,Lost_found.class);
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
                Intent intent=new Intent(New_lost.this,Lost_found.class);
                startActivity(intent);
            }
        });
    }

    //获取相册图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_RESULT_CODE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            Bitmap bitmap = PhotoUtils.getBitmapFromUri(uri, this);
            img = img_byte(bitmap);
            toastMessage(getString(R.string.upload_success));
        }
    }

    //把图片转换成字节
    public byte[] img_byte(Bitmap bitmap)
    {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
          return baos.toByteArray();
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //返回键
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(New_lost.this,Lost_found.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
