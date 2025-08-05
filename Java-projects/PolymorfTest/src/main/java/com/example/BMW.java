package com.example;

public class BMW extends Vehicle{

    BMW() {
        this.vehicleHorn("BMW horn");
    }

    @Override
    void vehicleHorn(String horn) {
        System.out.println("BMW horn");
    }

    

}

class Audi extends Vehicle {

    Audi() {
        this.vehicleHorn("Vehicle horn");


    }
    @Override
    void vehicleHorn(String horn) {
        System.out.println("Audi horn");
    }
}
