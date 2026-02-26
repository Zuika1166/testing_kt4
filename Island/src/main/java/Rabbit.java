public class Rabbit extends Herbivore {
    public Rabbit() {
        super("Rabbit", 2, 150, 2, 0.45);
    }

    @Override
    protected Animal createBaby() {
        return new Rabbit();
    }
}