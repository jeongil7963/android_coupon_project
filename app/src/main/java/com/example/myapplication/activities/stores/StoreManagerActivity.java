package com.example.myapplication.activities.stores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.StoreTable;

public class StoreManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);

        ((TextView)findViewById(R.id.topTitle)).setText("상점 관리");
        //findViewById(R.id.topRight).setVisibility(View.GONE);

        TextView topRight = ((TextView)findViewById(R.id.topRight));
        topRight.setText("상점 추가");
        topRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoreManagerActivity.this, StoreAddActivity.class);
                startActivity(i);
            }
        });

        if (StoreTable.inst().size() == 0 )
            putStores();

        ((RecyclerView)findViewById(R.id.recyclerView)).setAdapter(new StoreAdapter());
    }

    private void putStores(){
        String [] names = {"스타벅스", "커피빈", "파스꾸지", "사보텐", "뚜레쥬르", "런던바게트", "사천리자전거", "알통스포츠",
                "스타벅스", "커피빈", "파스꾸지", "사보텐", "뚜레쥬르", "런던바게트", "사천리자전거", "알통스포츠",
                "스타벅스", "커피빈", "파스꾸지", "사보텐", "뚜레쥬르", "런던바게트", "사천리자전거", "알통스포츠",
                "스타벅스", "커피빈", "파스꾸지", "사보텐", "뚜레쥬르", "런던바게트", "사천리자전거", "알통스포츠"};
        for(int i = 0; i < names.length; i++){
            StoreTable.inst().put(i,names[i]);
        }
    }

    public void onClickBack(View v){
        finish();
    }
}
