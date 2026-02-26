public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super("Caterpillar", 0.01, 1000, 0, 0);
    }

    @Override
    public void move(Island island) {
        // Гусеница не двигается
    }

    @Override
    protected Animal createBaby() {
        return new Caterpillar();
    }
}