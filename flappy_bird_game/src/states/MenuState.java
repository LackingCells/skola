package states;

import gameElements.SortedScoreList;
import gameElements.SortedScoreListIO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This state represents the menu of the Game The main responsibility of this
 * class is to allow the user to swap state to the PlayState
 */
public class MenuState extends GameState {
	/*
	 * The following three variables are just used to show that a change of state
	 * can be made. The same variables also exist in the PlayState, can you think of
	 * a way to make this more general and not duplicate variables?
	 */
	private final String informationText;
	private final Color fontColor;
	private final Color buttonColor;
	// A PlayState, so we can change to the PlayState from the menu.
	private final PlayState playStateHard;
	private final PlayState playStateEasy;
	private final HighscoreState highscoreState;

	private final SortedScoreList topScores;
	private Image bg;

	public MenuState(GameModel model, SortedScoreList topScores) {
		super(model);
		playStateEasy = new PlayState(model, topScores, false);
		playStateHard = new PlayState(model, topScores, true);
		highscoreState = new HighscoreState(model, topScores);
		informationText = "Peck n flap";
		fontColor = Color.WHITE;
		buttonColor = Color.GREEN;
		try {
			bg = new Image(new FileInputStream("assets/bg.gif"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.topScores = topScores;
	}

	/**
	 * Draws information text to the screen
	 */
	@Override
	public void draw(GraphicsContext g) {
		//drawBg(g, bgColor);
		g.drawImage(bg,0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		// Set up stroke and fill for the text
		g.setStroke(Color.BLACK);
		g.setLineWidth(2);
		g.setFill(fontColor);

		// Load custom font and draw the text with the stroke
		Font customFont = Font.loadFont(getClass().getResourceAsStream("FlappyBird.ttf"), 70);
		g.setFont(customFont);
		g.strokeText(informationText, SCREEN_WIDTH / 2 - 140, 200);

		// Draw the text again with just the fill
		g.setFill(fontColor);
		g.fillText(informationText, SCREEN_WIDTH / 2 - 140, 200);

		//easy mode
		g.setFill(buttonColor);
		g.fillRect(SCREEN_WIDTH / 2 - 90, 240, 140, 60);

		g.setFill(fontColor);
		g.strokeText("Easy",SCREEN_WIDTH / 2 - 70, 287);
		g.fillText("Easy",SCREEN_WIDTH / 2 - 70, 287);

		//hard-mode
		g.setFill(buttonColor);
		g.fillRect(SCREEN_WIDTH / 2 - 90, 330, 140, 60);

		g.setFill(fontColor);
		g.strokeText("Hard",SCREEN_WIDTH / 2 - 70, 377);
		g.fillText("Hard",SCREEN_WIDTH / 2 - 70, 377);

		//highscore
		g.setFill(buttonColor);
		g.fillRect(SCREEN_WIDTH / 2 - 130, 420, 220, 70);

		g.setFill(fontColor);
		g.strokeText("Highscore",SCREEN_WIDTH / 2 - 120, 470);
		g.fillText("Highscore",SCREEN_WIDTH / 2 - 120, 470);

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
		System.out.println("Pressed " + key.getText() + " in MenuState");

		if (key.getCode() == KeyCode.ESCAPE) {
			SortedScoreListIO.saveToFile(topScores,"highscore.txt");
			System.exit(0);
		}
	}

	/**

	 Handles mouse input events by checking if the mouse is clicked and which button is clicked. If the left mouse button
	 is clicked on the Easy button, the model's state is switched to the PlayStateEasy. If the left mouse button is clicked
	 on the Hard button, the model's state is switched to the PlayStateHard. If the left mouse button is clicked on the
	 Highscore button, the model's state is switched to the HighscoreState.
	 @param mouseEvent the MouseEvent object representing the mouse input event
	 */
	@Override
	public void handleMouseInput(MouseEvent mouseEvent) {
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton().toString().equals("PRIMARY")){
			if (mouseEvent.getX() > SCREEN_WIDTH / 2 - 90 && mouseEvent.getX() <SCREEN_WIDTH / 2 + 50 && mouseEvent.getY() > 240 && mouseEvent.getY() < 290) {
				model.switchState(playStateEasy);
			}
			if (mouseEvent.getX() > SCREEN_WIDTH / 2 - 90 && mouseEvent.getX() <SCREEN_WIDTH / 2 + 50 && mouseEvent.getY() > 330 && mouseEvent.getY() < 390){
				model.switchState(playStateHard);
			}
			if (mouseEvent.getX() > SCREEN_WIDTH / 2 - 130 && mouseEvent.getX() <SCREEN_WIDTH / 2 + 90 && mouseEvent.getY() > 420 && mouseEvent.getY() < 490){
				model.switchState(highscoreState);
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
