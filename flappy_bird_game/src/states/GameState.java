package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Set;

import static constants.Constants.SCREEN_HEIGHT;
import static constants.Constants.SCREEN_WIDTH;

/**
 * This class represents a state of the game. These states are different views
 * in the game, which contain their own separate logic.
 * Examples of states are: menu, playing, paused, game over and many more
 * <p>
 * This Class is an abstract class that defines a couple of general functions
 * that are used by the state machine to delegate work to the state.
 * <p>
 * The responsibilities of a state are roughly speaking separated into three
 * categories: - Input from the user (key presses, mouse clicks, etc.) - Internal
 * logic (move sprites, check for collision etc.) - Rendering (drawing objects
 * to the screen)
 * <p>
 * These steps are represented by the functions: - keyPressed (input step) -
 * update (logic step) - draw (rendering step)
 * <p>
 * To define a valid state, that state has to be derived from this class, and
 * needs to override the three functions mentioned above.
 */

public abstract class GameState {

	protected GameModel model;

	public GameState(GameModel model) {
		this.model = model;
	}

	public abstract void draw(GraphicsContext g);

	/**
	 * This function is called for each keyPressed event which occurs on the
	 * GamePanel. It is the responsibility of the state to filter out any keys it is
	 * interested in, since no filtering is performed before calling this function.
	 * <p>
	 * What this means is that if a state only wants to know if a certain key has
	 * been pressed, the state needs to check for that event inside this function.
	 */
	public abstract void keyPressed(KeyEvent key);

	/**

	 Handles the mouse input event triggered by the user. This method should be implemented in a subclass to provide
	 functionality for the specific mouse input event.
	 @param mouseEvent the MouseEvent object representing the mouse input event
	 */
	public abstract void handleMouseInput(MouseEvent mouseEvent);

	/**

	 Updates the state of the game based on the elapsed time since the last update and the current set of keys pressed.
	 This method should be implemented in a subclass to provide functionality for updating the game state.
	 @param now the timestamp of the current frame
	 @param keysPressed the set of KeyCodes representing the keys currently pressed by the user
	 */
	public abstract void update(long now, Set<KeyCode> keysPressed);

	/**
	 * This function is called whenever this state becomes the active one in the
	 * state machine.
	 *
	 * By default, it does nothing, but can be useful sometimes. E.g. unpause, or
	 * load something.
	 */
	public abstract void activate();

	/**
	 * This function is called whenever this state is replaced with another state in
	 * the state machine.
	 * 
	 * Can be used to, for example, set a pause flag, save, etc.
	 */
	public abstract void deactivate();

}
