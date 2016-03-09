package gr.nrallakis.tichu.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gr.nrallakis.tichu.core.RoomManager;
import gr.nrallakis.tichu.database.DBManager;
import gr.nrallakis.tichu.networking.Packets.Rooms;

public class TichuServer {

    public static final int PORT = 44444;

    private Server server;
    private RoomManager roomManager;
    private List<Connection> lobbyPlayers;

    public TichuServer() throws IOException {
        server = new Server();
        Network.registerPackets(server);
        server.bind(PORT);
    }

    public void start() {
        roomManager = new RoomManager(this);
        lobbyPlayers = new ArrayList<>();
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

    public void removePlayer(Connection connection) {
        if (lobbyPlayers.contains(connection)) {
            lobbyPlayers.remove(connection);
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

    private EndPoint getEndPoint() {
        return server;
    }
}
