package gr.nrallakis.tichu.tests;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import gr.nrallakis.tichu.server.game.Card;

public class TestCardKryoSerialization extends TichuTestCase {

    Kryo kryo;

    @Override
    public void setUp() {
        kryo = new Kryo();
    }

    @Override
    public void cleanUp() {
        kryo = null;
    }

    @Test
    public void testCardSerialization() throws FileNotFoundException {
        Output output = new Output(new FileOutputStream("file.bin"));
        kryo.register(Card.class);

        Card cardBefore = new Card(3, 9);
        kryo.writeObject(output, cardBefore);
        output.close();

        Input input = new Input(new FileInputStream("file.bin"));
        Card cardAfter = kryo.readObject(input, Card.class);
        input.close();

        assertEquals(cardBefore.getType(), cardAfter.getType());
        assertEquals(cardBefore.getRank(), cardAfter.getRank());
    }
}
