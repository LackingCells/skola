package gameElements;

import constants.Constants;
import java.util.ArrayList;

/**
 * The `LevelManager` class manages the creation and update of pipes, enemies, and power-ups
 * in the game. It randomly generates pipes with a gap between them, enemies, and power-ups
 * at set intervals based on the game's current score. The class also stores the current
 * list of pipes, enemies, and power-ups in the game.
 */
public class LevelManager implements Constants {

    /**
     * The width of each pipe.
     */
    private final int pipeWidth;

    /**
     * The minimum gap between pipes.
     */
    private final int minGap;

    /**
     * The maximum gap between pipes.
     */
    private final int maxGap;

    /**
     * The minimum Y-coordinate for the top pipe.
     */
    private final int minY;

    /**
     * The maximum Y-coordinate for the top pipe.
     */
    private final int maxY;

    /**
     * The speed at which pipes move across the screen.
     */
    private final int pipeSpeed;

    /**
     * The list of pipes in the game.
     */
    private final ArrayList<Pipe> pipes;

    /**
     * The list of enemies in the game.
     */
    private final ArrayList<Enemy> enemies;

    /**
     * The list of power-ups in the game.
     */
    private final ArrayList<PowerUp> powerUps;

    /**
     * Constructs a new `LevelManager` object with the given lists of pipes, enemies,
     * and power-ups. If `hard` is true, the level will be harder with faster pipes and
     * a wider range of Y-coordinates for pipes and enemies.
     *
     * @param pipes     The list of pipes in the game.
     * @param enemies   The list of enemies in the game.
     * @param powerUps  The list of power-ups in the game.
     * @param hard      True if the level should be harder, false otherwise.
     */
    public LevelManager(ArrayList<Pipe> pipes, ArrayList<Enemy> enemies, ArrayList<PowerUp> powerUps, boolean hard) {
        // Set properties based on difficulty level
        if (hard){
            this.minY = -800;
            this.maxY = -400;
            this.minGap = 120;
            this.maxGap = 200;
            this.pipeSpeed = 5;
        } else {
            this.minGap = 120;
            this.maxGap = 200;
            this.pipeSpeed = 5;
            this.minY = -700;
            this.maxY = -450;
        }
        this.pipeWidth = 60;
        this.pipes = pipes;
        this.enemies = enemies;
        this.powerUps = powerUps;
    }

    /**
     * Generates a new set of pipes with a random gap between them and random Y-coordinates
     * for the top pipe.
     */
    public void generatePipes(){
        int gap = (int) (Math.random() * (maxGap - minGap) + minGap);
        int randomY = (int) (Math.random() * (maxY - minY) + minY);
        Pipe topPipe = new Pipe(SCREEN_WIDTH, randomY, pipeWidth, 1000,"assets/pipe2.png");
        Pipe bottomPipe = new Pipe(SCREEN_WIDTH, topPipe.y + topPipe.height + gap, pipeWidth, 1000,"assets/pipe.png");
        bottomPipe.scored();
        topPipe.speedX = pipeSpeed;
        bottomPipe.speedX = pipeSpeed;
        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }

    /**

     Generates a new enemy object randomly as either a SpaceShip or Lenny at a random position on the screen.

     Adds the new enemy object to the enemies list.
     */
    public void generateEnemy(){
        int randomY = (int) (Math.random() * (300 - 500) + 500);
        int randomX = (int) (Math.random() * (SCREEN_WIDTH - SCREEN_WIDTH/2) + SCREEN_WIDTH/2);
        double randomNumber = Math.random();
        if (randomNumber < 0.5) {
            enemies.add(new SpaceShip(SCREEN_WIDTH,randomY,50,50,"assets/spaceship.png"));
        } else {
            enemies.add(new Lenny(randomX,0,50,50,"assets/lenny.png", (int) Math.floor(Math.random() *(6 - 3 + 1) + 3)));
        }
    }

    /**

     Generates a new power-up object randomly as either a Ghost or DoubleScore at a random position on the screen.

     Adds the new power-up object to the powerUps list.
     */
    public void generatePowerUp() {
        int randomY = (int) (Math.random() * (300 - 500) + 500);
        double randomNumber = Math.random();
        if (randomNumber < 0.5) {
            powerUps.add(new Ghost(SCREEN_WIDTH, randomY, 50, 50, "assets/ghost.png"));
        } else {
            powerUps.add(new DoubleScore(SCREEN_WIDTH, randomY, 50, 50, "assets/multiplier.png"));
        }
    }

    private long lastPipeTime = 0;
    private long lastEnemyTime = 0;
    private long lastPowerUpTime = 0;

    /**

     Updates the game state based on the current time and score.

     Generates new pipes every 1.3 seconds.

     Generates a new enemy every 5 seconds if the score is above 10.

     Generates a new power-up every 25 seconds if the score is above 0.

     @param now The current time in nanoseconds.

     @param score The current score of the game.
     */
    public void update(long now, int score) {
        // Generate pipes
        if (now - lastPipeTime > 1_300_000_000L) {
            generatePipes();
            lastPipeTime = now;
        }

        // Generate enemy if score is above 10
        if (score > 10 && now - lastEnemyTime > 5_000_000_000L) {
            generateEnemy();
            lastEnemyTime = now;
        }

        if(score > 0 && now - lastPowerUpTime > 25_000_000_000L){
            generatePowerUp();
            lastPowerUpTime = now;
        }

    }
}

