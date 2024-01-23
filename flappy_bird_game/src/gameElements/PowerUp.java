package gameElements;

/**

 This abstract class represents a power-up object in the game. It extends the GameObject class and provides additional

 properties and methods specific to power-ups.
 */
public abstract class PowerUp extends GameObject {
    protected boolean active; // whether the power-up is currently active or not
    protected long startTime; // the start time of the active duration
    protected long duration; // the duration of the active state

    /**

     Constructs a new PowerUp object with the specified position, dimensions, image asset, and duration.
     @param x the x-coordinate of the power-up
     @param y the y-coordinate of the power-up
     @param width the width of the power-up
     @param height the height of the power-up
     @param asset the path to the image asset for the power-up
     @param duration the duration of the active state in milliseconds
     */
    public PowerUp(int x, int y, int width, int height, String asset, long duration) {
        super(x, y, width, height, asset);
        this.duration = duration;
        this.active = false;
    }
    /**

     Updates the position of the power-up object.
     */
    public void update(){
        x -= 4;
    }
    /**

     Activates the power-up and starts the active duration.
     @param now the current time in nanoseconds
     */
    public void activate(long now) {
        active = true;
        startTime = now;
    }
    /**

     Checks whether the power-up is currently active or not. If the power-up is active and the duration has expired,
     the power-up is deactivated.
     @param now the current time in nanoseconds
     @return true if the power-up is currently active, false otherwise
     */
    public boolean isActive(long now) {
        if (active && now - startTime > duration) {
            active = false;
        }
        return active;
    }
    /**

     Applies the effect of the power-up on the player.
     @param player the player object to apply the effect on
     */
    public abstract void effect(Player player);
}