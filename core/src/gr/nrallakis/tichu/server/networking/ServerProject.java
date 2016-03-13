package gr.nrallakis.tichu.server.networking;

import com.badlogic.gdx.ApplicationAdapter;

import java.io.IOException;

public class ServerProject extends ApplicationAdapter {

    private TichuServer server;

    @Override
    public void create() {
        try {
            server = new TichuServer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TichuServer getServer() {
        return server;
    }
}
