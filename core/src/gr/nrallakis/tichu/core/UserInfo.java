package gr.nrallakis.tichu.core;

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
    public int compareTo(UserInfo o) {
        if (username.equals(o.username)) return 0;
        return rankPoints < o.rankPoints ? -1 : 1;
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
        String string = username + ":" + password + ":" + id + ":" + rankPoints;
        return string;
    }
}
