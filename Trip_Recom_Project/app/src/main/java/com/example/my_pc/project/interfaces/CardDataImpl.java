package com.example.my_pc.dbproject.interfaces;

import com.example.my_pc.dbproject.R;
import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class  CardDataImpl implements ECCardData<Comment>{

    private String headTitle;
    private Integer mainBackgroundResource;
    private Integer headBackgroundResource;
    private Integer personPictureResource;


    // 객관적 수치 ->  인터넷, 환율, 대기오염
    // 주관적 수치 -> 안전, 영어, 인터넷, 평점

    private int currency;
    private int air;
    private int wifi;
    private int english;
    private int safety;

    public int getSafety() {
        return safety;
    }

    public void setSafety(int safety) {
        this.safety = safety;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    private String city_name;

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    private List<Comment> listItems;

    public CardDataImpl() {
        Random rnd = new Random();
        this.currency = 50 + rnd.nextInt(950);
        this.air = 35 + rnd.nextInt(170);
        this.wifi = 10 + rnd.nextInt(1000);
        this.english = 100 + rnd.nextInt(100);
        this.safety = 100 + rnd.nextInt(100);
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }


    public Integer getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    public void setHeadBackgroundResource(Integer headBackgroundResource) {
        this.headBackgroundResource = headBackgroundResource;
    }

    public Integer getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    public void setMainBackgroundResource(Integer mainBackgroundResource) {
        this.mainBackgroundResource = mainBackgroundResource;
    }

    public Integer getPersonPictureResource() {
        return personPictureResource;
    }

    public void setPersonPictureResource(Integer personPictureResource) {
        this.personPictureResource = personPictureResource;
    }

    @Override
    public List<Comment> getListItems() {
        return listItems;
    }
    public void setListItems(List<Comment> listItems) {
        this.listItems = listItems;
    }
}
