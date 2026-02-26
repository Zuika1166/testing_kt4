public class Fox extends Predator {
    public Fox() {
        super("Fox", 8, 30, 2, 2);
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        switch (prey.getName()) {
            case "Rabbit": return 70;
            case "Mouse": return 90;
            case "Duck": return 60;
            case "Caterpillar": return 40;
            default: return 0;
        }
    }

    @Override
    protected Animal createBaby() {
        return new Fox();
    }
}