package gameElements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

import static constants.Constants.SCREEN_HEIGHT;
/**

 The Player class represents the player object in the game.
 It extends the abstract GameObject class and implements its abstract methods.
 It also contains additional methods and fields specific to the player object.
 @author leodi727 eliba473
 */
public class Player extends GameObject{
    private boolean jumping;
    private final double jumpSpeed = 4;
    private final double fallSpeed = 0.2;
    protected boolean invincible;
    private double rotationAngle;
    private PowerUp powerUp;


    /**
     * Constructor for the Player class.
     * It initializes the player's position and dimensions, and loads the asset image.
     * @param x the x-coordinate of the player
     * @param y the y-coordinate of the player
     * @param width the width of the player
     * @param height the height of the player
     * @param asset the file path of the asset image
     */
    public Player(int x, int y, int width, int height, String asset) {
        super(x, y, width, height,asset);
        jumping = false;
        rotationAngle = 0;
    }

    /**
     * Draws the player object on the canvas.
     * It saves the current state of the GraphicsContext, translates to the center of the image,
     * rotates based on the angle of rotation, draws the image at the center, and restores the saved state of the GraphicsContext.
     * @param g the GraphicsContext used to draw on the canvas
     */
    @Override
    public void draw(GraphicsContext g) {
        g.save(); // Save the current state of the GraphicsContext
        g.translate(x + width / 2, y + height / 2); // Translate to the center of the image
        g.rotate(rotationAngle); // Rotate based on the angle of rotation
        g.drawImage(img, -width / 2, -height / 2, width, height); // Draw the image at the center
        g.restore(); // Restore the saved state of the GraphicsContext
    }

    /**
     * Abstract method implementation.
     * It does not perform any update operation for the player object.
     */
    @Override
    public void update() {

    }

    /**
     * Updates the player object based on user input.
     * It adjusts the vertical speed based on gravity, and sets the vertical speed and rotation angle if the space key is pressed.
     * It updates the player's position, and checks if it is going out of screen bounds and adjusts position and speed accordingly.
     * @param keysPressed the set of keys currently pressed by the user
     */
    public void update(Set<KeyCode> keysPressed) {

        // Update vertical speed based on gravity
        speedY += fallSpeed;

        // Adjust vertical speed if space key is pressed
        if (keysPressed.contains(KeyCode.SPACE)) {
            if (!jumping) {
                jumping = true;
                speedY = (float) -jumpSpeed;
                rotationAngle = -45; // Set the angle of rotation to 45 degrees
            }
        } else {
            jumping = false;
            rotationAngle = 0; // Set the angle of rotation to 0 degrees
        }

        // Update player position
        y += speedY;

        // Check if player is going out of screen bounds and adjust position and speed accordingly
        if (y < 0) {
            y = 0;
            speedY = 0;
        } else if (y + height > SCREEN_HEIGHT) {
            y = SCREEN_HEIGHT - height;
            speedY = 0;
        }
    }


    /**
     * Checks if the player is invincible.
     * It returns true if the player does not have an active Ghost powerup.
     * @param now the current timestamp
     * @return true if the player is invincible, false otherwise
     */
    public boolean invincible(long now) {
        return powerUp == null || !(powerUp instanceof Ghost) || !powerUp.isActive(now);
    }

    /**
     * Checks if the player has doubleScore.
     * It returns true if the player does not have an active DoubleScore powerup.
     * @param now the current timestamp
     * @return true if the player has doubleScore, false otherwise
     */
    public boolean doubleScore(long now) {
        return powerUp != null && powerUp instanceof DoubleScore && powerUp.isActive(now);
    }

    /**
     * Sets the players powerup.
     * @param powerUp the powerup to be set
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }
}
