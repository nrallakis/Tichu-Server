package gr.nrallakis.tichu.server.tests;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public abstract class TichuTestCase {

    @Before
    public void setUp() {}

    @Before
    public void networkingSetup() throws IOException {}

    @After
    public void cleanUp() {}
}
