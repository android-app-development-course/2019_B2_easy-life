package com.example.yahoo.scnu_easylife;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.os.Bundle;

public class Brower_web extends AppCompatActivity{
    private WebView myWebView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brower_web);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.website));
        }
        // 打开网页
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl(getIntent().getStringExtra("address"));
        //设置可自由缩放网页、JS 生效
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 修改默认在 WebView 中打开链接
        myWebView.setWebViewClient(new WebViewClient());
    }
    // 按键响应，在 WebView 中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个 WebView 返回退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack())
        {
            // 返回键退回
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //this.finish(); // back button
                Intent intent=new Intent(Brower_web.this,Website.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
