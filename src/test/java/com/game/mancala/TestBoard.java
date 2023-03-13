package com.game.mancala;

import com.game.mancala.model.Board;
import com.game.mancala.model.Pit;
import com.game.mancala.model.PitHouse;
import com.game.mancala.model.PitStore;
import com.game.mancala.model.Player;
import com.game.mancala.model.PlayerNumber;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestBoard {

    @Test
    public void testBoardShouldHaveTwelveHouses() {
        Board board = Board.create();
        List<PitHouse> houses = board.getPitHouses();
        Map<PlayerNumber, List<PitHouse>> sides = houses.stream().collect(Collectors.groupingBy(PitHouse::getPitOwner));
        assertEquals(sides.get(PlayerNumber.ONE).size(), 6);
        assertEquals(sides.get(PlayerNumber.TWO).size(), 6);
    }

    @Test
    public void testBoardShouldHaveTwoStores() {
        Board board = Board.create();
        List<PitStore> stores = board.getPitStores();
        Map<PlayerNumber, List<PitStore>> sides = stores.stream().collect(Collectors.groupingBy(PitStore::getPitOwner));
        assertEquals(sides.get(PlayerNumber.ONE).size(), 1);
        assertEquals(sides.get(PlayerNumber.TWO).size(),1);
    }

    @Test
    public void tesBoardShouldHaveTwoPlayers() {
        Board board = Board.create();
        List<Player> players = board.getPlayers();
        assertEquals(players.get(0).getNum(),PlayerNumber.ONE);
        assertEquals(players.get(1).getNum(),PlayerNumber.TWO);
        assertEquals(players.size(), 2);
    }

    @Test
    public void testHousesShouldHaveMutualOpposites() {
        Board board = Board.create();
        List<Player> players = board.getPlayers();
        List<PitHouse> housesOne = players.get(0).getPitHouses();
        List<PitHouse> housesTwo = players.get(1).getPitHouses();
        assertEquals(housesOne.get(0).getOppositePit().get(),housesTwo.get(5));
        assertEquals(housesOne.get(1).getOppositePit().get(),housesTwo.get(4));
        assertEquals(housesOne.get(2).getOppositePit().get(),housesTwo.get(3));
        assertEquals(housesOne.get(3).getOppositePit().get(),housesTwo.get(2));
        assertEquals(housesOne.get(4).getOppositePit().get(),housesTwo.get(1));
        assertEquals(housesOne.get(5).getOppositePit().get(),(housesTwo.get(0)));
        assertEquals(housesTwo.get(0).getOppositePit().get(),housesOne.get(5));
        assertEquals(housesTwo.get(1).getOppositePit().get(),housesOne.get(4));
        assertEquals(housesTwo.get(2).getOppositePit().get(),housesOne.get(3));
        assertEquals(housesTwo.get(3).getOppositePit().get(),housesOne.get(2));
        assertEquals(housesTwo.get(4).getOppositePit().get(),housesOne.get(1));
        assertEquals(housesTwo.get(5).getOppositePit().get(), housesOne.get(0));
    }

    @Test
    public void testAllPitsShouldFormACycle() {
        Board board = Board.create();
        Pit first = board.getPitHouses().get(0);
        Pit pit = first;

        Set<Pit> all = new HashSet<>();
        all.add(pit);

        for (int i = 0; i < 14; i++) {
            pit = pit.nextPit();
            all.add(pit);
        }

        assertEquals(pit, first);
        assertEquals(all.size(),14);
    }

}
