package gr.nrallakis.tichu.core;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.esotericsoftware.kryonet.Connection;

import gr.nrallakis.tichu.networking.TichuServer;
import gr.nrallakis.tichu.server.game.Room;
import gr.nrallakis.tichu.server.game.RoomSerializer;

public class RoomManager {

    private TichuServer server;
    private Array<Room> rooms;

    public RoomManager(TichuServer server) {
        this.server = server;
        rooms = new Array<Room>();
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
        rooms.removeIndex(roomIndex);
        server.roomListUpdated();
    }

    public Room getRoomContaining(Connection connection) {
        String id = connection.toString();
        for (Room room : rooms) {
            if (room.contains(id)) return room;
        }
        return null;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

    @SuppressWarnings("rawtypes")
    public String getRoomsData() {
        Json json = new Json();
        json.setSerializer(Room.class, new RoomSerializer());
        return json.toJson(rooms);
    }

    public int size() {
        return rooms.size;
    }
}
