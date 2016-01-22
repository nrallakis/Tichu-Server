package gr.nrallakis.tichu.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import gr.nrallakis.tichu.core.UserInfo;

public class TestAccountManager {

    FileHandle file;

    public void test() {
        file = Gdx.files.local("data/useraccounts.txt");

        UserInfo info = new UserInfo("nrallakis4@gmail.com", "Tilemachos4", "nrallakis");
        System.out.println(new UserInfo(info.toString()).toString());
    }

    private void testGetPlayer() {

    }

    private void testPasswordCorrect() {

    }

}
