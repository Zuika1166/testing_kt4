public class Mouse extends Herbivore {
    public Mouse() {
        super("Mouse", 0.05, 500, 1, 0.01);
    }

    @Override
    protected Animal createBaby() {
        return new Mouse();
    }
}