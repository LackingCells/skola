package states;

import gameElements.SortedScoreList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Set;

import static constants.Constants.SCREEN_WIDTH;

/**
 * This state represents the menu of the Game The main responsibility of this
 * class is to allow the user to swap state to the PlayState
 */
public class GameOverState extends GameState {
	/*
	 * The following three variables are just used to show that a change of state
	 * can be made. The same variables also exist in the PlayState, can you think of
	 * a way to make this more general and not duplicate variables?
	 */
	private final String informationText;
	private final Color fontColor;
	// A PlayState, so we can change to the PlayState from the menu.
	private final SortedScoreList topScores;
	private final boolean hard;

	public GameOverState(GameModel model, SortedScoreList topScores, boolean hard) {
		super(model);
		this.hard = hard;
		informationText = "Game Over";
		fontColor = Color.WHITE;
		this.topScores = topScores;
	}

	/**
	 * Draws information text to the screen
	 */
	@Override
	public void draw(GraphicsContext g) {
		// Set up stroke and fill for the text
		g.setStroke(Color.BLACK);
		g.setLineWidth(2);
		g.setFill(fontColor);

		Font customFont = Font.loadFont(getClass().getResourceAsStream("FlappyBird.ttf"), 70);
		g.setFont(customFont);
		g.strokeText(informationText, SCREEN_WIDTH / 2 - 130, 200);

		// Draw the text again with just the fill
		g.setFill(fontColor);
		g.fillText(informationText, SCREEN_WIDTH / 2 - 130, 200);

		//changes font to a smaller one
		Font smallerFont = Font.font(customFont.getFamily(), FontWeight.BOLD, 50);
		g.setFont(smallerFont);
		g.setFill(Color.GREEN);
		g.fillRect(SCREEN_WIDTH / 2 - 105, 270, 190, 60);

		g.strokeText("Try again?", SCREEN_WIDTH / 2 - 95, 315);
		g.setFill(fontColor);
		g.fillText("Try again?", SCREEN_WIDTH / 2 - 95, 315);

	}


	/**
	 *
	 * @param key KeyEvent representing the pressed key
	 *            This function prints the pressed key to the console it's used to
	 *            show that a change of state has been made
	 *
	 *            For more information see GameState
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("Pressed " + key.getText() + " in GameOverState");

		if (key.getCode() == KeyCode.ESCAPE) {
			model.switchState(new MenuState(model, topScores));
		}
	}

	@Override
	public void handleMouseInput(MouseEvent mouseEvent) {
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton().toString().equals("PRIMARY")) {
			if (mouseEvent.getX() > SCREEN_WIDTH / 2 - 105 && mouseEvent.getX() < SCREEN_WIDTH / 2 + 85 && mouseEvent.getY() > 270 && mouseEvent.getY() < 330) {
				model.switchState(new PlayState(model, topScores, hard));
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
