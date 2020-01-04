package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Display_cur_lost_found extends AppCompatActivity {
    MyHelper myhelper;
    TextView dis_item_name;
    TextView tv_place;
    TextView dis_place;
    TextView tv_time;
    TextView dis_time;
    TextView tv_description;
    TextView dis_description;
    TextView dis_publish_time;
    TextView dis_creator_nickname;
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_lost_found);
        ActionBar actionBar = getSupportActionBar();
        myhelper = new MyHelper(this);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.cur_lost_found));
        }
        Integer published_id = getIntent().getIntExtra("published_id",0);
        dis_item_name = findViewById(R.id.dis_item_name);
        tv_place = findViewById(R.id.tv_place);
        dis_place = findViewById(R.id.dis_place);
        tv_time = findViewById(R.id.tv_time);
        dis_time = findViewById(R.id.dis_time);
        tv_description = findViewById(R.id.tv_description);
        dis_description = findViewById(R.id.dis_description);
        dis_publish_time = findViewById(R.id.dis_publish_time);
        dis_creator_nickname = findViewById(R.id.dis_creator_nickname);
        iv_image = findViewById(R.id.iv_image);
        Button btn_contact_creator = findViewById(R.id.btn_contact_creator);

        SQLiteDatabase db;
        db = myhelper.getReadableDatabase();
        Cursor cursor = db.query("lost_found",null,"published_id=?",
                new String[]{String.valueOf(published_id)},null,null,null);
        cursor.moveToFirst();
        if (cursor.getString(6).equals("2")){

            tv_place.setText(getString(R.string.lost_place));
            tv_time.setText(getString(R.string.lost_time));
            tv_description.setText(getString(R.string.item_character));
            btn_contact_creator.setText(getString(R.string.contact_owner));
        }
        dis_item_name.setText(cursor.getString(1));
        dis_place.setText(cursor.getString(2));
        dis_time.setText(cursor.getString(3));
        dis_description.setText(cursor.getString(4));
        dis_publish_time.setText(cursor.getString(7));
        dis_creator_nickname.setText(cursor.getString(8));
        final int creator_id = cursor.getInt(9);
        byte[] img = cursor.getBlob(5);
        if (img.length==0){
            iv_image.setImageResource(R.drawable.no_picture);
        }else{
            Bitmap bmp = BitmapFactory.decodeByteArray(img,0, img.length);
            iv_image.setImageBitmap(bmp);
        }
        cursor.close();
        db.close();

        //联系拾获者/失主
        if(creator_id==MainActivity.user_id) btn_contact_creator.setVisibility(View.INVISIBLE);
        btn_contact_creator.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(Display_cur_lost_found.this,MList.class);
                MList.chatter_id = String.valueOf(creator_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Display_cur_lost_found.this,Cur_lost_found.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}