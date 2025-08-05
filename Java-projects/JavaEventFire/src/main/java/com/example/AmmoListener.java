package com.example;

public class AmmoListener implements GunFireListener {
    @Override
    public void onGunFired(GunFireEvent event) {
        System.out.println("Ammo reduced. Remaining ammo: " + event.getRemainingAmmo());
    }
}