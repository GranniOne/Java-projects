package com.example;

public class SoundListener implements GunFireListener {
    @Override
    public void onGunFired(GunFireEvent event) {
        System.out.println("Playing sound: " + event.getSoundEffect());
    }
}