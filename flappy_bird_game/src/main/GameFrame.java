package main;

import javafx.scene.layout.HBox;
import states.GameModel;

/**
 * This class Wraps a HBox: A HBox is a second level JavaFX container used
 * organize contents in the window (Stage/Scene).
 * <p>
 * The GameFrame, in this case, has the job of creating the game panel, and
 * adding it to itself in order for it to show.
 *
 */
public class GameFrame extends HBox {
	private final GamePanel g;

	public GameFrame(GameModel model, int width, int height) {
		// Create a new GamePanel and add it to the frame
		g = new GamePanel(model, width, height);
		this.getChildren().add(g);
	}

	public void repaint() {
		g.repaint();
	}
}
