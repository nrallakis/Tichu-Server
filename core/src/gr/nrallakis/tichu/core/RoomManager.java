package gr.nrallakis.tichu.core;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import gr.nrallakis.tichu.networking.TichuServer;
import gr.nrallakis.tichu.server.game.Room;

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

    public Array<Room> getRooms() {
        return rooms;
    }

    @SuppressWarnings("rawtypes")
    public String getRoomsData() {
        Json json = new Json();
        json.setSerializer(Room.class, new Json.Serializer<Room>() {
            @Override
            public void write(Json json, Room room, Class knownType) {
                json.writeObjectStart();
                json.writeValue("name", room.getName());
                json.writeValue("player_count", room.getPlayerCount());
                json.writeValue("id", room.getId());
                json.writeObjectEnd();
            }

            @Override
            public Room read(Json json, JsonValue jsonData, Class type) {
                return null;
            }
        });

        return json.toJson(rooms);
    }

    public int size() {
        return rooms.size;
    }
}
