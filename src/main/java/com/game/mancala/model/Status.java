package com.game.mancala.model;

public enum Status {

    ACTIVE("ACTIVE"),

    DRAW("It's a Tie"),

    PLAYER_ONE_WIN("Player 1 Wins!!"),

    PLAYER_TWO_WIN("Player 2 Wins!!");

    private String message;

    Status(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
