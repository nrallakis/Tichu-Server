package gr.nrallakis.tichu.networking;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import gr.nrallakis.tichu.core.AccountManager;
import gr.nrallakis.tichu.networking.Packets.*;
import gr.nrallakis.tichu.server.game.Player;
import gr.nrallakis.tichu.server.game.Room;

/**
 * Note: Clients hold an id to their name, can be accesed with {@link #toString()}
 */

public class ServerListener extends Listener {

    private TichuServer server;
    private PacketHandler packetHandler;

    public ServerListener(TichuServer server) {
        this.server = server;
        packetHandler = new PacketHandler(server);
    }

    @Override
    public void connected(Connection connection) {
        server.addLobbyPlayer(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        Array<Room> rooms = server.getRoomManager().getRooms();
        for (int i = 0; i < rooms.size; i++) {
            Room room = rooms.get(i);
            if (room.contains(connection.toString())) {
                room.removePlayer(connection);
                if (room.isEmpty()) server.getRoomManager().removeRoom(i);
                break;
            }
        }
        server.removePlayer(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        packetHandler.recieved(connection, object);
    }

}
