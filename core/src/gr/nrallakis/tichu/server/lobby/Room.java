package gr.nrallakis.tichu.server.lobby;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import gr.nrallakis.tichu.server.networking.Packets;
import gr.nrallakis.tichu.server.game.Game;
import gr.nrallakis.tichu.server.game.GameConnection;
import gr.nrallakis.tichu.server.game.Player;

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
        this.players = new Player[4];
        this.id = ++nextId;
        this.objectSpace = new ObjectSpace();
    }

    /** Adds a player to the room and the objectSpace connection. */
    public void addPlayer(Player player) {
        if (getPlayerCount() < 4) {
            if (players[0] == null) players[0] = player;
            else if (players[1] == null) players[1] = player;
            else if (players[2] == null) players[2] = player;
            else if (players[3] == null) players[3] = player;
            objectSpace.addConnection(player.getConnection());
        }
    }

    /** Sends the room players status to each player on the room */
    public void updatePlayerStates() {
        for (int playerIndex = 0; playerIndex < players.length; playerIndex++) {
            Player player = players[playerIndex];
            if (player == null) continue;
            sendRoomPlayersTo(playerIndex);
        }
    }

    /** Sends all the players excluding the receiver
     * The json is sent to the orientation of the player */
    private void sendRoomPlayersTo(int playerIndex) {
        Player player = players[playerIndex];
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(players));
        Collections.rotate(playerList, -playerIndex);
        playerList.remove(0);

        Packets.RoomPlayers packet = new Packets.RoomPlayers();
        Player rightPlayer = playerList.get(0);
        if (rightPlayer != null) {
            packet.rightPlayerJson = rightPlayer.toJson();
        }
        Player topPlayer = playerList.get(1);
        if (topPlayer != null) {
            packet.topPlayerJson = topPlayer.toJson();
        }
        Player leftPlayer = playerList.get(2);
        if (leftPlayer != null) {
            packet.leftPlayerJson = leftPlayer.toJson();
        }

        player.getConnection().sendTCP(packet);
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

        for (Player player : players) {
            if (player == null || !player.isReady()) return;
        }
        startGame();
    }

    /** Starts the game, initializes the game object
     * with the 4 players of the room and then informs them
     * that the game starts */
    public void startGame() {
        game = new Game(players);
        GameConnection remoteConnection = game;
        objectSpace.register(id, remoteConnection);
        Packets.GameStarted packet = new Packets.GameStarted();
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
