package com.example.my_pc.dbproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.my_pc.dbproject.R;

public class FrontActivity extends AppCompatActivity {

    private Button LoginMenuButton;
    private Button SignOutMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        LoginMenuButton = (Button) findViewById(R.id.button_login);
        SignOutMenuButton = (Button) findViewById(R.id.button_register);

    }

    public void onBtnLoginClick(View v)
    {

        Intent intent;

        switch(v.getId())
        {
            case R.id.button_register:
                intent = new Intent(FrontActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.button_login:
                intent = new Intent(FrontActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }


    }
}
