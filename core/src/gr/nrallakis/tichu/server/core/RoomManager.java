package gr.nrallakis.tichu.server.core;

import com.badlogic.gdx.utils.Json;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.List;

import gr.nrallakis.tichu.server.networking.TichuServer;
import gr.nrallakis.tichu.server.lobby.Room;
import gr.nrallakis.tichu.server.lobby.RoomSerializer;

public class RoomManager {

    private TichuServer server;
    private List<Room> rooms;

    public RoomManager(TichuServer server) {
        this.server = server;
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        server.roomListUpdated();
    }

    public Room getRoom(int id) {
        for (Room r : rooms) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    public void removeRoom(int roomIndex) {
        rooms.remove(roomIndex);
        server.roomListUpdated();
    }

    public Room getRoomContaining(Connection connection) {
        String id = connection.toString();
        for (Room room : rooms) {
            if (room.contains(id)) return room;
        }
        return null;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @SuppressWarnings("rawtypes")
    public String getRoomsData() {
        Json json = new Json();
        json.setSerializer(Room.class, new RoomSerializer());
        return json.toJson(rooms);
    }

    public int size() {
        return rooms.size();
    }
}
