package states;

import constants.Constants;
import gameElements.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * This state represents the Playing State of the Game The main responsibility
 * of this class is to; - create Game Objects - update Game Objects - draw Game
 * Objects are for instance; players, enemies, npc, etc...
 * <p>
 * The PlayState can also be thought of as a blueprint where data is loaded
 * into some container from a file or some other type of data storage.
 * <p>
 * It can also be created by some class responsible for object creation and then
 * passed to the PlayState as a parameter. This means all the PlayState has to
 * do is receive a list of objects, store them in some container and then for
 *  * every object in that container update and render that object.
 * <p>
 * This way you can let the user define different Levels based on what
 * parameters are passed into the PlayState.
 */
public class PlayState extends GameState implements Constants {

	private Image bg;
	private final ArrayList<Pipe> pipes = new ArrayList<>();
	private final ArrayList<Enemy> enemies = new ArrayList<>();
	private final ArrayList<PowerUp> powerUps = new ArrayList<>();
	private Player player;
	private final SortedScoreList topScores;
	private int score;
	private final boolean hard;
	private LevelManager lm;

	//Audio
	private final MediaPlayer themePlayer;
	private final MediaPlayer scorePlayer;
	private final MediaPlayer powerUpPlayer;
	private final MediaPlayer hitPlayer;

	/**

	 Constructs a new PlayState object with the specified GameModel and top scores list.
	 Also sets the background image, theme player, sound effect players, and hard mode flag.
	 @param model the GameModel object to associate with this PlayState
	 @param topScores the SortedScoreList object containing the top scores to display
	 @param hard a boolean flag indicating whether or not hard mode is enabled
	 */
	public PlayState(GameModel model, SortedScoreList topScores, boolean hard){
		super(model);
		try {
			bg = new Image(new FileInputStream("assets/bg.gif"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.hard = hard;
		if (hard){
			themePlayer = addMediaPlayer("assets/mlg.mp3");
		} else {
			themePlayer = addMediaPlayer("assets/theme.mp3");
		}

		scorePlayer = addMediaPlayer("assets/score.mp3");
		powerUpPlayer = addMediaPlayer("assets/powerup.mp3");
		hitPlayer = addMediaPlayer("assets/hit.mp3");

		this.topScores = topScores;
	}

	/**

	 Draws the game state on the given graphics context.
	 @param g the graphics context to draw on
	 */
	@Override
	public void draw(GraphicsContext g) {
		//drawBg(g, bgColor);
		g.drawImage(bg,0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		player.draw(g);

		for (Pipe obj : pipes) {
			obj.draw(g);
		}

		for (Enemy enemy : enemies){
			enemy.draw(g);
		}

		for (PowerUp powerUp : powerUps){
			powerUp.draw(g);
		}

		drawScore(g);
	}

	/**

	 Draws the current score on the canvas.

	 @param g the graphics context to draw on
	 */
	private void drawScore(GraphicsContext g) {
		g.setStroke(Color.BLACK);
		g.setLineWidth(2);
		g.setFill(Color.WHITE);

		// Load custom font and draw the text with the stroke
		Font customFont = Font.loadFont(getClass().getResourceAsStream("FlappyBird.ttf"), 50);
		g.setFont(customFont);
		g.strokeText(String.valueOf(score), SCREEN_WIDTH / 2 - 25, 100);
		g.fillText(String.valueOf(score), SCREEN_WIDTH / 2 - 25, 100);
	}

	/**

	 Handles key press events. If the user presses the ESCAPE key, the game state is switched to the MenuState and
	 the theme music is stopped.
	 @param key the key event that triggered this method
	 */
	@Override
	public void keyPressed(KeyEvent key) {

		if (key.getCode() == KeyCode.ESCAPE){
			model.switchState(new MenuState(model, topScores));
			themePlayer.stop();
		}

	}


	@Override
	public void handleMouseInput(MouseEvent mouseEvent) {
	}

	/**

	 Updates the game state based on the current timestamp and the keys pressed.
	 @param now the current timestamp in nanoseconds
	 @param keysPressed a Set of KeyCode representing the keys currently pressed
	 */
	@Override
	public void update(long now, Set<KeyCode> keysPressed) {

		player.update(keysPressed);
		lm.update(now,score);

		Iterator<Pipe> pipeIterator = pipes.iterator();
		while (pipeIterator.hasNext()) {
			Pipe pipe = pipeIterator.next();
			pipe.update();

			if (player.checkCollision(pipe) && player.invincible(now)) {
				gameOver();
			}

			if (player.getX() >= pipe.getX()) {
				if (!pipe.getScored()) {
					if (player.doubleScore(now)) {
						score += 2;
					} else {
						score++;
					}
					scorePlayer.play();
					pipe.scored();
				}
			}

			if (pipe.getX() + pipe.getWidth() < 0) {
				pipeIterator.remove();
			}
		}


		Iterator<Enemy> enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();
			enemy.update();

			if (player.checkCollision(enemy) && !enemy.hasCollided()) {
				if (score < enemy.getDamage()) {
					score = 0;
				} else {
					score -= enemy.getDamage();
				}
				enemy.setHasCollided();
				hitPlayer.play();
				enemyIterator.remove();
			}

			if(enemy.getX() + enemy.getWidth() < 0 || enemy.getY() > SCREEN_HEIGHT){
				enemyIterator.remove();
			}
		}


		Iterator<PowerUp> powerUpIterator = powerUps.iterator();
		while (powerUpIterator.hasNext()) {
			PowerUp powerUp = powerUpIterator.next();
			powerUp.update();

			if (player.checkCollision(powerUp)) {
				powerUp.activate(now);
				powerUp.effect(player);
				powerUpPlayer.play();
				powerUpIterator.remove();
			}

			if(powerUp.getX() + powerUp.getWidth() < 0){
				powerUpIterator.remove();
			}
		}

	}

	/**

	 Activates the game state by starting the background theme player, initializing the player object and level manager
	 */
	@Override
	public void activate() {
		themePlayer.play();
		player = new Player(100,Constants.SCREEN_HEIGHT/2 , 60,40,"assets/woody.png");
		lm = new LevelManager(pipes,enemies, powerUps,hard);
	}

	/**
	 * We currently don't have anything to deactivate in the PlayState, so we leave
	 * this method empty in this case.
	 */
	@Override
	public void deactivate() {
	}

	/**

	 Creates a new {@link MediaPlayer} instance with the specified sound file and sets its properties for playing in the game.
	 @param filePath the path of the sound file to be played by the media player
	 @return the new media player instance with the sound file loaded and configured
	 */
	private MediaPlayer addMediaPlayer(String filePath) {
		Media sound = new Media(new File(filePath).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		prepareMediaPlayer(mediaPlayer);
		return mediaPlayer;
	}

	/**

	 Prepares a media player by setting its volume, balance, cycle count, and on end of media behavior.
	 @param mediaPlayer the media player to prepare
	 */
	private void prepareMediaPlayer(MediaPlayer mediaPlayer){
		mediaPlayer.setVolume(0.1);
		mediaPlayer.setBalance(0.0);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setOnEndOfMedia(() -> {
			mediaPlayer.stop();
			mediaPlayer.seek(mediaPlayer.getStartTime());
		});
	}

	/**

	 Ends the current game session and switches to the Game Over state, passing the current game score and the list of top scores to the new state.
	 Stops the theme music from playing.
	 */
	private void gameOver(){
		topScores.add(score);
		model.switchState(new GameOverState(model, topScores, hard));
		themePlayer.stop();
	}
}
