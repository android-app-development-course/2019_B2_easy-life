package com.example.yahoo.scnu_easylife;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {
    TextView tv_nickname;
    TextView tv_my_profile;
    TextView tv_modify_password;
    TextView tv_logout;
    private boolean isGetData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_nickname = (TextView) getActivity().findViewById(R.id.tv_nickname);
        tv_my_profile = (TextView) getActivity().findViewById(R.id.tv_my_profile);
        tv_modify_password = (TextView) getActivity().findViewById(R.id.tv_modify_password);
        tv_logout = (TextView) getActivity().findViewById(R.id.tv_logout);
        tv_nickname.setText(MainActivity.nickname);
        //我的资料
        tv_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),My_profile.class);
                getActivity().startActivityForResult(intent, 3);
            }
        });
        //修改密码
        tv_modify_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Modify_password.class);
                getActivity().startActivityForResult(intent, 3);
            }
        });
        //退出登录
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user_id = 0;
                MainActivity.nickname = "0";
                MainActivity.realname = "0";
                MainActivity.password = "0";
                MainActivity.islogin = false;
                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });

        //返回页面时刷新
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    refresh();
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    private void refresh() {
        tv_nickname.setText(MainActivity.nickname);
    }
}