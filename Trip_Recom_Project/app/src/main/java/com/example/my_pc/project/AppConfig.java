package com.example.my_pc.dbproject;

// 192.168.43.72
public class AppConfig {
    public static String url = "172.16.251.153";
    // 서버 유저의 로그인 URL

    public static String URL_LOGIN = "http://"+url+"/login.php";

    // 서버 유저의 회원가입 URL
    public static String URL_REGISTER = "http://"+url+"/register.php";

    // 서버 유저의 추천 URL
    public static String URL_RECOM = "http://"+url+"/recommendation.php";

    // 추천 테이블의 마지막 인덱스 얻기 URL
    public static String URL_LAST = "http://"+url+"/getlastindex.php";

    // 도시에 대한 모든 추천 정보 얻기
    public static String URL_GETCITYRECOM = "http://"+url+"/get_city_recommendation.php";

}
