package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Connection;

public class Game implements GamePlayerActions {

    private boolean hasStarted;

    private int[] teamsScores;
    private int round;
    private int turn;

    private Player[] players;
    private Player turnPlayer;

    private Timer time;
    private Deck deck;
    private CardCombination lastCombination;
    private String lastPlayFrom;

    private GamePlayerUpdater gameObserverUpdater;

    public Game(Player[] players) {
        this.players = players;
        teamsScores = new int[2];
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
        //If one team exceeds the winning teamsScores :
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

    private void nextTurn() {
        //Increment turn (0-3)
        turn = ++turn % 4;
        turnPlayer = players[turn];
    }

    @Override
    public void pass(String playerId) {
        if (!isPlayerTurn(playerId)) return;
        nextTurn();
        System.out.println("Player: " + playerId + " PASSED");
    }

    @Override
    public void callTichu(String playerId) {
        gameObserverUpdater.playerTichu(playerId);
    }

    @Override
    public void callGrandTichu(String playerId) {
        gameObserverUpdater.playerGrandTichu(playerId);
    }

    @Override
    public void exchangeThreeCards(String playerId, Card left, Card top, Card right) {

    }

    @Override
    public void bomb(String playerId, CardCombination combination) {

    }

    @Override
    public void playCards(String playerId, CardCombination newCombination) {
        if (!isPlayerTurn(playerId)) return;
        if (newCombination.isStrongerThan(lastCombination)) {
            lastCombination = newCombination;
            gameObserverUpdater.playerPlayedCards(newCombination);
        }
        nextTurn();
        System.out.println("PLAYED CARDS");
    }

    private Player playerWithTurn() {
        return players[turn];
    }

    private boolean isPlayerTurn(String playerId) {
        return players[turn].getId().equals(playerId);
    }

}
