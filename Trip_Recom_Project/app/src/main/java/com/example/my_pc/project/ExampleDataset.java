package com.example.my_pc.dbproject;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.my_pc.dbproject.interfaces.CardDataImpl;
import com.example.my_pc.dbproject.interfaces.Comment;
import com.ramotion.expandingcollection.ECCardData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExampleDataset {

    private List<ECCardData> dataset;

    public ExampleDataset() {

        dataset = new ArrayList<>(30);

        CardDataImpl item1 = new CardDataImpl();
        String cityname;
        City city = new City("");

        cityname = "Canberra";
        item1.setCity_name(cityname);
        item1.setMainBackgroundResource(R.drawable.bg_hoju);
        item1.setHeadBackgroundResource(R.drawable.austrailia);
        item1.setHeadTitle("캔버라");
        city.setCity_name(cityname);
        item1.setListItems(prepareCommentsArray());
        dataset.add(item1);

        CardDataImpl item2 = new CardDataImpl();
        cityname = "Amsterdam";
        item2.setCity_name(cityname);
        item2.setMainBackgroundResource(R.drawable.bg_netherlands);
        item2.setHeadBackgroundResource(R.drawable.netherlands);
        item2.setHeadTitle("암스테르담");
        city.setCity_name(cityname);
        item2.setListItems(prepareCommentsArray());

        dataset.add(item2);

        CardDataImpl item3 = new CardDataImpl();
        cityname = "Ankara";
        item3.setCity_name(cityname);
        item3.setMainBackgroundResource(R.drawable.bg_turkey);
        item3.setHeadBackgroundResource(R.drawable.turkey);
        item3.setHeadTitle("앙카라");
        city.setCity_name(cityname);
        item3.setListItems(prepareCommentsArray());

        dataset.add(item3);

        CardDataImpl item4 = new CardDataImpl();
        cityname = "Athens";
        item4.setCity_name(cityname);
        item4.setMainBackgroundResource(R.drawable.bg_greece);
        item4.setHeadBackgroundResource(R.drawable.greece);
        item4.setHeadTitle("아테네");
        city.setCity_name(cityname);
        item4.setListItems(prepareCommentsArray());
         dataset.add(item4);

        CardDataImpl item5 = new CardDataImpl();
        cityname = "Bangkok";
        item5.setCity_name(cityname);
        item5.setMainBackgroundResource(R.drawable.nature);
        item5.setHeadBackgroundResource(R.drawable.thiland);
        item5.setHeadTitle("방콕");
        city.setCity_name(cityname);
        item5.setListItems(prepareCommentsArray());
        dataset.add(item5);

        CardDataImpl item6 = new CardDataImpl();
        cityname = "Beijing";
        item6.setCity_name(cityname);
        item6.setMainBackgroundResource(R.drawable.bg_china);
        item6.setHeadBackgroundResource(R.drawable.china);
        item6.setHeadTitle("베이징");
        city.setCity_name(cityname);
        item6.setListItems(prepareCommentsArray());
        dataset.add(item6);


    }

    public List<ECCardData> getDataset() {
       // Collections.shuffle(dataset);
        return dataset;
    }

    private List<Comment> prepareCommentsArray() {
        Random random = new Random();
        List<Comment> comments = new ArrayList<>();
        comments.addAll(Arrays.asList(
                new Comment(R.drawable.user, "Aaron Bradley", "When the sensor experiments for deep space, all mermaids accelerate mysterious, vital moons.", "jan 12, 2014"),
                new Comment(R.drawable.user, "Barry Allen", "It is a cold powerdrain, sir.", "jun 1, 2015"),
                new Comment(R.drawable.user, "Bella Holmes", "Particle of a calm shield, control the alignment!", "sep 21, 1937"),
                new Comment(R.drawable.user, "Caroline Shaw", "The human kahless quickly promises the phenomenan.", "may 23, 1967"),
                new Comment(R.drawable.user, "Connor Graham", "Ionic cannon at the infinity room was the sensor of voyage, imitated to a dead pathway.", "sep 1, 1972"),
                new Comment(R.drawable.user, "Deann Hunt", "Vital particles, to the port.", "aug 13, 1995"),
                new Comment(R.drawable.user, "Ella Cole", "Stars fly with hypnosis at the boldly infinity room!", "nov 18, 1952"),
                new Comment(R.drawable.user, "Jayden Shaw", "Hypnosis, definition, and powerdrain.", "apr 1, 2013"),
                new Comment(R.drawable.user, "Jerry Carrol", "When the queen experiments for nowhere, all particles control reliable, cold captains.", "nov 14, 1964"),
                new Comment(R.drawable.user, "Lena Lukas", "When the c-beam experiments for astral city, all cosmonauts acquire remarkable, virtual lieutenant commanders.", "may 4, 1965"),
                new Comment(R.drawable.user, "Leonard Kim", "Starships walk with love at the cold parallel universe!", "jul 3, 1974"),
                new Comment(R.drawable.user, "Mark Baker", "Friendship at the bridge that is when quirky green people yell.", "dec 24, 1989")));
        Collections.shuffle(comments);
        return comments.subList(0, 6 + random.nextInt(5));
    }

}
