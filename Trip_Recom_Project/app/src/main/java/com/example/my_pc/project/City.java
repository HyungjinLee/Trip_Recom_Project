package com.example.my_pc.dbproject;
import com.example.my_pc.dbproject.interfaces.Comment;
import java.util.List;

public class City {

    public City(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(int exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }


    public int getSafety() {
        return safety;
    }

    public void setSafety(int safety) {
        this.safety = safety;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    private String city_name;   // 도시명 - 기본키
    private String nation;      // 나라
    private String currency;    // 화폐단위
    private int exchange_rate;  // 환율 - /1만원 기준
    private int air;            // 공기 오염 정도
    private int safety;         // 안전 수치
    private int wifi;           // 와이파이 정도
    private int english;        // 영어 잘 하는 수치
    private float rate;         // 평점

    public List<Comment> getCommentList() {
        return CommentList;
    }

    private List<Comment> CommentList; // 모든 유저가 남긴 댓글


    public void setCommentList(List<Comment> commentList) {
        CommentList = commentList;
    }

}
