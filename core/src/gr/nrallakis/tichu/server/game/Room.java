package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import gr.nrallakis.tichu.networking.Packets.GameStarted;
import gr.nrallakis.tichu.networking.Packets.PlayersInfo;

public class Room {

    private static int nextId;

    private Player[] players;
    private String name;
    private Game game;
    private int id;

    /** Responsible for rmi connection */
    private ObjectSpace objectSpace;

    public Room(String name) {
        this.name = name;
        this.players = new Player[4];
        this.id = ++nextId;
        objectSpace = new ObjectSpace();
    }

    /** Adds a player to the room and the objectSpace connection. */
    public void addPlayer(Player player) {
        if (getPlayerCount() < 4) {
            //Add player
            if (players[0] == null) players[0] = player;
            else if (players[1] == null) players[1] = player;
            else if (players[2] == null) players[2] = player;
            else if (players[3] == null) players[3] = player;
            objectSpace.addConnection(player.getConnection());
            updatePlayerStates();
        }
    }

    /** Sends the room players status to each player on the room */
    public void updatePlayerStates() {
        //Broadcast the players information to the room
        PlayersInfo packet = new PlayersInfo();
        packet.playerInfos = playersToJson();
        System.out.println(packet.playerInfos);
        broadcast(packet);
    }

    public String playersToJson() {
        Json json = new Json();
        json.setSerializer(Player.class, new Json.Serializer<Player>() {
            @Override
            public void write(Json json, Player player, @SuppressWarnings("rawtypes") Class knownType) {
                json.writeObjectStart();
                json.writeValue("name", player.getName());
                json.writeValue("points", player.getPoints());
                json.writeValue("id", player.getId());
                json.writeObjectEnd();
            }

            @Override
            public Player read(Json json, JsonValue jsonData, @SuppressWarnings("rawtypes") Class type) {
                return null;
            }
        });
        return json.toJson(players);
    }

    public void removePlayer(Connection player) {
        for (int i = 0; i < 4; i++) {
            if (players[i] == null) continue;
            if (players[i].getId().equals(player.toString())) {
                players[i] = null;
            }
        }
        objectSpace.removeConnection(player);
        updatePlayerStates();
    }

    /** Sets a player ready. When all players are ready
     * the game starts. Called when playerReady packet has
     * been received on the server listener.
     * @param connection The player who pressed ready.
     */
    public void playerReady(Connection connection) {
        for (Player p : players) {
            if (p == null) continue;
            if (p.getConnection().equals(connection)) {
                p.ready = true;
                System.out.println(connection.toString() + " is ready");
            }
        }

        for (Player p : players) {
            if (p == null || !p.ready) return;
        }
        startGame();
    }

    /** Starts the game. Furthermore, it inits the game object
     * with the 4 players of the room and then informs them
     * that the game starts */
    public void startGame() {
        game = new Game(players);
        objectSpace.register(id, game);
        GameStarted packet = new GameStarted();
        for (Player p : players) {
            p.getConnection().sendTCP(packet);
        }
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
        Room other = (Room) obj;
        return this.getId() == other.getId();
    }

    /** Send a packet to every player on the room.
     * @param packet The packet to be sent */
    private void broadcast(Object packet) {
        for (Player player : players) {
            if (player == null) continue;
            player.getConnection().sendTCP(packet);
        }
    }
}
