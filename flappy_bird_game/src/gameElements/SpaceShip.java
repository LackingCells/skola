package gameElements;

/**
 * The SpaceShip class represents an enemy spaceship in the game.
 * It moves horizontally from right to left, while also performing a zigzag movement.
 * The zigzag period and amplitude are fixed and set by constants.
 */
public class SpaceShip extends Enemy{
    private int zigzagCounter = 0;
    private final int zigzagPeriod = 50;
    private final int zigzagAmplitude = 5;
    private boolean zigzagDirection = true;

    /**
     * Constructs a new SpaceShip object with the given position, size, image asset, and hit points.
     * @param x the x-coordinate of the top-left corner of the spaceship
     * @param y the y-coordinate of the top-left corner of the spaceship
     * @param width the width of the spaceship
     * @param height the height of the spaceship
     * @param asset the file path to the image asset of the spaceship
     */
    public SpaceShip(int x, int y, int width, int height, String asset) {
        super(x, y, width, height, asset, 2);
    }

    /**
     * Updates the position of the spaceship according to its speed and zigzag movement.
     * The zigzag movement is performed by changing the y-coordinate in a fixed pattern.
     */
    public void update() {
        x -= 3;
        zigzagCounter++;
        if (zigzagCounter >= zigzagPeriod) {
            zigzagCounter = 0;
            zigzagDirection = !zigzagDirection;
        }
        if (zigzagDirection) {
            y += zigzagAmplitude;
        } else {
            y -= zigzagAmplitude;
        }
    }
}
