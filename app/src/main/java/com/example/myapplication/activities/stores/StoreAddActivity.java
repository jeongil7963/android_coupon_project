package com.example.myapplication.activities.stores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.SignInActivity;
import com.example.myapplication.db.SessionTable;
import com.example.myapplication.utils.PostCallBack;
import com.example.myapplication.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StoreAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_add);
    }

    public void onClickGPS(View v) {

        final PostCallBack cb = new PostCallBack() {
            @Override
            public void onResponse(JSONObject ret, String errMsg) {
                try {
                    JSONObject result = ret.getJSONArray("results").getJSONObject(0);
                    JSONObject gps = result.getJSONObject("geometry").getJSONObject("location");
                    final Double lng = gps.getDouble("lng");
                    final Double lat = gps.getDouble("lat");

                    StoreAddActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((EditText) findViewById(R.id.lng)).setText(lng.toString());
                            ((EditText) findViewById(R.id.lat)).setText(lat.toString());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        EditText etStoreName = findViewById(R.id.ID_Text);
        EditText etAdress = findViewById(R.id.Pwd_Text);
        String apiKey = "put on your api key";

        JSONObject params = new JSONObject();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?";
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        Utils.post(url + "address=" + address + "&key=" + apiKey, params.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (cb != null)
                    cb.onResponse(null, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (cb == null)
                    return;
                try {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        cb.onResponse(new JSONObject(responseStr), null);
                    } else {
                        cb.onResponse(null, response.message());
                    }
                } catch (Exception e) {
                    cb.onResponse(null, e.getMessage());
                }
            }
        });
    }

    public void onClickBack(View v){
        finish();
    }

}
