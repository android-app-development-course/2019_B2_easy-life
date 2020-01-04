package com.example.yahoo.scnu_easylife;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "easylife.db", null, 2);
    }

    @Override
    //首次创建数据库的时候调用，一般可以执行建库，建表的操作
    //Sqlite没有单独的布尔存储类型，它使用INTEGER作为存储类型，0为false，1为true
    public void onCreate(SQLiteDatabase db) {
        //用户信息表
        db.execSQL("create table if not exists user_infomation(user_id integer primary key autoincrement, " +
                "nickname text not null, realname text not null, password text not null)");

        //课堂签到
        //课程信息表
        db.execSQL("create table if not exists course(course_id integer primary key autoincrement," +
                "collage text not null, grade text not null, course_name text not null, teacher text not null, " +
                "class_location text not null, able_sign_time integer not null, create_time text not null," +
                "creator_id integer references user_infomation(user_id), create_time_ms long not null)");
        //签到记录表
        db.execSQL("create table if not exists signed_record(course_id integer references course(course_id), " +
                "sign_user_id integer references user_infomation(user_id), sign_time_ms text not null)");

        //失物招领
        //失物信息表
        // kind=1为失物招领 description为物品当前所在地
        // kind=2为寻物启事 description为物品特征
        db.execSQL("create table if not exists lost_found(published_id integer primary key autoincrement, " +
                "item_name text not null, place text not null, time text, description text, " +
                "image blob, kind integer not null, create_time text not null, creator_nickname text not null," +
                "creator_id integer references user_infomation(user_id))");

        //委托
        //委托内容表
        db.execSQL("create table if not exists commission(commission_id integer primary key autoincrement, " +
                "commission_title text not null, commission_content text not null, commission_position text, " +
                "comission_reward text not null, create_time text not null, creator_nickname text not null, " +
                "creator_id integer references user_infomation(user_id))");

        //聊天记录
        db.execSQL("create table if not exists chat_record(send_id integer references user_infomation(user_id)," +
                "receive_id integer references user_infomation(user_id), content text, send_time text)");
    }

    @Override
    //当数据库的版本发生变化时，会自动执行
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String table, ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(table,null,values);
        db.close();
    }
}