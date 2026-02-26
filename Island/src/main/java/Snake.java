public class Snake extends Predator {
    public Snake() {
        super("Snake", 15, 30, 1, 3);
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        switch (prey.getName()) {
            case "Fox": return 15;
            case "Rabbit": return 20;
            case "Mouse": return 40;
            case "Duck": return 10;
            default: return 0;
        }
    }

    @Override
    protected Animal createBaby() {
        return new Snake();
    }
}