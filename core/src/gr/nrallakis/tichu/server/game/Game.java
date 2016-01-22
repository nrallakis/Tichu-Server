package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class Game implements IGame {

    private Combination lastCombination;
    private Deck deck;
    private Timer time;
    private int[] score;
    private int round;
    private int turn;
    private String lastPlayFrom;
    private boolean hasStarted;
    /* The players */
    private Player[] players;
    private Player turnPlayer;

    /* Interface to send players game states and actions */
    private IGameActions gameActions;

    public Game(Player[] players) {
        this.players = players;
		ObjectSpace objectSpace = new ObjectSpace();
		for (int i = 0; i < 4; i++) {
			objectSpace.addConnection(players[i].getConnection());
		}
        score = new int[2];
    }

    public void init(Connection[] players) {

        // Initialize deck

        //Initialize the players

        //Find who plays first
    }

    public void startGame() {
        round = 0;
        hasStarted = true;
        deck = new Deck();
        startRound();
    }

    public void startRound() {
        round++;

        //Deal 8 cards to players
        //Wait for Grand Tichu call
        //Deal 6 cards to players
        //Wait for players to exchange cards
        //Set turn to the player who has the mahjong
        //The flow continues with events from GameListener
    }

    public void endRound() {
        //Add points to each team
        //If one team exceeds the winning score :
        //Call endGame()
        //Else : call startRound()
    }

    public void endGame() {
        hasStarted = false;
        //Change players points to the db
        //Send gameFinished packet to players
    }

    public void dealCards() {
        //Send with rmi a json
        //with all the 14 cards
    }

    public void nextTurn() {
        //Increment turn (0-3)
        turn = ++turn % 4;
        turnPlayer = players[turn];
    }

    @Override
    public void pass(String id) {
        //Increment turn
        System.out.println("Player: " + id + " PASSED");
    }

    @Override
    public void playCards(String id, String comb) {
        //Check if the new combination is stronger than the last
        //Increment round
        System.out.println("PLAYED CARDS");
    }

    @Override
    public void leave(String id) {
        //Call endRound()
        //Call endGame()
        System.out.println("LEAVED");
    }

    private Player getPlayer(String id) {
        for (Player p : players) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    @Override
    public void giveCards(String threeCards) {
        /** */
    }

}
