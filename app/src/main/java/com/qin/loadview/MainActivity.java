package com.qin.loadview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LoadingLayout mLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadView = findViewById(R.id.loadview);
 //       mLoadView.setEmptyImg(R.mipmap.empty).setEmptyText("当前没有数据");
        mLoadView.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Toast.makeText(MainActivity.this,"reload",Toast.LENGTH_SHORT).show();
            }
        });
        mLoadView.setStatus(LoadingLayout.NONETWORK);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadView.setStatus(LoadingLayout.SUCCESS);
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadView.setStatus(LoadingLayout.EMPTY);
            }
        },2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadView.setStatus(LoadingLayout.ERROR);
            }
        },3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadView.setStatus(LoadingLayout.NONETWORK);
            }
        },4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadView.setStatus(LoadingLayout.LOADING);
            }
        },5000);
    }
}
