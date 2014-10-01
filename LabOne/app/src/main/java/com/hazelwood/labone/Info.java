package com.hazelwood.labone;

import java.io.Serializable;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class Info implements Serializable {
    public static final long serialVersionUID = 123456789L;

    String Fname, Lname;
    int age;

    public Info(String _first, String _last, int _age){
        Fname = _first;
        Lname = _last;
        age = _age;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public int getAge() {
        return age;
    }
}
