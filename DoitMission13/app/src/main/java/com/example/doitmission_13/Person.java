package com.example.doitmission_13;

public class Person {
    String name;
    String birth;
    String mobile;

    public Person(String name, String birth, String mobile) {
        this.name = name;
        this.birth = birth;
        this.mobile = mobile;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
