package com.company;

import org.json.JSONObject;

import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String ipAddr = "http://ip-api.com/json";
        String ipJson = new String();
        try{
            URL ipUrl = new URL(ipAddr);
            Scanner in = new Scanner(ipUrl.openStream());
            while(in.hasNext())
                ipJson += in.nextLine();
            in.close();

        }catch (Exception e){
            System.err.println("URL ERROR OCCUR!");
            System.exit(1);
        }

        //JSON object 생성
        JSONObject obj = new JSONObject(ipJson);
        Double lat = (Double) obj.get("lat");
        Double lon = (Double) obj.get("lon");
        String city = (String) obj.get("city");
        String isp = (String) obj.get("isp");

        String weatherAddr = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=48f885b0c1286e3fc25a557e6e665597";
        String weatherJson = new String();
        
        try{
            URL weatherUrl = new URL(weatherAddr);
            Scanner in = new Scanner(weatherUrl.openStream());
            while(in.hasNext())
                weatherJson += in.nextLine();
            in.close();

        }catch (Exception e){
            System.err.println("URL ERROR OCCUR!");
            System.exit(1);
        }

        obj = new JSONObject(weatherJson);
        String currentWeather = (String) obj.getJSONArray("weather").getJSONObject(0).get("main");
        Double temperature = (Double) obj.getJSONObject("main").get("temp");

        String location = city;
        if(isp != null){
            location += "(" + isp + ")";
        }
        System.out.println(location + "의 현재 날씨 : " + currentWeather);
        System.out.printf("현재 기온 : %.2f ℃", kToC(temperature));
    }

    public static double kToC(double kelvin){ return kelvin - 273.15; }

}
