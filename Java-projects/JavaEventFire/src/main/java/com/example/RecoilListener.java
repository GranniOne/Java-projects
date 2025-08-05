package com.example;

public class RecoilListener implements GunFireListener {
    @Override
    public void onGunFired(GunFireEvent event) {
        System.out.println("Recoil applied.");
    }
}