package com.game.mancala;

import com.game.mancala.model.Pit;
import com.game.mancala.model.PitHouse;
import com.game.mancala.model.PitStore;
import com.game.mancala.model.PlayerNumber;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPit {

    @Test
    public void testShouldStoreStonesInHouse() {
        Pit house = new PitHouse(PlayerNumber.ONE, 6);
        assertEquals(6, (int) house.count());
    }

    @Test
    public void testPitStoreShouldBeginEmpty() {
        Pit pitStore = new PitStore(PlayerNumber.ONE);
        assertEquals(0, (int) pitStore.count());
    }

    @Test
    public void testShouldPointToTheNextPit() {
        Pit a = new PitHouse(PlayerNumber.ONE, 6);
        PitStore b = new PitStore(PlayerNumber.ONE);
        PitHouse c = new PitHouse(PlayerNumber.ONE, 6);

        a.setNextPit(b);
        b.setNextPit(c);

        assertEquals(a.nextPit(), b);
        assertEquals(b.nextPit(), c);
    }

    @Test
    public void testShouldBeAbleToTakeStonesFromHouse() {
        PitHouse house = new PitHouse(PlayerNumber.ONE, 6);
        assertEquals(6, (int) house.count());

        Integer taken = house.take();

        assertEquals(6, (int) taken);
        assertEquals(0, (int) house.count());
    }

    @Test
    public void testShouldBeAbleToSowStoneInHouse() {
        PitHouse house = new PitHouse(PlayerNumber.ONE, 0);
        house.sow();
        assertEquals(1, (int) house.count());
    }

    @Test
    public void testShouldBeAbleToSowStoneInStore() {
        PitStore store = new PitStore(PlayerNumber.ONE);
        store.sow();
        assertEquals(1, (int) store.count());
    }

    @Test
    public void testShouldBeAbleToSowMultipleStonesInStore() {
        PitStore store = new PitStore(PlayerNumber.ONE);
        store.sow(6);
        assertEquals(6, (int) store.count());
    }
}
