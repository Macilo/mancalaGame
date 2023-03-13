package com.game.mancala.model;

import java.util.Optional;

public abstract class Pit {

    protected int stones;

    private Pit nextPit;

    protected final PlayerNumber pitOwner;

    public Pit (PlayerNumber pitOwner, int stones) {
        this.pitOwner = pitOwner;
        this.stones = stones;
    }

    public Integer count() {
        return stones;
    }

    public Pit nextPit() {
        return nextPit;
    }

    public Pit setNextPit(Pit nextPit) {
        this.nextPit = nextPit;
        return nextPit;
    }

    public PlayerNumber getPitOwner() {
        return pitOwner;
    }

    public void sow() {
        this.stones++;
    }

    abstract boolean isSowable(PlayerNumber player);

    public boolean isEmpty() {
        return this.stones == 0;
    }

    public Optional<PitHouse> getOppositePit() {
        return Optional.empty();
    }

    public Integer capture() {
        if (this.getOppositePit().isPresent()) {
            return 0;
        }
        return this.getOppositePit().get().take();
    }

    public Integer take() {
        return 0;
    }
}
