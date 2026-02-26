public class Sheep extends Herbivore {
    public Sheep() {
        super("Sheep", 70, 140, 3, 15);
    }

    @Override
    protected Animal createBaby() {
        return new Sheep();
    }
}