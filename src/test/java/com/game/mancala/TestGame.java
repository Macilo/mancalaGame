package com.game.mancala;

import com.game.mancala.model.Game;
import com.game.mancala.model.Player;
import com.game.mancala.model.PlayerNumber;
import com.game.mancala.model.Result;
import com.game.mancala.model.Status;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestGame {
    private Game game;
    @Before
    public void before(){
        game = Game.getInstance();
        game.setActivePlayer(game.getActivePlayer());
    }

    @Test
    public void testPlayerOneShouldTakeFirstTurn() {
        Game game = Game.getInstance();
        Player player = game.getActivePlayer();
        assertEquals(player.getNum(),PlayerNumber.TWO);
    }

    @Test
    public void testShouldRejectMoveByInactivePlayer() throws IllegalStateException {
        Game game = Game.getInstance();
        try {
            game.move(PlayerNumber.TWO, 1);
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIsGameActive() {
        Game game = Game.getInstance();
        Result result = game.move(PlayerNumber.ONE, 2);
        game.displayBoard(result.getBoard());
        assertEquals(result.getStatus(), Status.ACTIVE);
    }

    @Test
    public void testShouldAllowAlternateTurns() {
        Game game = Game.getInstance();
        Result result = game.move(PlayerNumber.TWO, 2);
        game.displayBoard(result.getBoard());
        assertEquals(result.getNext(), PlayerNumber.ONE);
    }

    @Test
    public void testShouldGetAnotherTurnWhenLandingInOwnStore() {
        Game game = Game.getInstance();
        Result result = game.move(PlayerNumber.ONE, 1);
        game.displayBoard(result.getBoard());
        assertEquals(result.getNext(), PlayerNumber.ONE);
    }

}
