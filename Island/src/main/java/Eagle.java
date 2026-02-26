public class Eagle extends Predator {
    public Eagle() {
        super("Eagle", 6, 20, 3, 1);
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        switch (prey.getName()) {
            case "Fox": return 10;
            case "Rabbit": return 90;
            case "Mouse": return 90;
            case "Duck": return 80;
            default: return 0;
        }
    }

    @Override
    protected Animal createBaby() {
        return new Eagle();
    }
}