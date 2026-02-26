import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    protected final String name;
    protected double weight;
    protected final double maxWeight;
    protected final int maxPerCell;
    protected final int maxSpeed;
    protected final double foodRequired;
    protected boolean isAlive;
    protected int x, y;

    public Animal(String name, double weight, int maxPerCell, int maxSpeed, double foodRequired) {
        this.name = name;
        this.weight = weight;
        this.maxWeight = weight;
        this.maxPerCell = maxPerCell;
        this.maxSpeed = maxSpeed;
        this.foodRequired = foodRequired;
        this.isAlive = true;
    }

    public abstract boolean eat(Object food, IslandCell cell);
    public abstract void move(Island island);
    public abstract boolean reproduce(IslandCell cell);

    public void die() {
        this.isAlive = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public String getName() { return name; }
    public double getWeight() { return weight; }
    public int getMaxPerCell() { return maxPerCell; }
    public int getMaxSpeed() { return maxSpeed; }
    public double getFoodRequired() { return foodRequired; }
    public boolean isAlive() { return isAlive; }
    public int getX() { return x; }
    public int getY() { return y; }
}