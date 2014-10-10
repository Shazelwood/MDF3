package com.hazelwood.maps;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Hazelwood on 10/9/14.
 */
public class Form implements Serializable{
    private static final long serialVersionUID = 46543445L;

    String fieldONE, fieldTWO, image;
    double latitude, longitude;
    File file;

    public Form(String f1, String f2, String img, File path,double lat, double lng){
        fieldONE = f1;
        fieldTWO = f2;
        latitude = lat;
        longitude = lng;
        file = path;
        image = img;
    }

    public Form(){

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setFieldONE(String fieldONE) {
        this.fieldONE = fieldONE;
    }

    public void setFieldTWO(String fieldTWO) {
        this.fieldTWO = fieldTWO;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFieldONE() {
        return fieldONE;
    }

    public String getFieldTWO() {
        return fieldTWO;
    }
}
