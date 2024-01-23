package gameElements;

/**

 Enemy is an abstract class that represents an enemy object in the game.

 Enemy is a subclass of GameObject and has a damage value that it inflicts upon contact with the player.
 */
public abstract class Enemy extends GameObject {
    protected int damage;

    /**

     Constructs a new Enemy object with the specified x and y position, width, height, asset image path, and damage value.
     @param x The x coordinate of the Enemy's position.
     @param y The y coordinate of the Enemy's position.
     @param width The width of the Enemy.
     @param height The height of the Enemy.
     @param asset The image path of the Enemy's asset.
     @param damage The amount of damage the Enemy inflicts upon contact with the player.
     */
    public Enemy(int x, int y, int width, int height, String asset, int damage) {
        super(x, y, width, height, asset);
        this.damage = damage;
    }
    /**

     Returns the damage value of the Enemy object.
     @return The amount of damage the Enemy inflicts upon contact with the player.
     */
    public int getDamage() {
        return damage;
    }
}