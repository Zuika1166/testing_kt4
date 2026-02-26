public class Buffalo extends Herbivore {
    public Buffalo() {
        super("Buffalo", 700, 10, 3, 100);
    }

    @Override
    protected Animal createBaby() {
        return new Buffalo();
    }
}