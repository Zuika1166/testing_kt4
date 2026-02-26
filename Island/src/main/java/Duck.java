public class Duck extends Herbivore {
    public Duck() {
        super("Duck", 1, 200, 4, 0.15);
    }

    @Override
    protected boolean canEatAnimal(Animal prey) {
        return prey instanceof Caterpillar;
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        if (prey instanceof Caterpillar) return 90;
        return 0;
    }

    @Override
    protected Animal createBaby() {
        return new Duck();
    }
}