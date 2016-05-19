package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import gr.nrallakis.tichu.server.networking.GamePackets;
import gr.nrallakis.tichu.server.networking.GamePackets.ExchangeCards;
import gr.nrallakis.tichu.server.networking.GamePackets.GiveCards;

public class Game implements GameConnection {

    private boolean hasStarted;

    private int[] teamsScores;
    private int round;
    private int turn;

    private Player[] players;
    private Player turnPlayer;

    private Timer timer;
    private Deck deck;
    private CardCombination lastCombination;
    private String lastPlayFrom;

    private GamePlayerUpdater gamePlayerUpdater;

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
        lastCombination = null;  /** Empty combination */
        //Deal 8 cards to players
        dealEightCards();

        //Start 1 minute timer

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

    private void dealEightCards() {
        Gdx.app.log("SERVER", "Dealing 8 cards");
        GiveCards packet = new GiveCards();
        for (Player player : players) {
            Card[] cards = deck.drawEightCards();
            packet.cards = cards;
            player.sendPacket(packet);
            player.addCards(cards);
        }
    }

    /** Called when a client received the cards from the exchange */
    @Override
    public void receivedCards(String playerId) {
        Player player = getPlayer(playerId);
        player.setHasReceivedCards(true);
        if (allPlayersHaveReceivedCards()) {
            Player playerPlaying = findWhoPlaysFirst();
            gamePlayerUpdater.playerPlaysFirst(playerPlaying.getId());
            setTurn(playerPlaying);

            GamePackets.StartPlayingCards packet = new GamePackets.StartPlayingCards();
            packet.timeStarted = TimeUtils.nanoTime();
            gamePlayerUpdater.broadcast(packet);

        }
    }

    @Override
    public void dealCardsLeft(String playerId) {
        Player player = getPlayer(playerId);
        if (player.getHand().size() > 8) return;
        GiveCards cardsLeft = new GiveCards();
        Card[] theCards = deck.drawSixCards();
        cardsLeft.cards = theCards;
        player.sendPacket(cardsLeft);

        player.addCards(theCards);
        System.out.println(player.getId() + " , " + player.getHand().size());
        gamePlayerUpdater.playerHandSize(playerId, 14);
        if (allPlayersHaveFourteenCards()) {
            gamePlayerUpdater.broadcast(new GamePackets.StartExchange());
        }
    }

    /** Increments turn (0-3) and update turnPlayer */
    private void nextTurn() {
        turn = ++turn % 4;
        turnPlayer = players[turn];
    }

    @Override
    public void pass(String playerId) {
        if (!isPlayerTurn(playerId)) return;
        gamePlayerUpdater.playerPassed();
        nextTurn();
        System.out.println("Player: " + playerId + " PASSED");
    }

    @Override
    public void callTichu(String playerId) {
        Player player = getPlayer(playerId);
        if (player == null) return;

        /** Make sure the player hasn't played any cards */
        if (player.getHand().size() < 14) return;

        player.setBet(Bet.TICHU);
        gamePlayerUpdater.playerTichu(playerId);
        System.out.println("Player: " + playerId + " CALLED TICHU");
    }

    @Override
    public void callGrandTichu(String playerId) {
        Gdx.app.log("SERVER", "GRAND TICHU");
        Gdx.app.log("SERVER", "Player ID = " + playerId);
        Player player = getPlayer(playerId);
        if (player == null) return;
        player.setBet(Bet.GRAND_TICHU);
        gamePlayerUpdater.playerGrandTichu(playerId);
        System.out.println("Player: " + playerId + " CALLED GRAND TICHU");
    }

    @Override
    public void exchangeThreeCards(String playerId, Card left, Card top, Card right) {
        int playerIndex = getPlayerIndex(playerId);
        Player sender = players[playerIndex];
        switch (playerIndex) {
            /** Put each card to the players orientation. (0:left, 1:top, 2:right) */
            case 0:
                players[1].receivedExchangeCard(right, 0);
                players[2].receivedExchangeCard(top, 1);
                players[3].receivedExchangeCard(left, 2);
                break;
            case 1:
                players[2].receivedExchangeCard(right, 0);
                players[3].receivedExchangeCard(top, 1);
                players[0].receivedExchangeCard(left, 2);
                break;
            case 2:
                players[3].receivedExchangeCard(right, 0);
                players[0].receivedExchangeCard(top, 1);
                players[1].receivedExchangeCard(left, 2);
                break;
            case 3:
                players[0].receivedExchangeCard(right, 0);
                players[1].receivedExchangeCard(top, 1);
                players[2].receivedExchangeCard(left, 2);
                break;
        }

        sender.removeCards(left, top, right);
        sender.setHasExchanged(true);
        if (allPlayersHaveExchanged()) {
            sendExchangeCardsToEachPlayer();
            for (Player p : players) {
                System.out.println("Player: " + p.getId() + " hand: " + p.printCards());
            }
        }
        System.out.println("Player: " + playerId + " EXCHANGED CARDS");
    }

    private void sendExchangeCardsToEachPlayer() {
        ExchangeCards exchangeCards = new ExchangeCards();
        for (Player player : players) {
            exchangeCards.cards = player.getExchangeCardsReceived();
            player.sendPacket(exchangeCards);
        }
        Gdx.app.log("DEBUG", "PLAYER: successfully sent exchange cards");
    }

    private void setTurn(Player player) {
        this.turn = getPlayerIndex(player.getId());
    }

    @Override
    public void bomb(String playerId, CardCombination combination) {
        int playerIndex = getPlayerIndex(playerId);
        if (combination.isStrongerThan(lastCombination)) {
            lastCombination = combination;
            turn = playerIndex;
            gamePlayerUpdater.playerBombed(playerId, combination);
        }
        System.out.println("Player: " + playerId + " BOMBED");
    }

    private boolean allPlayersHaveFourteenCards() {
        for (Player player : players) {
            if (player.getHand().size() != 14)
                return false;
        }
        return true;
    }

    private boolean allPlayersHaveExchanged() {
        for (Player player : players) {
            if (!player.hasExchanged()) return false;
        }
        return true;
    }

    private boolean allPlayersHaveReceivedCards() {
        for (Player player : players) {
            if (!player.hasReceivedCards()) return false;
        }
        return true;
    }

    public Player findWhoPlaysFirst() {
        for (Player player : players) {
            if (player.hasMahjong()) return player;
        }
        return null;
    }

    private void sendCard(Player player, Card card) {
        GiveCards cardPacket = new GiveCards();
        cardPacket.cards = new Card[]{ card };
        player.sendPacket(cardPacket);
        player.getHand().add(card);
    }

    @Override
    public void playCards(String playerId, CardCombination newCombination) {
        Player player = getPlayer(playerId);
        if (!isPlayerTurn(player)) return;
        if (newCombination.isStrongerThan(lastCombination)) {
            lastCombination = newCombination;
            gamePlayerUpdater.playerPlayedCards(newCombination);
            player.removeCards(newCombination.getCards());
            nextTurn();
        }
        System.out.println("Player: " + playerId + " PLAYED CARDS ");
        for (Card c : newCombination.getCards()) {
            System.out.print(c + " ");
        }
    }

    private Player getPlayer(String connectionId) {
        for (Player player : players) {
            if (player.getId().equals(connectionId)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No player exists with id: " + connectionId);
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

    private boolean isPlayerTurn(Player player) { return isPlayerTurn(player.getId()); }

    private boolean isPlayerTurn(String playerId) {
        return players[turn].getId().equals(playerId);
    }

}
