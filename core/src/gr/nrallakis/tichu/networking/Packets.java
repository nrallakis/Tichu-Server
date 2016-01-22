package gr.nrallakis.tichu.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

import gr.nrallakis.tichu.server.game.IGame;

public class Packets {

    public static class CreateRoom {
        public String name;
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

    public static class LoginSuccesful {
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

    public static class PlayersInfo {
        public String playerInfos;
    }

    public static class GetPlayersInfo {
    }

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        ObjectSpace.registerClasses(kryo);
        kryo.register(CreateRoom.class);
        kryo.register(RoomInfo.class);
        kryo.register(JoinRoom.class);
        kryo.register(JoinAccepted.class);
        kryo.register(StartGame.class);
        kryo.register(GameStarted.class);
        kryo.register(Login.class);
        kryo.register(LoginSuccesful.class);
        kryo.register(GetRooms.class);
        kryo.register(Rooms.class);
        kryo.register(GuestInfo.class);
        kryo.register(GuestLogin.class);
        kryo.register(GetStatsPacket.class);
        kryo.register(RoomCreated.class);
        kryo.register(PlayersInfo.class);
        kryo.register(GetPlayersInfo.class);
        kryo.register(IGame.class);
    }
}
