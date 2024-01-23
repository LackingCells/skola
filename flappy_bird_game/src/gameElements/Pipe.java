package gameElements;

/**

 Pipe class represents the obstacle in the game.

 It extends the GameObject class and implements the update method to move the pipe horizontally.

 It also has a scored flag that is used to keep track of whether the player has scored a point by passing the pipe.
 */
public class Pipe extends GameObject{
    private boolean scored;

    /**
     Constructor for creating a new pipe object.
     @param x the x-coordinate of the pipe's initial position
     @param y the y-coordinate of the pipe's initial position
     @param width the width of the pipe
     @param height the height of the pipe
     @param asset the filename of the image asset used to represent the pipe
     */
    public Pipe(int x, int y, int width, int height, String asset) {
        super(x, y, width, height, asset);
        scored = false;
    }
    /**

     Updates the pipe's position by moving it horizontally based on the speedX value.
     */
    public void update() {
        x -= speedX;
    }

    /**
     Marks the pipe as scored.
     */
    public void scored(){
        scored = true;
    }

    /**
     Returns whether the pipe has been scored by the player.
     @return true if the pipe has been scored, false otherwise
     */
    public boolean getScored() {
        return scored;
    }
}
