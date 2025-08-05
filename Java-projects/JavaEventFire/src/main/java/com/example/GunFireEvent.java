package com.example;

public class GunFireEvent {
    private final int remainingAmmo;
    private final String soundEffect;

    public GunFireEvent(int remainingAmmo, String soundEffect) {
        this.remainingAmmo = remainingAmmo;
        this.soundEffect = soundEffect;
    }

    public int getRemainingAmmo() {
        return remainingAmmo;
    }

    public String getSoundEffect() {
        return soundEffect;
    }
}
