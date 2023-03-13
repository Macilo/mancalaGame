package com.game.mancala.model;

public class Result {
    private Status status;
    private PlayerNumber next;
    private Board board;

    public Result(Status status, PlayerNumber next, Board board) {
        this.status = status;
        this.next = next;
        this.board = board;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PlayerNumber getNext() {
        return next;
    }

    public void setNext(PlayerNumber next) {
        this.next = next;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
