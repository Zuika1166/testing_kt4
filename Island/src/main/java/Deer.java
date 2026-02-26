public class Deer extends Herbivore {
    public Deer() {
        super("Deer", 300, 20, 4, 50);
    }

    @Override
    protected Animal createBaby() {
        return new Deer();
    }
}