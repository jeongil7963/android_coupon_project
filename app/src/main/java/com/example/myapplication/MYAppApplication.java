package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.db.SessionTable;
import com.example.myapplication.db.UserInfoTable;

public class MYAppApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        SessionTable.inst().load(this);
        UserInfoTable.inst().load(this);
        Log.e("application", "앱이 실행되고 SessionTable의 값을 불렀습니다.");
        Log.e("application", SessionTable.inst().toString());
    }
}
