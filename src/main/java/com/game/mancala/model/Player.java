package com.game.mancala.model;

import java.util.List;

public class Player {

    private PlayerNumber num;

    private final List<PitHouse> pitHouses;

    private final PitStore pitStore;

    public List<PitHouse> getPitHouses() {
        return pitHouses;
    }

    public PitStore getPitStore() {
        return pitStore;
    }

    public PlayerNumber getNum() {
        return num;
    }

    public Player(final PlayerNumber num, final List<PitHouse> pitHouses, final PitStore pitStore){
        this.num = num;
        this.pitHouses = pitHouses;
        this.pitStore = pitStore;
    }


    public Pit turn(int pitHouseNum) {
        PitHouse pitHouse = getHouse(pitHouseNum);
        checkHasStones(pitHouse);
        Pit pit = takeTurn(pitHouse);
        if (shouldCaptureOppositeStones(pit)) {
            pitStore.sow(pit.take());
            pitStore.sow(pit.capture());
        }
        return pit;
    }

    public boolean complete() {
        return pitHouses.stream().allMatch(PitHouse::isEmpty);
    }

    public void finish() {
        for (PitHouse house: pitHouses) {
            pitStore.sow(house.take());
        }
    }

    public int score() {
        return pitStore.count();
    }

    private boolean shouldCaptureOppositeStones(final Pit pit) {
        return pit.count() == 1 && pit.getOppositePit().isPresent();
    }

    private void checkHasStones(final PitHouse pitHouse) {
        if (pitHouse.isEmpty()) {
            throw new IllegalArgumentException("You must pick from a PitHouse with stones to take turn. Try Again!");
        }
    }

    private Pit takeTurn(final PitHouse pitHouse) {
        Integer stones = pitHouse.take();
        Pit pit = pitHouse;
        while (stones > 0) {
            pit = pit.nextPit();
            if (pit.isSowable(num)) {
                stones--;
                pit.sow();
            }
        }
        return pit;
    }

    private PitHouse getHouse(int pitHouseNum) {

        if (pitHouseNum < 1 || pitHouseNum > pitHouses.size()) {
            throw new IllegalArgumentException("A PitHouse number must be between 1 and " + pitHouses.size() + ". Try Again!");
        }
        return this.pitHouses.get(pitHouseNum - 1);
    }
}
