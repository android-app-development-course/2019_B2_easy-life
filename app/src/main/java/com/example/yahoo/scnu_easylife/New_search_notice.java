package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_search_notice extends AppCompatActivity {
    private final int IMAGE_RESULT_CODE = 2;
    MyHelper myhelper;
    TextView tv_new_lost_found;
    EditText edt_item_name;
    TextView tv_lost_place;
    EditText edt_lost_place;
    TextView tv_lost_time;
    EditText edt_lost_time;
    TextView tv_item_character;
    EditText edt_item_character;
    Bitmap bitmap;
    byte[] img = new byte[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lost_found);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.new_search_notice));
        }
        myhelper = new MyHelper(this);
        Button btn_upload_image = findViewById(R.id.btn_upload_image);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_quit = findViewById(R.id.btn_quit);
        tv_new_lost_found = findViewById(R.id.tv_new_lost_found);
        edt_item_name = findViewById(R.id.edt_item_name);
        tv_lost_place = findViewById(R.id.tv_place);
        edt_lost_place = findViewById(R.id.edt_place);
        tv_lost_time = findViewById(R.id.tv_time);
        edt_lost_time = findViewById(R.id.edt_time);
        tv_item_character = findViewById(R.id.tv_description);
        edt_item_character = findViewById(R.id.edt_description);

        tv_new_lost_found.setText(getString(R.string.new_search_notice));
        tv_lost_place.setText(getString(R.string.lost_place));
        tv_lost_time.setText(getString(R.string.lost_time));
        tv_item_character.setText(getString(R.string.item_character));

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
                String lost_place = edt_lost_place.getText().toString();
                String lost_time = edt_lost_time.getText().toString();
                String item_character = edt_item_character.getText().toString();

                if(!TextUtils.isEmpty(item_name) && !TextUtils.isEmpty(lost_place)){
                    ContentValues values = new ContentValues();
                    values.put("item_name",item_name);
                    values.put("place",lost_place);
                    values.put("time",lost_time);
                    values.put("description",item_character);//物品特征
                    values.put("image",img);
                    values.put("kind","2");//寻物启事
                    SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    values.put("create_time",String.valueOf(s_format.format(new Date())));//获取当前时间
                    values.put("creator_nickname",MainActivity.nickname);
                    values.put("creator_id",String.valueOf(MainActivity.user_id));
                    myhelper.insert("lost_found",values);
                    toastMessage(getString(R.string.publish_success));
                    Intent intent=new Intent(New_search_notice.this,Lost_found.class);
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
                Intent intent=new Intent(New_search_notice.this,Lost_found.class);
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
            bitmap = PhotoUtils.getBitmapFromUri(uri, this);
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
                Intent intent=new Intent(New_search_notice.this,Lost_found.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
