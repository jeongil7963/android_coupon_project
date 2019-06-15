package com.example.myapplication.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.myProfile.MyProfileViewActivity;
import com.example.myapplication.activities.stores.StoreManagerActivity;
import com.example.myapplication.db.SessionTable;
import com.example.myapplication.utils.PostCallBack;
import com.example.myapplication.utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    GoogleMap gMap = null;
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           String[] permissions,
                                           int[] grandResults){
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE) {

            // 모든 퍼미션을 허용했는지 체크
            for ( int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED){
                    finish();
                    return;
                }
            }
            // 퍼미션을 허용했다면 위치 받기
            if ( gMap != null){
                if(checkPermission())
                    gMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 필요한 작업을 진
        }else {
            // 퍼미션이 없으면 계속요청
            ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE);
        }


        FragmentManager fragmentManager = getFragmentManager();

        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //지도에 추가 작업을 할 수 있을 때 호출 되도록 세팅
                MarkerOptions marker = marker(37.558558, 126.937116, "할리스", "할리스커피입니다");
                //지도에 마커 추가
                googleMap.addMarker(marker);
                googleMap.addMarker(marker(37.558677,126.936674, "스타벅스", "스타벅스입니다."));
                googleMap.addMarker(marker(37.557180,126.936352, "세븐일레븐", "세븐일레븐편의점입니다.."));


                //지도를 원하는 위치로 이동
                LatLng pos = marker.getPosition();
                CameraUpdate cam = CameraUpdateFactory.newLatLng(pos);
                googleMap.moveCamera(cam);

                //원하는 줌이 되게
                CameraUpdate cam2 = CameraUpdateFactory.zoomTo(10);
                googleMap.moveCamera(cam2);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        float index = marker.getZIndex();
                        marker.setZIndex(index -1);

                        Toast.makeText(MainActivity.this, (int)index + "개의 쿠폰이 남았습니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                gMap = googleMap;
                if(checkPermission()) {
                    googleMap.setMyLocationEnabled(true);
                }
            }
        });
    }

    private boolean checkPermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoareLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoareLocationPermission == PackageManager.PERMISSION_GRANTED);
    }

    public void onClickLogout(View v) {
        SessionTable.inst().delSession(this);

        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);

        finish();
    }

    public void onClickBack(View v){
        finish();
    }

    public void onClickStoreManager(View v){
        Intent intent = new Intent(this, StoreManagerActivity.class);
        //값 넣고 전달
        intent.putExtra("test", "test");
        intent.putExtra("testInt", 1);

        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(this, MyProfileViewActivity.class);
        //값 넣고 전달
        intent.putExtra("test", "test");
        intent.putExtra("testInt", 1);

        startActivity(intent);
    }

    private MarkerOptions marker(double lat, double lng, String name, String desc){
        //마커
        LatLng seoul = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(seoul);
        markerOptions.title(name);
        markerOptions.snippet(desc);
        markerOptions.zIndex(100);
        //지도에 마커 추가
        return markerOptions;
    }
}
