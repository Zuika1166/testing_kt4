import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    void bearShouldHaveWeight() {
        Bear bear = new Bear();
        assertTrue(bear.getWeight() > 0);
    }

    @Test
    void goatShouldBeAliveAfterCreation() {
        Goat goat = new Goat();
        assertTrue(goat.isAlive());
    }

    @Test
    void wolfShouldBePredator() {
        Wolf wolf = new Wolf();
        assertTrue(wolf instanceof Predator);
    }

    @Test
    void rabbitShouldBeHerbivore() {
        Rabbit rabbit = new Rabbit();
        assertTrue(rabbit instanceof Herbivore);
    }

    @Test
    void duckShouldNotBeNull() {
        Duck duck = new Duck();
        assertNotNull(duck);
    }

    @Test
    void buffaloShouldHaveWeight() {
        Buffalo buffalo = new Buffalo();
        assertTrue(buffalo.getWeight() > 0);
    }

    @Test
    void caterpillarShouldHaveWeight() {
        Caterpillar c = new Caterpillar();
        assertTrue(c.getWeight() > 0);
    }

    @Test
    void snakeShouldBePredator() {
        Snake snake = new Snake();
        assertTrue(snake instanceof Predator);
    }

    @Test
    void foxShouldBePredator() {
        Fox fox = new Fox();
        assertTrue(fox instanceof Predator);
    }

    @Test
    void horseShouldBeHerbivore() {
        Horse horse = new Horse();
        assertTrue(horse instanceof Herbivore);
    }
}