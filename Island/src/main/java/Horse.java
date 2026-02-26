public class Horse extends Herbivore {
    public Horse() {
        super("Horse", 400, 20, 4, 60);
    }

    @Override
    protected Animal createBaby() {
        return new Horse();
    }
}