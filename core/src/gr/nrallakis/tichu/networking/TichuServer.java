package gr.nrallakis.tichu.networking;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import gr.nrallakis.tichu.core.RoomManager;
import gr.nrallakis.tichu.database.DBManager;
import gr.nrallakis.tichu.networking.Packets.Rooms;

public class TichuServer {

    private Server server;
    private RoomManager roomManager;
    private Array<Connection> lobbyPlayers;

    public TichuServer() throws IOException {
        server = new Server();
        Packets.register(server);
        server.bind(44444);
    }

    public void start() {
        roomManager = new RoomManager(this);
        lobbyPlayers = new Array<Connection>();
        server.start();
        server.addListener(new ServerListener(this));
    }

    public String generateGuestId() {
        System.out.println("GENERATING ID");
        return String.valueOf(DBManager.getInstance().nextId());
    }

    public void addLobbyPlayer(Connection c) {
        lobbyPlayers.add(c);

        Rooms packet = new Rooms();
        packet.rooms = roomManager.getRoomsData();
        if (packet.rooms == null) return;
        c.sendTCP(packet);
    }

    public void removePlayer(Connection c) {
        if (lobbyPlayers.contains(c, true)) {
            lobbyPlayers.removeValue(c, true);
        }
    }

    public int getOnlinePlayerCount() {
        return server.getConnections().length;
    }

    public void sendPacketToLobby(Object packet) {
        for (Connection c : lobbyPlayers) {
            c.sendTCP(packet);
        }
    }

    /**
     * Called when a rooms is added, removed or changed
     * Used to inform client for room changes
     */
    public void roomListUpdated() {
        Rooms packet = new Rooms();
        packet.rooms = roomManager.getRoomsData();
        sendPacketToLobby(packet);
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }
}
