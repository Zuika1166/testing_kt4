import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Island {
    private final int width, height;
    private final IslandCell[][] cells;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService animalExecutor;
    private final SimulationConfig config;
    private volatile boolean isRunning;
    private int currentTick;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new IslandCell[width][height];
        this.config = new SimulationConfig();
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.animalExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.isRunning = false;
        this.currentTick = 0;

        initializeCells();
        populateIsland();
    }

    private void initializeCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new IslandCell(x, y);
            }
        }
    }

    private void populateIsland() {
        Random random = new Random();

        for (int i = 0; i < config.getInitialAnimalsCount(); i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Animal animal = createRandomAnimal();
            cells[x][y].addAnimal(animal);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y].growPlants();
            }
        }
    }

    private Animal createRandomAnimal() {
        Random random = new Random();
        int animalType = random.nextInt(15);

        switch (animalType) {
            case 0: return new Wolf();
            case 1: return new Snake();
            case 2: return new Fox();
            case 3: return new Bear();
            case 4: return new Eagle();
            case 5: return new Horse();
            case 6: return new Deer();
            case 7: return new Rabbit();
            case 8: return new Mouse();
            case 9: return new Goat();
            case 10: return new Sheep();
            case 11: return new Boar();
            case 12: return new Buffalo();
            case 13: return new Duck();
            case 14: return new Caterpillar();
            default: return new Rabbit();
        }
    }

    public void startSimulation() {
        isRunning = true;
        System.out.println("Simulation started");

        scheduler.scheduleAtFixedRate(this::growPlantsTask, 0, 3, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::animalLifeCycleTask, 0, 1, TimeUnit.SECONDS);
    }

    public void stopSimulation() {
        isRunning = false;
        scheduler.shutdown();
        animalExecutor.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            if (!animalExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                animalExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            animalExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Simulation stopped");
    }

    private void growPlantsTask() {
        if (!isRunning) return;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y].growPlants();
            }
        }
    }

    private void animalLifeCycleTask() {
        if (!isRunning) return;

        currentTick++;
        System.out.println("Tick: " + currentTick);

        List<Callable<Void>> tasks = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int cellX = x;
                final int cellY = y;

                tasks.add(() -> {
                    processCell(cellX, cellY);
                    return null;
                });
            }
        }

        try {
            animalExecutor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        cleanupDeadAnimals();

        if (shouldStopSimulation()) {
            System.out.println("Simulation stopping condition met!");
            stopSimulation();
        }
    }

    private void processCell(int x, int y) {
        IslandCell cell = cells[x][y];
        List<Animal> animals = new ArrayList<>(cell.getAnimals());

        Collections.shuffle(animals);

        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;

            if (animal instanceof Predator) {

                for (Animal prey : animals) {
                    if (prey != animal && prey.isAlive() && animal.eat(prey, cell)) {
                        break;
                    }
                }
            } else if (animal instanceof Herbivore) {

                if (cell.getPlantsCount() > 0) {
                    animal.eat(new Plant(), cell);
                }

                for (Animal prey : animals) {
                    if (prey != animal && prey.isAlive() && animal.eat(prey, cell)) {
                        break;
                    }
                }
            }

            animal.reproduce(cell);

            animal.move(this);

            animal.weight -= animal.getFoodRequired() * 0.3;
            if (animal.weight <= 0) {
                animal.die();
            }
        }
    }

    private void cleanupDeadAnimals() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                IslandCell cell = cells[x][y];
                List<Animal> animals = cell.getAnimals();
                animals.removeIf(animal -> !animal.isAlive());
            }
        }
    }

    private boolean shouldStopSimulation() {
        int totalAnimals = getTotalAnimals();
        return totalAnimals == 0 || currentTick >= config.getMaxTicks();
    }

    public int getTotalAnimals() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                total += cells[x][y].getAnimals().size();
            }
        }
        return total;
    }

    public int getTotalPlants() {
        int total = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                total += cells[x][y].getPlantsCount();
            }
        }
        return total;
    }

    public Map<String, Integer> getAnimalCounts() {
        Map<String, Integer> counts = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (Animal animal : cells[x][y].getAnimals()) {
                    if (animal.isAlive()) {
                        String name = animal.getName();
                        counts.put(name, counts.getOrDefault(name, 0) + 1);
                    }
                }
            }
        }
        return counts;
    }

    public IslandCell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        }
        return null;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}