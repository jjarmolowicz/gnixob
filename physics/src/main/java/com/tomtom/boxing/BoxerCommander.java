package com.tomtom.boxing;

public class BoxerCommander {
    private BoxerCommand command;

    BoxerCommand getCommand() {
        return command;
    }

    public void setCommand(BoxerCommand command) {
        this.command = command;
    }
}
