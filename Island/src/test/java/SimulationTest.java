import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    void predatorShouldEatHerbivore() {

        IslandCell cell = new IslandCell(5, 5);

        Wolf wolf = new Wolf();
        Rabbit rabbit = new Rabbit();

        cell.addAnimal(wolf);
        cell.addAnimal(rabbit);

        wolf.eat(rabbit, cell);

        assertFalse(rabbit.isAlive());
    }

    @Test
    void predatorShouldNotEatPredator() {

        IslandCell cell = new IslandCell(5, 5);

        Wolf wolf1 = new Wolf();
        Wolf wolf2 = new Wolf();

        cell.addAnimal(wolf1);
        cell.addAnimal(wolf2);

        wolf1.eat(wolf2, cell);

        assertTrue(wolf2.isAlive());
    }
}