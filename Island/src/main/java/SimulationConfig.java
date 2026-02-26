public class SimulationConfig {
    private int initialAnimalsCount = 500;
    private int maxTicks = 100;
    private int islandWidth = 60;
    private int IslandHeight = 30;
    private int tickDurationMs = 1000;

    public int getInitialAnimalsCount() {return initialAnimalsCount;}
    public int getMaxTicks() {return maxTicks;}
    public int getIslandWidth() {return islandWidth;}
    public int getTickDurationMs() {return tickDurationMs;}
    public int getIslandHeight() {return IslandHeight;}

    public void setInitialAnimalsCount(int count){
        this.initialAnimalsCount = count;
    }
    public void setMaxTicks(int ticks){
        this.maxTicks = ticks;
    }
    public void setIslandWidth(int width){
        this.islandWidth = width;
    }
    public void setIslandHeight(int height){
        this.IslandHeight = height;
    }
}
