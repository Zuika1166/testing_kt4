public class Boar extends Herbivore {
    public Boar() {
        super("Boar", 400, 50, 2, 50);
    }

    @Override
    protected boolean canEatAnimal(Animal prey) {
        return prey instanceof Caterpillar || prey instanceof Mouse;
    }

    @Override
    protected int getHuntingProbability(Animal prey) {
        if (prey instanceof Mouse) return 50;
        if (prey instanceof Caterpillar) return 90;
        return 0;
    }

    @Override
    protected Animal createBaby() {
        return new Boar();
    }
}