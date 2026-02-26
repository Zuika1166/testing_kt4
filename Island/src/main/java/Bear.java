public class Bear extends Predator {
    public Bear() {
        super("Bear", 500, 5, 2, 80);
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        switch (prey.getName()) {
            case "Snake": return 80;
            case "Horse": return 40;
            case "Deer": return 80;
            case "Rabbit": return 80;
            case "Mouse": return 90;
            case "Goat": return 70;
            case "Sheep": return 70;
            case "Boar": return 50;
            case "Buffalo": return 20;
            case "Duck": return 10;
            default: return 0;
        }
    }

    @Override
    protected Animal createBaby() {
        return new Bear();
    }
}