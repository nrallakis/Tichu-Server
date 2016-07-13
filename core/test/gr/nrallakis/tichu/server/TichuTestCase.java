package gr.nrallakis.tichu.server;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public abstract class TichuTestCase {

    @BeforeClass
    public static void initGdx() {
        Gdx.app = mock(Application.class);
    }

    @Before
    public void setUp() {}

    @Before
    public void networkingSetup() throws IOException {}

    @After
    public void cleanUp() {}
}
