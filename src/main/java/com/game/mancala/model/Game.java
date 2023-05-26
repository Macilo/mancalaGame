package com.game.mancala.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game {

    private Board board;

    private Player player;

    private Status status;

    private static Game game;

    private Game() {
    }

    public static Game getInstance() {
        if(game == null){
            Board board = Board.create();
            game = new Game();
            game.board = board;
            game.player = board.getPlayers().get(0);
            game.status = Status.ACTIVE;

        }
        return game;
    }

    public Result move(final PlayerNumber num, int pitHouse) {
        if (!player.getNum().equals(num)) {
            throw new IllegalStateException(String.format("Player %s cannot take their turn yet", num));
        }
        Pit landed = player.turn(pitHouse);
        if (player.complete()) {
            Objects.requireNonNull(swap()).finish();
            status = declareWinner();
        }
        player = nextPlayer(landed);
        return new Result(status, player.getNum(), board);
    }

    private Status declareWinner() {
        List<Player> players = board.getPlayers();
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        if (player1.score() > player2.score()) {
            return Status.PLAYER_ONE_WIN;
        } else if (player1.score() < player2.score()) {
            return Status.PLAYER_TWO_WIN;
        }
        return Status.DRAW;
    }

    public Player nextPlayer(Pit landed) {
        if (landed.equals(player.getPitStore())) {
            System.out.println("Player  "  +
                    player.getNum()  + "  goes  again");
            return player;
        }
        return swap();
    }

    private Player swap() {
        List<Player> players = board.getPlayers();
        switch (player.getNum()) {
            case ONE:
                setActivePlayer(players.get(1));
                return players.get(1);
            case TWO:
                setActivePlayer(players.get(0));
                return players.get(0);
            default:
                return null;
        }
    }

    public void setActivePlayer(final Player player) {
        this.player = player;
    }

    public Player getActivePlayer() {
        return player;
    }

    public boolean  gameOver()  {
       return !status.equals(Status.ACTIVE);
    }

    public  void  displayBoard(Board board)  {
             String TEMPLATE = "\t\t\t\tPlayer Two \n" +
            "\t| %02d | %02d | %02d | %02d | %02d | %02d | \n" +
            "(%02d)                                 (%02d)\n" +
            "\t| %02d | %02d | %02d | %02d | %02d | %02d |\n" +
            "\t\t\t\t Player One";

        Player player1 = board.getPlayers().get(0);
        Player player2 = board.getPlayers().get(1);
        List<PitHouse> p2Rev = new ArrayList<>(player2.getPitHouses());

        Collections.reverse(p2Rev);
        List<Pit> pits = new ArrayList<>(p2Rev);
        pits.add(player2.getPitStore());
        pits.add(player1.getPitStore());
        pits.addAll(player1.getPitHouses());

            System.out.printf((TEMPLATE) + "%n", pits.stream()
                .map(Pit::count).toArray());
    }

    public void play() {
        do {
            displayBoard(board);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Player : " + getActivePlayer().getNum() + "'s turn!");
                System.out.print("Enter  a  pit  to  move from:  ");          // Prompt,
                System.out.flush();
                int i = Integer.parseInt(br.readLine()); //  get the move,
                Result result = move(getActivePlayer().getNum(), i);
                if (gameOver()) {
                    displayBoard(board);
                    System.out.println(result.getStatus().getMessage());
                }
            }catch (NumberFormatException e){
                System.out.println("Please only enter an integer! Try again");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!gameOver());

    }
}
