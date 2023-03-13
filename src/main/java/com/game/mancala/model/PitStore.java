package com.game.mancala.model;

public class PitStore extends Pit {


    public PitStore(PlayerNumber pitOwner, int stones) {
        super(pitOwner, stones);
    }

    public PitStore(PlayerNumber pitOwner){
        this(pitOwner, 0);
    }

    public void sow(int i) {
        stones += i;
    }

    @Override
    boolean isSowable(PlayerNumber player) {
       return player.equals(pitOwner);
    }

    @Override
    public String toString() {
        return "PitStore{" +
                "stones=" + stones +
                ", owner=" + pitOwner +
                '}';
    }
}
