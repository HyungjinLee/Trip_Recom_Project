package com.example.my_pc.dbproject.activity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.my_pc.dbproject.AppConfig;
import com.example.my_pc.dbproject.AppController;
import com.example.my_pc.dbproject.City;
import com.example.my_pc.dbproject.ExampleDataset;
import com.example.my_pc.dbproject.MenuFragment;
import com.example.my_pc.dbproject.R;
import com.example.my_pc.dbproject.ServerRequestThread;
import com.example.my_pc.dbproject.helper.SessionManager;
import com.example.my_pc.dbproject.interfaces.CardDataImpl;
import com.example.my_pc.dbproject.interfaces.Comment;
import com.example.my_pc.dbproject.interfaces.CommentArrayAdapter;
import com.example.my_pc.dbproject.helper.SQLiteHandler;
import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerCardContentList;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.media.Rating;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends BaseActivity {
    public static final int THREAD_HANDLER_SUCCESS_INFO = 1;

    boolean isFragmentLoaded;   
    TextView title;
    ImageView menuButton;

    private Button btnLogout;
    private ProgressDialog pDialog;

    private SQLiteHandler db;
    private SessionManager session;
    private ECPagerView ecPagerView;
    Fragment menuFragment;
    private String username;
    private String userid;
    private long pressedTime=0;
    private String cur_city_name="";    // 현재 선택한 도시 이름

  
    private ArrayList<Comment>  comments = new ArrayList<>();;

    private ExampleDataset dataset;
    private ServerRequestThread server_thread;
    private CommentArrayAdapter commentArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_main);

        // Thread initialization
         server_thread = new ServerRequestThread(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        dataset = new ExampleDataset();
        commentArrayAdapter = new CommentArrayAdapter(getApplicationContext(), comments);
        // Implement pager adapter and attach it to pager view

        ECPagerViewAdapter adapter = new ECPagerViewAdapter(this, dataset.getDataset()) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, final ListView list, final ECCardData data) {
                // Data object for current card
                final CardDataImpl cardData = (CardDataImpl) data;
                // Create adapter for list inside a card and set adapter to card content
                Log.d("TAG","코멘트 생성!!!!!");

                list.setAdapter(commentArrayAdapter);
                list.setDivider(getResources().getDrawable(R.drawable.list_divider));
                list.setDividerHeight((int) pxFromDp(getApplicationContext(), 0.5f));
                list.setSelector(R.color.transparent);
                list.setBackgroundColor(Color.WHITE);

                list.setCacheColorHint(Color.TRANSPARENT);
                // Add gradient to root header view

                View gradient = new View(getApplicationContext());
                gradient.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                gradient.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_head_gradient));
                head.addView(gradient);

                // inflate main header layout and attach it to header root view
                inflaterService.inflate(R.layout.simple_head, head);

                // Set header data from data object
                TextView title = (TextView) head.findViewById(R.id.title);
                title.setText(cardData.getHeadTitle());
                ImageView avatar = (ImageView) head.findViewById(R.id.avatar);
                avatar.setImageResource(cardData.getHeadBackgroundResource());
                TextView currency = (TextView) head.findViewById(R.id.currency_value);
                currency.setText(Integer.toString(cardData.getCurrency()));
                TextView air = (TextView) head.findViewById(R.id.air_value);
                air.setText(Integer.toString(cardData.getAir()));
                TextView safety = (TextView) head.findViewById(R.id.safety_value);
                safety.setText(Integer.toString(cardData.getSafety()));
                TextView wifi = (TextView) head.findViewById(R.id.wifi_value);
                wifi.setText(Integer.toString(cardData.getWifi()));
                TextView english = (TextView) head.findViewById(R.id.english_value);
                english.setText(Integer.toString(cardData.getCurrency()));
                RatingBar rating = (RatingBar) head.findViewById(R.id.nationrating);

                Random rnd = new Random();
                rating.setRating(100 + rnd.nextInt(100));


                // Card toggling by click on head element
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        // 나라 클릭했을 때
                        list.setAdapter(commentArrayAdapter);

                        if(ecPagerView.expand()) {

                            comments.clear();
                            commentArrayAdapter.DeleteAllItems();

                            cur_city_name = cardData.getCity_name();
                            server_thread.setCity_name(cur_city_name);
                            server_thread.run();
                            Log.d("TAG", "클릭 이벤트 실행!!");
                            pDialog.setMessage("데이터베이스에서 정보 불러오는 중 ... ");
                            showDialog();

                        }
                        ecPagerView.toggle();

                    };

                });
            }
        };

        title = (TextView) findViewById(R.id.title_top);
        menuButton = (ImageView) findViewById(R.id.menu_icon);
        title.setText("여행 추천 시스템");

        btnLogout = (Button) findViewById(R.id.btnLogout);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("id");

        username = name;
        userid = email;

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Menu button click event
       menuButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!isFragmentLoaded) {
                        loadFragment();
                        title.setText("프로필");
                    } else {
                        if (menuFragment != null) {
                            if (menuFragment.isAdded()) {
                                hideFragment();
                            }
                        }
                    }
                }
        });


        // Get Pager from layout
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Add background switcher to pager view
        ecPagerView.setPagerViewAdapter(adapter);
        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) findViewById(R.id.ec_bg_switcher));


    }


    public void onClick(View v)
    {
        Intent intent;

        switch(v.getId())
        {
            case R.id.recommendLayout:

                // 추천 화면으로 전환
                intent = new Intent(getApplicationContext(), RecomActivity.class);
                intent.putExtra("city_name",cur_city_name);
                intent.putExtra("user_id",username);
                startActivity(intent);
                break;

            case R.id.searchLayout:
               // 검색 화면으로 전환
                intent = new Intent(getApplicationContext(), WeatherActivity.class);
                intent.putExtra("city_name",cur_city_name);
                startActivity(intent);
                break;

        }

    }

    public void hideFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.remove(menuFragment);
        fragmentTransaction.commit();
        menuButton.setImageResource(R.drawable.ic_menu);
        isFragmentLoaded = false;
        title.setText("여행 추천 시스템");
    }
    public void loadFragment(){

        FragmentManager fm = getSupportFragmentManager();
        menuFragment = fm.findFragmentById(R.id.container);
        menuButton.setImageResource(R.drawable.ic_up_arrow);
        if(menuFragment == null){
            menuFragment = new MenuFragment();

            Bundle bundle = new Bundle(1);
            bundle.putString("username",username);
            menuFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
            fragmentTransaction.add(R.id.container,menuFragment);
            fragmentTransaction.commit();
        }

        isFragmentLoaded = true;
    }

    // Card collapse on back pressed
    @Override
    public void onBackPressed() {
       if ( pressedTime == 0 ) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
                finish(); // app 종료
            }
        }
    }


    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case THREAD_HANDLER_SUCCESS_INFO:
                    comments = server_thread.getComments();
                    // Log.d("TAG", "Getting index Response: " + response);
                    // ****** 뷰 갱신 ****** //

                    // Add background switcher to pager view
                   // ecPagerView.setPagerViewAdapter(adapter);

                    Log.d("TAG", "뷰 갱신 시작 !!");
                    hideDialog();
                    commentArrayAdapter.setItems(comments);

                    if(comments.size() == 0)
                        Toast.makeText(MainActivity.this, "댓글이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };
}