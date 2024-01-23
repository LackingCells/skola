package main;

import constants.Constants;
import gameElements.SortedScoreList;
import gameElements.SortedScoreListIO;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import states.GameModel;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This Class is the entry point of the application.
 * <p>
 * This Class has the following primary responsibilities: 1. Serve as the entry
 * for the Application
 * <p>
 * 2. Create the GameModel (For more information about the GameModel see
 * /src/states/GameModel)
 * <p>
 * 3. Create the GameFrame (A HBox Wrapper): (For more information about the
 * GameFrame see /src/main/GameFrame)...
 * <p>
 * 4. Create the GamePanel (A VBOx Wrapper): (For more information about the
 * GamePanel see /src/main/GamePanel)...
 * <p>
 * 5. Run the main loop of the game.
 * <p>
 * <a href="https://gitlab.liu.se/magni54/ExempelProjekt-TDDE10_725G90">...</a>
 *
 * @author magni54, alomi60
 */
public class Main extends Application implements Constants {
	private long frameCount = 0;
	private long lastUpdateTime = 0;

	private final Set<KeyCode> keysPressed = new HashSet<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage gameStage)  {
		gameStage.setTitle("Peck n flap");
		gameStage.setWidth(SCREEN_WIDTH);
		gameStage.setHeight(SCREEN_HEIGHT);
		GameModel model = new GameModel();
		GameFrame frame = new GameFrame(model, SCREEN_WIDTH, SCREEN_HEIGHT);
		Scene gameScene = new Scene(frame);
		// Set the target number of frames per second
		final double targetFps = 144;
		// Calculate frequency in nano seconds
		final double nanoPerUpdate = 1000000000.0 / targetFps;
		gameStage.setScene(gameScene);

		// We set up a setOnKeyPressed, to handle keyboard input,
		// like we had a onMouseClick in the canvas for the paint lab.
		// We send it on to the model, to handle it in the various
		// states of the game.

		gameScene.setOnKeyPressed(event -> {
			model.keyPressed(event);
			keysPressed.add(event.getCode());
		});

		gameScene.setOnKeyReleased(event -> {
			keysPressed.remove(event.getCode());
		});

		gameScene.setOnMouseClicked(model::handleMouseInput);

		// We set an AnimationTimer, to control the flow of the game.
		new AnimationTimer() {
			long lastUpdate = 0;

			// This method will be called
			public void handle(long now) {

				// Perform game update and game rendering. This will
				// execute approximately 144 times per second, or as
				// close to that as possible. Can vary greatly between systems.
				// If we want closer control we use something like the
				// if-statement below to control frame rate.

				frameCount++;
				if (now - lastUpdateTime >= 1_000_000_000) {
					double fps = frameCount / ((now - lastUpdateTime) / 1_000_000_000.0);
					System.out.println("FPS: " + fps);
					frameCount = 0;
					lastUpdateTime = now;
				}

				if ((now - lastUpdate) > nanoPerUpdate) {
					model.update(now,keysPressed);
					frame.repaint();
					lastUpdate = now;
				}
			}
		}.start(); // We start the timer.


		gameStage.setOnCloseRequest(event -> {
			SortedScoreListIO.saveToFile(model.getScores(),"highscore.txt");
			// Stop the JavaFX application
			System.exit(0);
		});

		gameStage.setResizable(false);
		gameStage.show();

	}
}