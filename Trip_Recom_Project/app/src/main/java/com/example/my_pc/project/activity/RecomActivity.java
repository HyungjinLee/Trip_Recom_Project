package com.example.my_pc.dbproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.my_pc.dbproject.AppConfig;
import com.example.my_pc.dbproject.AppController;
import com.example.my_pc.dbproject.R;
import com.example.my_pc.dbproject.helper.BackgroundWorker;

import org.json.JSONException;
import org.json.JSONObject;

public class RecomActivity extends AppCompatActivity {

    // 컴포넌트

    private Button btnRecom;
    private Button btnLinkToMain;

    private EditText edit_cityname;
    private EditText edit_air;
    private EditText edit_safety;
    private EditText edit_wifi;
    private EditText edit_english;
    private EditText edit_comment;
    private RatingBar ratingBar;
    String userid;

    // 값
    private String recom_id;
    private String safety;
    private String wifi_subjective;
    private String english;
    private String rating;
    private String comments;
    private String city_name;

    private Integer last_recom_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recom_menu);

        edit_cityname = (EditText) findViewById(R.id.city_name);
        edit_safety = (EditText) findViewById(R.id.safety);
        edit_wifi = (EditText) findViewById(R.id.wifi);
        edit_english = (EditText) findViewById(R.id.english);
        edit_comment = (EditText) findViewById(R.id.comments);
        ratingBar = (RatingBar) findViewById(R.id.user_rate);


        btnRecom = (Button) findViewById(R.id.btnRecommend);
        btnLinkToMain = (Button) findViewById(R.id.btnLinkToMainScreen);

        Intent intent = getIntent();
        city_name = intent.getExtras().getString("city_name");
        userid = intent.getExtras().getString("user_id");

        edit_cityname.setText(city_name);
        edit_cityname.setClickable(false);
        edit_cityname.setEnabled(false);
        edit_cityname.setFocusable(false);
        edit_cityname.setFocusableInTouchMode(false);

        String tag_string_req = "req_getlastnum";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LAST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Getting index Response: " + response);

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        recom_id = (jObj.getString("recom_id"));
                        Log.d("TAG","파싱한 추천 아이디 : " + recom_id);
                        int recom_id_integer = Integer.parseInt(recom_id);
                        recom_id_integer++;
                        recom_id = String.valueOf(recom_id_integer);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Getting Error: " + error.getMessage());
            }
        }) {

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    public void onClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        switch(v.getId())
        {
            case R.id.btnRecommend:
            // 유저가 선택한 내용 서버로 전송

                // 값
                safety=edit_safety.getText().toString();
                wifi_subjective=edit_wifi.getText().toString();
                english=edit_english.getText().toString();
                rating = Float.toString(ratingBar.getRating());
                comments = edit_comment.getText().toString();

                OnRecomm(v);

                startActivity(intent);
                break;

            case R.id.btnLinkToMainScreen:
                // 유저가 선택한 내용 서버로 전송
                startActivity(intent);
                break;
        }

    }

    public void OnRecomm(View view)
    {

        String type = "Recom";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,recom_id,userid,city_name,comments,safety,wifi_subjective,english,rating);

    }

}
