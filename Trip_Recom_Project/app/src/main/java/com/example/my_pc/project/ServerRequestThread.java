package com.example.my_pc.dbproject;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.my_pc.dbproject.activity.MainActivity;
import com.example.my_pc.dbproject.interfaces.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerRequestThread extends Thread {

    MainActivity mContext;

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    String city_name;

    public ServerRequestThread(MainActivity mContext){
        this.mContext = mContext;
        // Progress dialog
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }



    private ArrayList<Comment> comments = new ArrayList<>();
    public void run(){
        super.run();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        GetCommentsFromServer(city_name);
    }

    private void GetCommentsFromServer(final String city_name) {

        String tag_string_req = "GetComments";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GETCITYRECOM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Getting index Response: " + response);
                JSONObject jsonResponse;

                try {

                    jsonResponse = new JSONObject(response);
                    JSONArray recoms = jsonResponse.getJSONArray("result");
                    boolean error = jsonResponse.getBoolean("error");


                    // Check for error node in json
                    if (!error) {

                        for (int i = 0; i < recoms.length(); i++) {
                            JSONObject recom = recoms.getJSONObject(i);
                            String username = recom.getString("user_id");
                            String comment = recom.getString("comments");
                            String recom_time = recom.getString("recom_time");
                            Comment mycomment = new Comment(R.drawable.user,username,comment,recom_time);
                            comments.add(mycomment);
                        }
                        mContext.handler.sendEmptyMessage(mContext.THREAD_HANDLER_SUCCESS_INFO);
                        //Thread 작업 종료, UI 작업을 위해 MainHandler에 Message보냄


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jsonResponse.getString("error_msg");
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
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("city_name", city_name);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }



}
