package com.tomtom.boxing;

public interface BoxerVisualState {
    float getX();
    float getY();
    boolean reversed();
    boolean isHit();
    short leftArmOffset();
    short rightArmOffset();
}
