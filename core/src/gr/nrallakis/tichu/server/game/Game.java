package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

import gr.nrallakis.tichu.networking.GamePackets.*;

public class Game implements GameConnection {

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

    private GamePlayerUpdater gamePlayerUpdater;
    private GameState currentState;

    public Game(Player[] players) {
        this.players = players;
        this.teamsScores = new int[2];
        this.gamePlayerUpdater = new GamePlayerUpdater(players);
    }

    public void startGame() {
        round = 0;
        hasStarted = true;
        deck = new Deck();
        startRound();
    }

    public void startRound() {
        Gdx.app.log("SERVER", "Round started");
        round++;

        //Deal 8 cards to players
        dealEightCards();

        //Start 1 minute timer

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

    private void dealEightCards() {
        Gdx.app.log("SERVER", "Dealing 8 cards");
        currentState = GameState.CALL_FOR_GRAND;
        GiveCards packet = new GiveCards();
        Card[] eightCards = new Card[8];
        for (GamePlayer player : players) {
            for (int i = 0; i < 8; i++) {
                eightCards[i] = deck.drawCard();
            }
            packet.cards = eightCards;
            player.getConnection().sendTCP(packet);
        }
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
        Player player = getPlayer(playerId);
        if (player == null) return;
        player.setBet(Bet.TICHU);
        gamePlayerUpdater.playerTichu(playerId);
    }

    @Override
    public void callGrandTichu(String playerId) {
        Gdx.app.log("SERVER", "GRAND TICHU");
        Gdx.app.log("SERVER", "Player ID = " + playerId);
        Player player = getPlayer(playerId);
        if (player == null) return;
        player.setBet(Bet.GRAND_TICHU);
        gamePlayerUpdater.playerGrandTichu(playerId);
        System.out.println("Player: " + playerId + " GRAND_TICHU");
    }

    @Override
    public void exchangeThreeCards(String playerId, Card left, Card top, Card right) {
        int playerIndex = getPlayerIndex(playerId);
        switch (playerIndex) {
            case 0:
                sendCard(players[1], right);
                sendCard(players[2], left);
                sendCard(players[3], top);
                break;
            case 1:
                sendCard(players[2], right);
                sendCard(players[3], left);
                sendCard(players[0], top);
                break;
            case 2:
                sendCard(players[3], right);
                sendCard(players[0], left);
                sendCard(players[1], top);
                break;
            case 3:
                sendCard(players[0], right);
                sendCard(players[1], left);
                sendCard(players[2], top);
                break;
        }
    }

    @Override
    public void bomb(String playerId, CardCombination combination) {
        int playerIndex = getPlayerIndex(playerId);
        if (combination.isStrongerThan(lastCombination)) {
            lastCombination = combination;
            turn = playerIndex;
            gamePlayerUpdater.playerBombed(playerId, combination);
        }
    }

    @Override
    public void dealCardsLeft(String playerId) {
        Player player = getPlayer(playerId);
        GiveCards cardsLeft = new GiveCards();
        cardsLeft.cards = deck.drawSixCards();
        player.sendPacket(cardsLeft);
    }

    private void sendCard(Player player, Card card) {
        GiveCards cardPacket = new GiveCards();
        cardPacket.cards = new Card[]{ card };
        player.sendPacket(cardPacket);
    }

    @Override
    public void playCards(String playerId, CardCombination newCombination) {
        if (!isPlayerTurn(playerId)) return;
        if (newCombination.isStrongerThan(lastCombination)) {
            lastCombination = newCombination;
            gamePlayerUpdater.playerPlayedCards(newCombination);
        }
        nextTurn();
        System.out.println("PLAYED CARDS");
    }

    public GamePlayerUpdater getGamePlayerUpdater() {
        return gamePlayerUpdater;
    }

    private Player getPlayer(String playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    private int getPlayerIndex(String playerId) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player.getId().equals(playerId)) {
                return i;
            }
        }
        throw new IllegalStateException();
    }

    private boolean isPlayerTurn(String playerId) {
        return players[turn].getId().equals(playerId);
    }

}
