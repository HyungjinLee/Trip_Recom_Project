package com.example.my_pc.dbproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.my_pc.dbproject.activity.LoginActivity;

import com.example.my_pc.dbproject.helper.SQLiteHandler;
import com.example.my_pc.dbproject.helper.SessionManager;

import org.w3c.dom.Text;

public class MenuFragment extends Fragment {

    GestureDetector gestureDetector;
    private SQLiteHandler db;
    private SessionManager session;
    TextView myname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.slide_menu, container, false);
        myname = (TextView) rootView.findViewById(R.id.name);
        String userName = getArguments().getString("username");
        myname.setText(userName);

        return rootView;
    }
}