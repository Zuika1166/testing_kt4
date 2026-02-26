public class Goat extends Herbivore {
    public Goat() {
        super("Goat", 60, 140, 3, 10);
    }

    @Override
    protected Animal createBaby() {
        return new Goat();
    }
}