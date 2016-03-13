package gr.nrallakis.tichu.server.networking;

import com.esotericsoftware.kryonet.Connection;

import gr.nrallakis.tichu.server.core.AccountManager;
import gr.nrallakis.tichu.server.game.Player;
import gr.nrallakis.tichu.server.lobby.Room;
import gr.nrallakis.tichu.server.lobby.RoomProperties;

public class PacketHandler {

    private TichuServer server;

    /** @param server The server's packet to handle */
    public PacketHandler(TichuServer server) {
        this.server = server;
    }

    public void received(Connection connection, Object object) {
        if (object instanceof Packets.Login) {
            login(connection, (Packets.Login) object);
        }
        else if (object instanceof Packets.GuestLogin) {
            guestLogin(connection, (Packets.GuestLogin) object);
        }
        else if (object instanceof Packets.CreateRoom) {
            createRoom(connection, (Packets.CreateRoom) object);
        }
        else if (object instanceof Packets.JoinRoom) {
            joinRoom(connection, (Packets.JoinRoom) object);
        }
        else if (object instanceof Packets.StartGame) {
            startGame(connection, (Packets.StartGame) object);
        }
        else if (object instanceof Packets.GetRooms) {
            getRooms(connection);
        }
        else if (object instanceof Packets.GetRoomPlayers) {
            getRoomPlayers(connection, (Packets.GetRoomPlayers) object);
        }
        else if (object instanceof GamePackets.GiveCards) {
            receivedCards((GamePackets.GiveCards) object);
        }
    }

    private void receivedCards(GamePackets.GiveCards object) {
        
    }

    private void getRoomPlayers(Connection connection, Packets.GetRoomPlayers object) {
        Room roomToGetPlayers = server.getRoomManager()
                .getRoomContaining(connection);
        roomToGetPlayers.updatePlayerStates();
    }

    private void getRooms(Connection connection) {
        if (server.getRoomManager().size() == 0) return;
        Packets.Rooms packet = new Packets.Rooms();
        packet.rooms = server.getRoomManager().getRoomsData();
        connection.sendTCP(packet);
    }

    private void startGame(Connection connection, Packets.StartGame object) {
        server.getRoomManager().getRoomContaining(connection).setPlayerReady(connection);
    }

    private void joinRoom(Connection connection, Packets.JoinRoom object) {
        boolean accepted;
        int id = object.roomId;
        Room room = server.getRoomManager().getRoom(id);
        if (room.isFull()) {
            accepted = false;
        } else {
            room.addPlayer(new Player(connection));
            room.updatePlayerStates();
            accepted = true;
            server.roomListUpdated();
        }
        Packets.JoinAccepted packet = new Packets.JoinAccepted();
        packet.accepted = accepted;
        connection.sendTCP(packet);
    }

    private void createRoom(Connection connection, Packets.CreateRoom packet) {
        RoomProperties properties = new RoomProperties();
        properties.name = packet.roomName;
        properties.winningScore = packet.winningScore;
        properties.secondsToPlay = packet.timeToPlay;

        Room room = new Room(properties);
        room.addPlayer(new Player(connection));
        room.updatePlayerStates();

        server.getRoomManager().addRoom(room);
        server.roomListUpdated();

        Packets.RoomCreated packet2 = new Packets.RoomCreated();
        connection.sendTCP(packet2);
    }

    private void guestLogin(Connection connection, Packets.GuestLogin object) {
        String id = object.id;
        if (id == null) {
            id = server.generateGuestId();
            Packets.GuestInfo packet = new Packets.GuestInfo();
            packet.id = id;
            packet.username = "Guest-" + packet.id;
            connection.sendTCP(packet);
        }
        AccountManager.getInstance().login(id);
        connection.setName(id);
        Packets.LoginSuccessful packet = new Packets.LoginSuccessful();
        connection.sendTCP(packet);
    }

    private void login(Connection connection, Packets.Login object) {
        String username = object.username;
        String id = object.id;
        connection.setName(id);
        AccountManager.getInstance().login(username, id);
        Packets.LoginSuccessful packet = new Packets.LoginSuccessful();
        connection.sendTCP(packet);
    }
}
