package fr.eletutour.spel.model;

import org.springframework.stereotype.Component;

@Component("myCar")
public class Car {
    private String make = "GoodCar";
    private int year = 2021;
    private Engine engine = null; // This will be null

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public boolean isNewerThan(int year) {
        return this.year > year;
    }

    public Engine getEngine() {
        return engine;
    }
}
