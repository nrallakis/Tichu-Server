package gr.nrallakis.tichu.server;

import com.esotericsoftware.kryonet.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.Game;
import gr.nrallakis.tichu.server.game.Player;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestGame extends TichuTestCase {

    Player[] players;
    Game game;

    @Before
    public void setUp() {
        Connection connection = mock(Connection.class);
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(connection);
            when(connection.toString()).thenReturn("" + i);
        }

        game = new Game(players);

    }

    @After
    public void tearDown() {
        game = null;
        players = null;
    }

    @Test
    public void testFindWhoPlaysFirst() {
        Card[] c1 = new Card[14];
        for (int i = 0; i < 13; i++) {
            c1[i] = new Card(0, i+2);
        }
        c1[13] = new Card(Card.MAHJONG, 1);
        players[0].addCards(c1);

        Card[] c2 = new Card[14];
        for (int i = 0; i < 13; i++) {
            c2[i] = new Card(1, i+2);
        }
        c2[13] = new Card(Card.DOGS, 0);
        players[1].addCards(c2);

        Card[] c3 = new Card[14];
        for (int i = 0; i < 14; i++) {
            c3[i] = new Card(2, i+2);
        }
        c3[13] = new Card(Card.DRAGON, 25);
        players[2].addCards(c3);

        Card[] c4 = new Card[14];
        for (int i = 0; i < 14; i++) {
            c4[i] = new Card(3, i+2);
        }
        c4[13] = new Card(Card.PHOENIX, 1);
        players[3].addCards(c4);

        assertEquals(players[0], game.findWhoPlaysFirst());
    }

    @Test
    public void test8CardsAreDealtWhenGameStarts() {
        game.startGame();
        for (Player p : players) {
            assertEquals(8, p.getHand().size());
        }
    }
}
