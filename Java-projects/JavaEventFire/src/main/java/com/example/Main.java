package com.example;

public class Main {
    public static void main(String[] args) {
        Gun gun = new Gun(5);  // Gun starts with 5 ammo

        gun.addGunFireListener(new AmmoListener());
        gun.addGunFireListener(new SoundListener());
        gun.addGunFireListener(new RecoilListener());

        // Fire the gun and see the effects
        gun.fire();  // First shot
        gun.fire();  // Second shot
        gun.fire();  // Third shot
    }
}
