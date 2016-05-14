package gr.nrallakis.tichu.server.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gr.nrallakis.tichu.server.core.RoomManager;
import gr.nrallakis.tichu.server.database.DBManager;

public class TichuServer {

    public static final int PORT = 44444;

    private Server server;
    private RoomManager roomManager;

    private List<Connection> lobbyPlayers;
    private List<Connection> roomPlayers;

    public TichuServer() throws IOException {
        server = new Server();
        Network.registerPackets(server);
        server.bind(PORT);
    }

    public void start() {
        lobbyPlayers = new ArrayList<>();
        roomPlayers = new ArrayList<>();
        roomManager = new RoomManager(this);
        server.start();
        server.addListener(new ServerListener(this));
    }

    public String generateGuestId() {
        System.out.println("GENERATING ID");
        return String.valueOf(DBManager.getInstance().nextId());
    }

    public void addLobbyPlayer(Connection c) {
        roomPlayers.remove(c);
        lobbyPlayers.add(c);
    }

    public void addRoomPlayer(Connection c) {
        lobbyPlayers.remove(c);
        roomPlayers.add(c);
    }

    public void removePlayer(Connection connection) {
        lobbyPlayers.remove(connection);
        roomPlayers.remove(connection);
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
        Packets.Rooms packet = new Packets.Rooms();
        packet.rooms = roomManager.getRoomsData();
        sendPacketToLobby(packet);
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public int getPlayersCount() {
        return lobbyPlayers.size() + roomPlayers.size();
    }
}
