package gameElements;

/**

 DoubleScore is a subclass of PowerUp that represents a power-up item that doubles the player's score for a limited time.
 */
public class DoubleScore extends PowerUp {

    /**

     Constructs a new DoubleScore object with the specified x and y position, width, height, asset image path, and duration time.
     @param x The x coordinate of the DoubleScore's position.
     @param y The y coordinate of the DoubleScore's position.
     @param width The width of the DoubleScore.
     @param height The height of the DoubleScore.
     @param asset The image path of the DoubleScore's asset.
     */
    public DoubleScore(int x, int y, int width, int height, String asset) {
        super(x, y, width, height, asset, 10000 * 1_000_000L);
    }
    /**

     Doubles the player's score and sets the DoubleScore object as the active power-up of the player.
     @param player The player object that picks up the power-up.
     */
    @Override
    public void effect(Player player) {
        player.setPowerUp(this);
    }
}