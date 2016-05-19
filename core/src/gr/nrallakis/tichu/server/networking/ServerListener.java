package gr.nrallakis.tichu.server.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import gr.nrallakis.tichu.server.game.Player;
import gr.nrallakis.tichu.server.lobby.Room;

public class ServerListener extends Listener {

    private TichuServer server;
    private PacketHandler packetHandler;

    public ServerListener(TichuServer server) {
        this.server = server;
        this.packetHandler = new PacketHandler(server);
    }

    @Override
    public void connected(Connection connection) {
        server.addLobbyPlayer(new Player(connection));
    }

    @Override
    public void disconnected(Connection connection) {
        Player player = server.getPlayer(connection);
        Room room = player.getRoomJoinedTo();
        room.removePlayer(player);
        if (room.isEmpty()) {
            server.getRoomManager().removeRoom(room);
        }
        server.removePlayer(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        packetHandler.received(connection, object);
    }

}
