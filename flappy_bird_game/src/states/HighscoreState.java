package states;

import gameElements.SortedScoreList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This state represents the menu of the Game The main responsibility of this
 * class is to allow the user to swap state to the PlayState
 */
public class HighscoreState extends GameState {
	/*
	 * The following three variables are just used to show that a change of state
	 * can be made. The same variables also exist in the PlayState, can you think of
	 * a way to make this more general and not duplicate variables?
	 */
	private final String informationText;

	private Image bg;
	private final Color fontColor;
	private final SortedScoreList topScores;

	public HighscoreState(GameModel model, SortedScoreList topScores) {
		super(model);
		informationText = "Highscores";
		fontColor = Color.WHITE;
		this.topScores = topScores;
		try {
			bg = new Image(new FileInputStream("assets/bg.gif"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draws information text to the screen
	 */
	@Override
	public void draw(GraphicsContext g) {
		g.drawImage(bg,0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

		// Set up stroke and fill for the text
		g.setStroke(Color.BLACK);
		g.setLineWidth(2);
		g.setFill(fontColor);

		// Load custom font and draw the text with the stroke
		Font customFont = Font.loadFont(getClass().getResourceAsStream("FlappyBird.ttf"), 70);
		g.setFont(customFont);
		g.strokeText(informationText, SCREEN_WIDTH / 2 - 130, 200);

		//Draw the text again with just the fill
		g.setFill(fontColor);
		g.fillText(informationText, SCREEN_WIDTH / 2 - 130, 200);

		//Changes font to a smaller one
		Font smallerFont = Font.font(customFont.getFamily(), FontWeight.BOLD, 50);
		g.setFont(smallerFont);
		int offsetY = 240;

		//Top high-scores 1 through 5
		for (int x = 0; x < topScores.getMaxSize(); x++){
			g.setFill(Color.GREEN);
			g.fillRect(SCREEN_WIDTH / 2 - 90, offsetY, 140, 40);

			g.setFill(fontColor);
			g.strokeText((x + 1) + ". " + topScores.get(x),SCREEN_WIDTH / 2 - 80, offsetY + 34);
			g.fillText((x + 1) + ". " + topScores.get(x),SCREEN_WIDTH / 2 - 80, offsetY + 34);
			offsetY += 65;
		}

		// Add a "Clear" button
		g.setFill(Color.RED);
		g.fillRect(SCREEN_WIDTH / 2 - 90, offsetY, 140, 40);
		g.setFill(fontColor);
		g.strokeText("Clear", SCREEN_WIDTH / 2 - 60, offsetY + 34);
		g.fillText("Clear", SCREEN_WIDTH / 2 - 60, offsetY + 34);
	}


	/**
	 *
	 * @param key KeyEvent representing the pressed key
	 *
	 *            This function prints the pressed key to the console it's used to
	 *            show that a change of state has been made
	 *
	 *            For more information see GameState
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Pressed " + key.getText() + " in HighscoreState");

		if (key.getCode() == KeyCode.ESCAPE) {
			model.switchState(new MenuState(model, topScores));
		}
	}

	@Override
	public void handleMouseInput(MouseEvent mouseEvent) {
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton().toString().equals("PRIMARY")) {
			if (mouseEvent.getX() > SCREEN_WIDTH / 2 - 90 && mouseEvent.getX() < SCREEN_WIDTH / 2 - 90 + 140 &&
					mouseEvent.getY() > 565 && mouseEvent.getY() < 565 + 40) {
				// clear button clicked, handle the event here
				topScores.clear();
			}
		}
	}

	/**
	 * We have nothing to update in the menu, no moving objects etc. so we leave the
	 * update method empty.
     */
	@Override
	public void update(long now,Set<KeyCode> keysPressed) {
	}

	/**
	 * We currently don't have anything to activate in the MenuState, so we leave
	 * this method empty in this case.
	 */
	@Override
	public void activate() {

	}

	/**
	 * We currently don't have anything to deactivate in the MenuState, so we leave
	 * this method empty in this case.
	 */

	@Override
	public void deactivate() {

	}


}
