package com.hazelwood.widgetlab;

import java.io.Serializable;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class Weather implements Serializable {

    String conditions,lastTime;
    int temperatureNOW, temperatureYESTERDAY ,temperatureTOMORROW, tempHigh, tempLow;

    public Weather(){}

    public Weather(String cond, String time, int tempNOW, int tempYESTERDAY, int tempTOMORROW,int high, int low){
        tempHigh = high;
        tempLow = low;
        temperatureNOW = tempNOW;
        temperatureYESTERDAY = tempYESTERDAY;
        temperatureTOMORROW = tempTOMORROW;
        conditions = cond;
        lastTime = time;
    }

    public void setTempHigh(int tempHigh) {
        this.tempHigh = tempHigh;
    }

    public void setTempLow(int tempLow) {
        this.tempLow = tempLow;
    }

    public void setTemperatureNOW(int temperatureNOW) {
        this.temperatureNOW = temperatureNOW;
    }

    public void setTemperatureTOMORROW(int temperatureTOMORROW) {
        this.temperatureTOMORROW = temperatureTOMORROW;
    }

    public void setTemperatureYESTERDAY(int temperatureYESTERDAY) {
        this.temperatureYESTERDAY = temperatureYESTERDAY;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

    public int getTemperatureNOW() {
        return temperatureNOW;
    }

    public int getTemperatureTOMORROW() {
        return temperatureTOMORROW;
    }

    public int getTemperatureYESTERDAY() {
        return temperatureYESTERDAY;
    }

    public String getConditions() {
        return conditions;
    }

    public String getLastTime() {
        return lastTime;
    }
}
