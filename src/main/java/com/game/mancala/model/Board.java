package com.game.mancala.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private List<PitHouse> pitHouses;

    private List<PitStore> pitStores;

    private List<Player> players;

    private final static int NUMBER_OF_STONES = 6;

    private final static int LENGTH_OF_PIT_HOUSES = 6;

    private static Board board;

    private Board() {}

    public static Board create() {
        if(board == null){
            board = create(NUMBER_OF_STONES, LENGTH_OF_PIT_HOUSES);
        }
        return board;
    }

    public static Board create(int numberOfStones, int length) {
        List<Integer> stones = Stream.generate(() -> numberOfStones).limit(length).collect(Collectors.toList());
        return from(stones, 0, stones, 0);
    }

    public static Board from(List<Integer> p1PitHouses, int p1PitStore, List<Integer> p2PitHouses, int p2PitStore) {
        LinkedList<PitHouse> player1PitHouses = buildPitHouses(PlayerNumber.ONE, p1PitHouses);
        LinkedList<PitHouse> player2PitHouses = buildPitHouses(PlayerNumber.TWO, p2PitHouses);
        mutuallyOpposite(player1PitHouses, player2PitHouses);

        PitStore player1Store = new PitStore(PlayerNumber.ONE, p1PitStore);
        PitStore player2Store = new PitStore(PlayerNumber.TWO, p2PitStore);

        circular(player1PitHouses, player1Store, player2PitHouses, player2Store);

        Player playerOne = new Player(PlayerNumber.ONE, player1PitHouses, player1Store);
        Player playerTwo = new Player(PlayerNumber.TWO, player2PitHouses, player2Store);

        Board board = new Board();
        board.pitHouses = new ArrayList<>(player1PitHouses);
        board.pitHouses.addAll(player2PitHouses);
        board.pitStores = Arrays.asList(player1Store, player2Store);
        board.players = Arrays.asList(playerOne, playerTwo);

        return board;
    }

    private static LinkedList<PitHouse> buildPitHouses(PlayerNumber playerNumber, List<Integer> stones) {
        LinkedList<PitHouse> houses = new LinkedList<>();
        houses.addLast(new PitHouse(playerNumber, stones.get(0)));
        while (houses.size() < stones.size()) {
            PitHouse house = new PitHouse(playerNumber, stones.get(houses.size()));
            houses.getLast().setNextPit(house);
            houses.addLast(house);
        }
        return houses;
    }

    private static void mutuallyOpposite(List<PitHouse> player1PitHouses, List<PitHouse> player2PitHouses) {
        for (int i=0; i< player1PitHouses.size(); i++) {
            PitHouse one = player1PitHouses.get(i);
            PitHouse two = player2PitHouses.get(player2PitHouses.size() - i - 1);
            one.setOppositePit(two);
            two.setOppositePit(one);
        }
    }

    private static void circular(LinkedList<PitHouse> player1PitHouses, PitStore player1Store, LinkedList<PitHouse> player2PitHouses, PitStore player2Store) {
        player1PitHouses.getLast().setNextPit(player1Store);
        player1Store.setNextPit(player2PitHouses.getFirst());
        player2PitHouses.getLast().setNextPit(player2Store);
        player2Store.setNextPit(player1PitHouses.getFirst());
    }

    public List<PitHouse> getPitHouses() {
        return pitHouses;
    }

    public List<PitStore> getPitStores() {
        return pitStores;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
