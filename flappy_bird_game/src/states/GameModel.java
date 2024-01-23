package states;

import gameElements.SortedScoreList;
import gameElements.SortedScoreListIO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Set;

/**
 * This class represents the current state of the game.
 * <p>
 * This implementation of a state machine keeps a reference to the current state
 * (see /src/states/GameState).
 * <p>
 * Please note: This is just one way to do it, there are several other ways and
 * none of them work for every case, so if you want to implement your own state
 * machine make sure that it can do all the operations that you need for your
 * project.
 * <p>
 * To change state simply call the switchState(GameState nextState) function
 * passing a reference to some other gameState.
 * <p>
 * Initial State: MenuState
 *
 */

public class GameModel {

	private GameState currentState;

	private SortedScoreList scores;

	public GameModel() {
		scores = new SortedScoreList();
		try{
			scores = SortedScoreListIO.readFromFile("highscore.txt");
			if(scores.size()<scores.getMaxSize()){
				for(int i = scores.size(); i < scores.getMaxSize(); i++){
					scores.add(0);
				}
			}
		} catch (NumberFormatException nfe){
			for(int i = 0; i < scores.getMaxSize(); i++){
				scores.add(0);
			}
		}

		// We start out in the MenuState.
		this.currentState = new MenuState(this, scores);
	}

	/**
	 * Switch to a new state, stored in the 'state' reference.
	 *
	 * This will call 'deactivate' on the current state, then store the new state as
	 * the current state, and finally call 'activate' on the new current state.
	 */
	public void switchState(GameState nextState) {
		currentState.deactivate();
		currentState = nextState;
		currentState.activate();
	}

	/**
	 * Delegates the keyPress from GamePanel to the current state
	 * 
	 * @param key Pressed key
	 */
	public void keyPressed(KeyEvent key) {
		currentState.keyPressed(key);
	}


	public void handleMouseInput(MouseEvent mouseEvent) {
		currentState.handleMouseInput(mouseEvent);
	}

	/**
	 * The update function is called every iteration of the game loop. it's usually
	 * used to update the games' logic e.g. objects position, velocity, etc...
     * @param now GameTime
     * @param keysPressed Pressed keys
     */
	public void update(long now, Set<KeyCode> keysPressed) {
		currentState.update(now,keysPressed);
	}

	/**
	 * @param g Graphics object passed from GamePanel This function delegates
	 *          drawing from the GamePanel to the current state
	 */
	public void draw(GraphicsContext g) {
		currentState.draw(g);
	}

	public SortedScoreList getScores(){
		return scores;
	}


}
