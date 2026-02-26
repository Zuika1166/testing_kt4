public class Wolf extends Predator {
    public Wolf() {
        super("Wolf", 50, 30, 3, 8);
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        switch (prey.getName()) {
            case "Horse": return 10;
            case "Deer": return 15;
            case "Rabbit": return 60;
            case "Mouse": return 80;
            case "Goat": return 60;
            case "Sheep": return 70;
            case "Boar": return 15;
            case "Buffalo": return 10;
            case "Duck": return 40;
            default: return 0;
        }
    }

    @Override
    protected Animal createBaby() {
        return new Wolf();
    }
}