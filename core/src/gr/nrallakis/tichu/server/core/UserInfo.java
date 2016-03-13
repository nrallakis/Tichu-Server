package gr.nrallakis.tichu.server.core;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UserInfo implements Comparable<UserInfo> {

    public String password;
    public String username;
    public String id;
    public Image image;
    public int rankPoints;

    public UserInfo() {
        this("null", "null", "null");
    }

    public UserInfo(String username, String password, String id) {
        this.password = password;
        this.username = username;
        this.id = id;
        this.rankPoints = 1000;
    }

    @Override
    public int compareTo(UserInfo info) {
        if (username.equals(info.username)) return 0;
        return rankPoints < info.rankPoints ? -1 : 1;
    }

    public UserInfo(String fromString) {
        String[] info = fromString.split(":");
        this.username = info[0];
        this.password = info[1];
        this.id = info[2];
        this.rankPoints = Integer.parseInt(info[3]);
    }

    @Override
    public String toString() {
        return username + ":" + password + ":" + id + ":" + rankPoints;
    }
}
