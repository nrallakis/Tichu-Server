package gr.nrallakis.tichu.tests;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import gr.nrallakis.tichu.server.game.Card;
import gr.nrallakis.tichu.server.game.CardCombination;

public class TestCardCombinationKryoSerializer extends TichuTestCase {

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
        Array<Card> cards = new Array<>();
        cards.add(new Card(2, 2));
        cards.add(new Card(1, 3));
        cards.add(new Card(2, 4));
        cards.add(new Card(3, 5));
        cards.add(new Card(0, 6));

        CardCombination combination = new CardCombination(CardCombination.STRAIGHT, 2, cards);
        Output output = new Output(new FileOutputStream("file.bin"));

        DefaultArraySerializers.ObjectArraySerializer cardArraySerializer
                = new DefaultArraySerializers.ObjectArraySerializer(kryo, Card[].class);
        cardArraySerializer.setElementsCanBeNull(false);
        cardArraySerializer.setElementsAreSameType(true);

        kryo.register(Card[].class, cardArraySerializer);
        kryo.writeObject(output, combination);
        output.close();

        Input input = new Input(new FileInputStream("file.bin"));
        CardCombination afterCombination = kryo.readObject(input, CardCombination.class);

        assertEquals(combination.getType(), afterCombination.getType());
        assertEquals(combination.getValue(), afterCombination.getValue());
        assertEquals(combination.getCards(), afterCombination.getCards());
    }
}
