package com.example.chapter7_4;

public class Person {
    String name;
    String mobile;

    public Person(String mobile, String name) {
        this.mobile = mobile;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMobile(){
        return this.mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
}
