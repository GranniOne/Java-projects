package com.example;

import java.util.ArrayList;
import java.util.List;

public class Gun {
    private final List<GunFireListener> listeners = new ArrayList<>();
    private int ammoCount;

    public Gun(int initialAmmo) {
        ammoCount = initialAmmo;
    }

    public void addGunFireListener(GunFireListener listener) {
        listeners.add(listener);
    }

    public void fire() {
        if (ammoCount > 0) {
            System.out.println("Bang!");

            // Create an event with ammo data and sound effect
            GunFireEvent event = new GunFireEvent(ammoCount, "gunshot.wav");

            // Notify all listeners with the event
            for (GunFireListener listener : listeners) {
                listener.onGunFired(event);
            }

            // Reduce ammo after firing
            ammoCount--;
        } else {
            System.out.println("Out of ammo!");
        }
    }
}



