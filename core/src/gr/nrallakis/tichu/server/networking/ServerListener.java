package gr.nrallakis.tichu.server.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.List;

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
        server.addLobbyPlayer(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        List<Room> rooms = server.getRoomManager().getRooms();
        for (int roomIndex = 0; roomIndex < rooms.size(); roomIndex++) {
            Room room = rooms.get(roomIndex);
            if (room.contains(connection.toString())) {
                room.removePlayer(connection);
                room.updatePlayerStates();
                if (room.isEmpty()) {
                    server.getRoomManager().removeRoom(roomIndex);
                }
                break;
            }
        }
        server.roomListUpdated();
        server.removePlayer(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        packetHandler.received(connection, object);
    }

}
