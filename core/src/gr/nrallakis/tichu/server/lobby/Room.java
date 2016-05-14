package gr.nrallakis.tichu.server.lobby;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import java.util.Arrays;

import gr.nrallakis.tichu.server.game.Game;
import gr.nrallakis.tichu.server.game.GameConnection;
import gr.nrallakis.tichu.server.game.Player;
import gr.nrallakis.tichu.server.networking.Packets.GameStarted;
import gr.nrallakis.tichu.server.networking.Packets.RoomPlayers;

public class Room {

    private static int nextId;

    private int id;
    private Player[] players;
    private String name;
    private Game game;
    private int timeToPlay;
    private int winningScore;

    /** Responsible for rmi connection */
    private ObjectSpace objectSpace;

    public Room(RoomProperties roomProperties) {
        this.name = roomProperties.name;
        this.winningScore = roomProperties.winningScore;
        this.timeToPlay = roomProperties.secondsToPlay;
        this.id = nextId++;
        this.objectSpace = new ObjectSpace();
        this.players = new Player[4];
    }

    /** Adds a player to the room and the objectSpace connection. */
    public boolean addPlayer(Player player) {
        if (isFull()) return false;
        for (int i = 0; i < 4; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
        objectSpace.addConnection(player.getConnection());
        System.out.println(Arrays.toString(players));
        return true;
    }

    public void removePlayer(Connection playerConnection) {
        for (int i = 0; i < 4; i++) {
            if (players[i] == null) continue;
            if (players[i].getId().equals(playerConnection.toString())) {
                players[i] = null;
            }
        }
        objectSpace.removeConnection(playerConnection);
    }

    /** Sends the room players status to each player on the room */
    public void onPlayersChanged() {
        RoomPlayers packet = new RoomPlayers();
        packet.players = players;
        System.out.println(Arrays.toString(players));
        for (Player player : players) {
            if (player == null) continue;
            player.sendPacket(packet);
        }
    }

    /** Sets a player ready. When all players are ready
     * the game starts. Called when setPlayerReady packet has
     * been received on the server listener.
     * @param connection The player who pressed ready.
     */
    public void setPlayerReady(Connection connection) {
        for (Player player : players) {
            if (player == null) continue;
            if (player.getConnection().equals(connection)) {
                player.setReady(true);
            }
        }
        if (allPlayersReady()) {
            startGame();
        }
    }

    private boolean allPlayersReady() {
        for (Player p : players) {
            if (p == null) return false;
            if (!p.isReady()) return false;
        }
        return true;
    }

    /** Starts the game, initializes the game object
     * with the 4 players of the room and then informs them
     * that the game starts */
    public void startGame() {
        game = new Game(players);
        GameConnection remoteConnection = game;
        objectSpace.register(id, remoteConnection);
        GameStarted packet = new GameStarted();
        packet.gameConnectionId = id;
        for (Player player : players) {
            player.sendPacket(packet);
        }
        game.startGame();
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        int count = 0;
        for (Player p : players) {
            if (p == null) continue;
            if (p.getConnection() != null) count++;
        }
        return count;
    }

    public boolean contains(String id) {
        for (Player p : players) {
            if (p == null) continue;
            if (p.getId().equals(id)) return true;
        }
        return false;
    }

    public boolean isEmpty() { return getPlayerCount() == 0; }

    public boolean isFull() {
        return getPlayerCount() == 4;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        if (!(obj.getClass().equals(this.getClass()))) {
            return false;
        }
        Room other = (Room) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + name.length();
        return result;
    }
}
