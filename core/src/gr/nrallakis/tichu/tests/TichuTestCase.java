package gr.nrallakis.tichu.tests;

import org.junit.After;
import org.junit.Before;

public abstract class TichuTestCase {

    @Before
    public abstract void setUp();

    @After
    public abstract void cleanUp();
}
