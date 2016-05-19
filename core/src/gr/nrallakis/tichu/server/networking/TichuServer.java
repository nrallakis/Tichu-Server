package gr.nrallakis.tichu.server.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gr.nrallakis.tichu.server.core.RoomManager;
import gr.nrallakis.tichu.server.database.DBManager;
import gr.nrallakis.tichu.server.game.Player;

public class TichuServer {

    public static final int PORT = 44444;

    private Server server;
    private RoomManager roomManager;

    private Map<Connection, Player> lobbyPlayers;
    private Map<Connection, Player> roomPlayers;

    public TichuServer() throws IOException {
        server = new Server();
        Network.registerPackets(server);
        server.bind(PORT);
    }

    public void start() {
        lobbyPlayers = new HashMap<>();
        roomPlayers = new HashMap<>();
        roomManager = new RoomManager(this);
        server.start();
        server.addListener(new ServerListener(this));
    }

    public String generateGuestId() {
        System.out.println("GENERATING ID");
        return String.valueOf(DBManager.getInstance().nextId());
    }

    public void addLobbyPlayer(Player p) {
        roomPlayers.remove(p.getConnection());
        lobbyPlayers.put(p.getConnection(), p);
    }

    public void addRoomPlayer(Player p) {
        lobbyPlayers.remove(p.getConnection());
        roomPlayers.put(p.getConnection(), p);
    }

    public void removePlayer(Connection connection) {
        lobbyPlayers.remove(connection);
        roomPlayers.remove(connection);
    }

    public void sendPacketToLobby(Object packet) {
        for (Player p : lobbyPlayers.values()) {
            p.sendPacket(packet);
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

    public Player getPlayer(Connection connection) {
        Player player = lobbyPlayers.get(connection);
        if (player == null) {
            player = roomPlayers.get(connection);
        }
        return player;
    }
}
