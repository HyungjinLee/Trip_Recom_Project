package com.example.my_pc.dbproject.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.example.my_pc.dbproject.AppController;

import static com.example.my_pc.dbproject.AppConfig.URL_RECOM;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    String recom_id;

    public BackgroundWorker(Context ctx){
        context = ctx;

    }

    @Override
    protected String doInBackground(String... params)
    {
        String type = params[0];

        if(type.equals("Recom"))
        {
            recom_id = params[1];
            String user_id = params[2];
            String city_name = params[3];
            String comments = params[4];
            String safety = params[5];
            String wifi = params[6];
            String english = params[7];
            String user_rate = params[8];

            try{
                URL url = new URL(URL_RECOM);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                Log.d("TAG", "변환하고자 하는 추천 아이디 : " + recom_id);

                String post_data = URLEncoder.encode("recom_id","UTF-8") + "=" +URLEncoder.encode(recom_id,"UTF-8") + "&"
                        + URLEncoder.encode("user_id","UTF-8") + "=" +URLEncoder.encode(user_id,"UTF-8") + "&"
                        + URLEncoder.encode("city_name","UTF-8") + "=" +URLEncoder.encode(city_name,"UTF-8") + "&"
                        + URLEncoder.encode("comments","UTF-8") + "=" +URLEncoder.encode(comments,"UTF-8") + "&"
                        + URLEncoder.encode("safety","UTF-8") + "=" +URLEncoder.encode(safety,"UTF-8") + "&"
                        + URLEncoder.encode("wifi","UTF-8") + "=" +URLEncoder.encode(wifi,"UTF-8") + "&"
                        + URLEncoder.encode("english","UTF-8") + "=" +URLEncoder.encode(english,"UTF-8") + "&"
                        + URLEncoder.encode("user_rate","UTF-8") + "=" +URLEncoder.encode(user_rate,"UTF-8") + "&";

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result ="";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("TAG",result);
                return result;

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Tag used to cancel the request


    }

    protected void onPostExecute(String aVoid) {
    }

    protected void onProgressUpdate(String... values) {

    }
}
