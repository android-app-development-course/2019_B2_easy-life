package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn_sign_in = (Button) getActivity().findViewById(R.id.btn_sign_in);
        Button btn_lost_found = (Button) getActivity().findViewById(R.id.btn_lost_found);
        Button btn_entrust = (Button) getActivity().findViewById(R.id.btn_entrust);
        Button btn_website = (Button) getActivity().findViewById(R.id.btn_website);

        //课堂签到
        btn_sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Sign_in.class);
                startActivity(intent);
            }
        });
        //失物招领
        btn_lost_found.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Lost_found.class);
                startActivity(intent);
            }
        });
        //委托
        btn_entrust.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Entrust.class);
                startActivity(intent);
            }
        });
        //常用网站
        btn_website.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Website.class);
                startActivity(intent);
            }
        });
    }
}