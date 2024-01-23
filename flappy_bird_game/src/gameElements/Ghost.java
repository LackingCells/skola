package gameElements;

/**

 Ghost class is a subclass of PowerUp class which represents a power up object in the game.

 Ghost is a type of power up that makes the player invincible for a certain amount of time.
 */
public class Ghost extends PowerUp {

    /**

     Constructs a new Ghost object with the specified x and y position, width, height, and asset image path.
     Sets the duration of the Ghost power up to 5000 milliseconds.
     @param x The x coordinate of the Ghost's position.
     @param y The y coordinate of the Ghost's position.
     @param width The width of the Ghost.
     @param height The height of the Ghost.
     @param asset The image path of the Ghost's asset.
     */
    public Ghost(int x, int y, int width, int height, String asset) {
        super(x, y, width, height, asset, 5000 * 1_000_000L);
    }
    /**

     Overrides the effect method in PowerUp class to make the player invincible and set the Ghost power up to the player's current power up.
     @param player The player object that collects the Ghost power up.
     */
    @Override
    public void effect(Player player) {
        player.invincible = true;
        player.setPowerUp(this);
    }
}