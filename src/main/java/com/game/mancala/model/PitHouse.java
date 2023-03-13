package com.game.mancala.model;

import java.util.Optional;

public class PitHouse extends Pit{

    private PitHouse oppositePit;

    public PitHouse(PlayerNumber owner, int stones) {
        super(owner, stones);
    }

    @Override
    boolean isSowable(PlayerNumber player) {
        return true;
    }

    @Override
    public Integer take() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }

    @Override
    public Optional<PitHouse> getOppositePit() {
        return Optional.ofNullable(oppositePit);
    }

    public void setOppositePit(PitHouse oppositePit) {
        this.oppositePit = oppositePit;
    }

    @Override
    public String toString() {
        return "PitHouse{" +
                "stones=" + stones +
                ", owner=" + pitOwner +
                '}';
    }
}
