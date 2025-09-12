package com.example.apiversioningtutorial.dto;

public class UserV3 extends UserV2 {
    private int age;

    public UserV3(String firstName, String lastName, int age) {
        super(firstName, lastName);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
