package gr.nrallakis.tichu.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import gr.nrallakis.tichu.server.game.GamePlayerActions;

public class Packets {

    public static class CreateRoom {
        public String roomName;
        public String roomPassword;
        public int winningScore;
        public int timeToPlay;
    }

    public static class RoomInfo {
        public String name;
    }

    public static class JoinRoom {
        public int roomId;
    }

    public static class StartGame {
        public int roomId;
    }

    public static class GameStarted {
    }

    public static class Login {
        public String username, id;
    }

    public static class LoginSuccessful {
    }

    public static class Rooms {
        public String rooms;
    }

    public static class GetRooms {
    }

    public static class JoinAccepted {
        public boolean accepted;
    }

    public static class GuestLogin {
        public String id;
    }

    public static class GuestInfo {
        public String username, id;
    }

    public static class GetStatsPacket {
    }

    public static class RoomCreated {
        public int id;
    }

    public static class RoomPlayers {
        public String rightPlayerJson, topPlayerJson, leftPlayerJson;
    }

    public static class GetRoomPlayers { public int roomId; }

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        ObjectSpace.registerClasses(kryo);
        kryo.register(Packets.CreateRoom.class);
        kryo.register(Packets.RoomInfo.class);
        kryo.register(Packets.JoinRoom.class);
        kryo.register(Packets.JoinAccepted.class);
        kryo.register(Packets.StartGame.class);
        kryo.register(Packets.GameStarted.class);
        kryo.register(Packets.Login.class);
        kryo.register(LoginSuccessful.class);
        kryo.register(Packets.GetRooms.class);
        kryo.register(Packets.Rooms.class);
        kryo.register(Packets.GuestInfo.class);
        kryo.register(Packets.GuestLogin.class);
        kryo.register(Packets.RoomCreated.class);
        kryo.register(Packets.RoomPlayers.class);
        kryo.register(Packets.GetRoomPlayers.class);
        kryo.register(GamePlayerActions.class);
        kryo.register(String.class);
    }
}
