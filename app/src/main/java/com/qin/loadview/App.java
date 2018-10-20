package com.qin.loadview;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadingLayout.Builder().setEmptyText("app").setErrorText("cuosuesafeaf");
    }
}
