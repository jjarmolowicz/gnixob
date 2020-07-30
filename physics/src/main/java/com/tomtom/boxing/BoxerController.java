package com.tomtom.boxing;

public interface BoxerController {

    default void init(boolean isBlack) {

    }

    BoxerCommand tick(Physics physics);
}
