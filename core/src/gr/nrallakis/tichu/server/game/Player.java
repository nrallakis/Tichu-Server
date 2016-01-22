package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;

import gr.nrallakis.tichu.core.AccountManager;

public class Player {

    private Connection connection;
    public boolean ready;
    private String id;
    private Array<Card> hand;
    private IGameActions gameActions;

    public Player(Connection c) {
        this.connection = c;
        this.id = connection.toString();
    }

    public void giveCard(Card card) {
        hand.add(card);

    }

    public Connection getConnection() {
        return connection;
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return AccountManager.getInstance().getAccountRankPoints(id);
    }

    public String getName() {
        return AccountManager.getInstance().getAccountName(id);
    }
}
