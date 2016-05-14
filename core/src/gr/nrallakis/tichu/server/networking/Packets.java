package gr.nrallakis.tichu.server.networking;


import gr.nrallakis.tichu.server.game.Player;

public class Packets {

    public static class CreateRoom extends Packets {
        public String roomName;
        public String roomPassword;
        public int winningScore;
        public int timeToPlay;
    }

    public static class RoomInfo extends Packets {
        public String name;
    }

    public static class JoinRoom extends Packets {
        public int roomId;
    }

    public static class StartGame extends Packets {
    }

    public static class GameStarted extends Packets {
        public int gameConnectionId;
        public String[] playerIds;
    }

    public static class Login extends Packets {
        public String username, id;
    }

    public static class LoginSuccessful extends Packets {
    }

    public static class Rooms extends Packets {
        public String rooms;
    }

    public static class GetRooms extends Packets {
    }

    public static class JoinAccepted extends Packets {
        public boolean accepted;
    }

    public static class GuestLogin extends Packets {
        public String id;
    }

    public static class GuestInfo extends Packets {
        public String username, id;
    }

    public static class GetStatsPacket extends Packets {
    }

    public static class RoomCreated extends Packets {
    }

    public static class RoomPlayers extends Packets {
        public Player[] players;
    }

    public static class GetRoomPlayers extends Packets {}
}
