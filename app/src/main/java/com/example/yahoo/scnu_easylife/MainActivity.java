package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigation;
    private Fragment home;
    private Fragment notifications;
    private Fragment profile;
    private Fragment[] fragments;
    private int lastfragment = 0;
    public static int user_id = 0;
    public static String nickname = "0";
    public static String realname = "0";
    public static String password = "0";
    public static boolean islogin = false;

    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.navigation_profile:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!islogin){
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initFragment();
    }

    private void initFragment() {
        home = new Home();
        notifications = new Notifications();
        profile = new Profile();
        fragments = new Fragment[]{home, notifications, profile};
        lastfragment = 0;

        //设置默认fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,home).show(home).commit();
        bottomNavigation = (BottomNavigationView) findViewById(R.id.nav_view);
        //bottomnavigationview的点击事件
        bottomNavigation.setOnNavigationItemSelectedListener(changeFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,home).show(home).commit();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,notifications).show(notifications).commit();
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,profile).show(profile).commit();
                }
                break;
        }
    }

    //切换fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainFrame, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
}