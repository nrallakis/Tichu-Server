package gr.nrallakis.tichu.server.game;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class RoomSerializer implements Json.Serializer<Room> {

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
}
