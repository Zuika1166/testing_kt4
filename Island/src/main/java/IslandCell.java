import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class IslandCell {
    private final int x, y;
    private final List<Animal> animals;
    private int plantsCount;

    public IslandCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.animals = new CopyOnWriteArrayList<>();
        this.plantsCount = 0;
    }

    public void addAnimal(Animal animal) {
        if (canAddAnimal(animal)) {
            animals.add(animal);
            animal.setPosition(x, y);
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public boolean canAddAnimal(Animal animal) {
        return getAnimalsCount(animal.getClass()) < animal.getMaxPerCell();
    }

    public int getAnimalsCount(Class<?> animalClass) {
        return (int) animals.stream()
                .filter(a -> a.getClass().equals(animalClass))
                .count();
    }

    public int getTotalAnimalsCount() {
        return animals.size();
    }

    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    public void growPlants() {
        plantsCount = Math.min(Plant.getMaxPerCell(),
                plantsCount + ThreadLocalRandom.current().nextInt(5, 15));
    }

    public void removePlants(int amount) {
        plantsCount = Math.max(0, plantsCount - amount);
    }

    public int getPlantsCount() {
        return plantsCount;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}