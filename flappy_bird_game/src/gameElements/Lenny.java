package gameElements;

/**

 Lenny class is a subclass of Enemy class which represents an enemy object in the game.

 Lenny is a type of enemy that moves down and to the left on the screen.
 */
public class Lenny extends Enemy {

    private int ySpeed;

    /**

     Constructs a new Lenny object with the specified x and y position, width, height, and asset image path.
     Sets the Lenny's speed to 10.
     @param x The x coordinate of the Lenny's position.
     @param y The y coordinate of the Lenny's position.
     @param width The width of the Lenny.
     @param height The height of the Lenny.
     @param asset The image path of the Lenny's asset.
     @param ySpeed The speed of which Lenny falls.
     */
    public Lenny(int x, int y, int width, int height, String asset, int ySpeed) {
        super(x, y, width, height, asset, 10);
        this.ySpeed = ySpeed;
    }
    /**

     Updates the Lenny's position by increasing the y coordinate by ySpeed and decreasing the x coordinate by 2.
     */
    public void update() {
        y += ySpeed;
        x -= 2;
    }
}


