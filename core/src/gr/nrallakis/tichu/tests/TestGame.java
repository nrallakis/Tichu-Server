package gr.nrallakis.tichu.tests;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;

import org.junit.Test;

import java.lang.reflect.Method;

import gr.nrallakis.tichu.networking.TichuServer;
import gr.nrallakis.tichu.server.game.Game;
import gr.nrallakis.tichu.server.game.GamePlayerUpdate;
import gr.nrallakis.tichu.server.game.GamePlayerUpdater;
import gr.nrallakis.tichu.server.game.Player;

import static org.junit.Assert.assertEquals;

public class TestGame extends NetworkingTestCase {

    Player[] gamePlayers;
    Game game;
    GamePlayerUpdater updater;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        TichuServer server = new TichuServer();
        Method getEndPoint = server.getClass().getDeclaredMethod("getEndPoint");
        getEndPoint.setAccessible(true);
        EndPoint endPoint = (EndPoint) getEndPoint.invoke(server, null);
        startEndPoint(endPoint);
        Client[] clients = new Client[4];
        gamePlayers = new Player[4];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client();
            startEndPoint(clients[i]);
            clients[i].connect(8000, "192.168.1.199", TichuServer.PORT);
            gamePlayers[i] = new Player(clients[i]);
        }
        game = new Game(gamePlayers);
        waitForThreads(8000);
        updater = game.getGamePlayerUpdater();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        game = null;
        gamePlayers = null;
        updater = null;
    }

    @Test
    public void testGamePlayerUpdater() {
        updater.playerPassed();
        assertLastGameUpdate(GamePlayerUpdate.PLAYER_PASSED);

        updater.playerTichu(gamePlayers[0].getId());
        assertLastGameUpdate(GamePlayerUpdate.PLAYER_TICHU);

        updater.playerPlayedCards(null);
        assertLastGameUpdate(GamePlayerUpdate.PLAYER_PLAYED_CARDS);

        updater.playerBombed(gamePlayers[0].getId(), null);
        assertLastGameUpdate(GamePlayerUpdate.PLAYER_BOMBED);

        updater.roundStarted();
        assertLastGameUpdate(GamePlayerUpdate.ROUND_STARTED);

        updater.roundFinished(null);
        assertLastGameUpdate(GamePlayerUpdate.ROUND_FINISHED);

        updater.gameFinished(null);
        assertLastGameUpdate(GamePlayerUpdate.GAME_FINISHED);
    }

    private void assertLastGameUpdate(GamePlayerUpdate expected) {
        waitForThreads();
        for (int i = 0; i < 4; i++) {
            assertEquals(expected, gamePlayers[i].getLastGameUpdate());
        }
    }

}
