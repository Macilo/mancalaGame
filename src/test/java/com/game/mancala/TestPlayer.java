package com.game.mancala;

import com.game.mancala.model.Pit;
import com.game.mancala.model.PitHouse;
import com.game.mancala.model.PitStore;
import com.game.mancala.model.Player;
import com.game.mancala.model.PlayerNumber;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {

    @Test
    public void testPlayerShouldSowStonesOnTurn() {
        PitHouse last = new PitHouse(PlayerNumber.ONE, 0);
        PitHouse middle = new PitHouse(PlayerNumber.ONE,0);
        PitHouse first = new PitHouse(PlayerNumber.ONE,2);
        PitStore end = new PitStore(PlayerNumber.ONE);

        first.setNextPit(middle).setNextPit(last).setNextPit(end);

        Player player = new Player(PlayerNumber.ONE, Arrays.asList(first, middle, last), end);
        Pit landed = player.turn(1);

        assertEquals(landed, last);
        assertEquals(0, (int) first.count());
        assertEquals(1, (int) middle.count());
        assertEquals(1, (int) last.count());
    }

    @Test
    public void testPlayerCannotPickFromEmptyPitHouse() throws IllegalArgumentException {
            Player player = new Player(PlayerNumber.ONE, Collections.singletonList(new PitHouse(PlayerNumber.ONE, 0)), new PitStore(PlayerNumber.ONE));
        try {
            player.turn(1);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testPlayerCannotPickFromPitHouseOutsideTheirRange() throws IllegalArgumentException {
            Player player = new Player(PlayerNumber.ONE, Collections.singletonList(new PitHouse(PlayerNumber.ONE, 0)), new PitStore(PlayerNumber.ONE));
            try {
                player.turn(0);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
    }

    @Test
    public void testPlayerSkipsOppPlayerPitStore() {
        PitHouse myHouse = new PitHouse(PlayerNumber.ONE,3);
        PitStore myStore = new PitStore(PlayerNumber.ONE);
        PitHouse oppPlayerPitHouse = new PitHouse(PlayerNumber.TWO,0);
        PitStore oppPlayerPitStore = new PitStore(PlayerNumber.TWO);

        myHouse.setNextPit(myStore)
                .setNextPit(oppPlayerPitHouse)
                .setNextPit(oppPlayerPitStore)
                .setNextPit(myHouse);

        Player player = new Player(PlayerNumber.ONE, Collections.singletonList(myHouse), myStore);
        Pit landed = player.turn(1);

        assertEquals(landed,myHouse);
        assertEquals(java.util.Optional.ofNullable(myHouse.count()), Optional.of(1));
        assertEquals(java.util.Optional.ofNullable(myStore.count()), Optional.of(1));
        assertEquals(java.util.Optional.ofNullable(oppPlayerPitHouse.count()), Optional.of(1));
        assertEquals(Optional.ofNullable(oppPlayerPitStore.count()), Optional.of(0));
    }
}
