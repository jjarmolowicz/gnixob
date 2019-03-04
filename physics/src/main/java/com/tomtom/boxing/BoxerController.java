package com.tomtom.boxing;

public interface BoxerController {

    default void init() {

    }

    BoxerCommand tick();
}
