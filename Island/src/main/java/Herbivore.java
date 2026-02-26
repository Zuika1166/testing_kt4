import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    public Herbivore(String name, double weight, int maxPerCell, int maxSpeed, double foodRequired) {
        super(name, weight, maxPerCell, maxSpeed, foodRequired);
    }

    @Override
    public boolean eat(Object food, IslandCell cell) {
        if (food instanceof Plant) {
            Plant plant = (Plant) food;
            if (cell.getPlantsCount() > 0) {
                cell.removePlants(1);
                this.weight = Math.min(this.maxWeight, this.weight + plant.getNutritionalValue());
                return true;
            }
        } else if (food instanceof Animal) {
            // Для утки, которая ест гусениц
            Animal prey = (Animal) food;
            if (canEatAnimal(prey)) {
                int probability = getHuntingProbability(prey);
                if (ThreadLocalRandom.current().nextInt(100) < probability) {
                    this.weight = Math.min(this.maxWeight, this.weight + prey.getWeight());
                    prey.die();
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean canEatAnimal(Animal prey) {
        return false;
    }

    protected int getHuntingProbability(Animal prey) {
        return 0;
    }

    @Override
    public void move(Island island) {
        if (!isAlive) return;

        int speed = ThreadLocalRandom.current().nextInt(maxSpeed + 1);
        if (speed == 0) return;

        int newX = x;
        int newY = y;

        for (int i = 0; i < speed; i++) {
            int direction = ThreadLocalRandom.current().nextInt(4);
            switch (direction) {
                case 0: newX = Math.min(island.getWidth() - 1, newX + 1); break;
                case 1: newX = Math.max(0, newX - 1); break;
                case 2: newY = Math.min(island.getHeight() - 1, newY + 1); break;
                case 3: newY = Math.max(0, newY - 1); break;
            }
        }

        IslandCell newCell = island.getCell(newX, newY);
        if (newCell.canAddAnimal(this)) {
            island.getCell(x, y).removeAnimal(this);
            newCell.addAnimal(this);
            this.x = newX;
            this.y = newY;
        }
    }

    @Override
    public boolean reproduce(IslandCell cell) {
        if (!isAlive || cell.getAnimalsCount(this.getClass()) < 2) {
            return false;
        }

        if (ThreadLocalRandom.current().nextDouble() < 0.4) { // 40% шанс размножения для травоядных
            int babies = ThreadLocalRandom.current().nextInt(1, 5);
            for (int i = 0; i < babies; i++) {
                if (cell.canAddAnimal(this)) {
                    Animal baby = createBaby();
                    cell.addAnimal(baby);
                    baby.setPosition(cell.getX(), cell.getY());
                }
            }
            return true;
        }
        return false;
    }

    protected abstract Animal createBaby();
}